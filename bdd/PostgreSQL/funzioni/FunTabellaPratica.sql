--trigger per check della presenza di una sessione nello stesso giorno_sessione in pratica
CREATE OR REPLACE FUNCTION prevent_pratica_date_conflict()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.giorno_sessione IS NOT NULL AND NEW.corso_id IS NOT NULL THEN
        IF EXISTS (
            SELECT 1 FROM pratica
            WHERE giorno_sessione = NEW.giorno_sessione AND corso_id = NEW.corso_id AND id_pratica <> NEW.id_pratica
        ) THEN
            RAISE EXCEPTION 'Conflitto di data: esiste gia'' una sessione pratica per il corso di nome "%" nella data "%" ', (SELECT nome FROM corso WHERE id_corso = NEW.corso_id), NEW.giorno_sessione;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--trigger che utilizza la funzione prevent_pratica_date_conflict

CREATE TRIGGER trg_prevent_pratica_date_conflict
BEFORE INSERT OR UPDATE ON pratica
FOR EACH ROW EXECUTE FUNCTION prevent_pratica_date_conflict();

--trigger per evitare che sessioni online e pratica abbiano lo stesso giorno_sessione per lo stesso corso
CREATE OR REPLACE FUNCTION prevent_pratica_online_date_conflict()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.giorno_sessione IS NOT NULL AND NEW.corso_id IS NOT NULL THEN
        IF EXISTS (
            SELECT 1 FROM online
            WHERE giorno_sessione = NEW.giorno_sessione AND corso_id = NEW.corso_id
        ) THEN
            RAISE EXCEPTION 'Conflitto di data: esiste gia'' una sessione online per il corso di nome "%" nella data "%" ', (SELECT nome FROM corso WHERE id_corso = NEW.corso_id), NEW.giorno_sessione;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_prevent_pratica_online_date_conflict
BEFORE INSERT OR UPDATE ON pratica
FOR EACH ROW EXECUTE FUNCTION prevent_pratica_online_date_conflict();

--Funzione che restituisce true se una pratica(id) è finita(giorno_sessione passato), false altrimenti (ausiliaria)
CREATE OR REPLACE FUNCTION is_pratica_finished(praticaId INT, checkDate DATE DEFAULT CURRENT_DATE)
RETURNS BOOLEAN AS $$
DECLARE
    pratica_date DATE;
BEGIN
    SELECT giorno_sessione INTO pratica_date FROM pratica WHERE id_pratica = praticaId;
    IF pratica_date IS NULL THEN
        RAISE EXCEPTION 'La pratica con id "%" non esiste', praticaId;
    END IF;
    RETURN pratica_date < checkDate;
END;
$$ LANGUAGE plpgsql;

--Trigger, si trova a riga 69 in FunTabellaCorso.sql

CREATE TRIGGER trg_enforce_sessione_date_within_corso
BEFORE INSERT OR UPDATE ON pratica
FOR EACH ROW EXECUTE FUNCTION enforce_sessione_date_within_corso();

--Trigger che evita l'inserimento di posti_totali negativi 
CREATE OR REPLACE FUNCTION prevent_negative_posti_totali()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.posti_totali < 0 THEN
        RAISE EXCEPTION 'Il numero di posti_totali non puo'' essere negativo: %', NEW.posti_totali;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_prevent_negative_posti_totali
BEFORE INSERT OR UPDATE ON pratica
FOR EACH ROW EXECUTE FUNCTION prevent_negative_posti_totali();

--Trigger che previene l'inserimento di un numero di posti_totali diverso da 0 se aula è NULL
CREATE OR REPLACE FUNCTION prevent_posti_totali_with_null_aula()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.aula IS NULL AND NEW.posti_totali <> 0 THEN
        RAISE EXCEPTION 'Se aula e'' impostata al valore NULL, il numero di posti totali deve essere 0, non %', NEW.posti_totali;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_prevent_posti_totali_with_null_aula
BEFORE INSERT OR UPDATE ON pratica
FOR EACH ROW EXECUTE FUNCTION prevent_posti_totali_with_null_aula();

--trigger per impedire l'eliminazione di una sessione praticagià passata
CREATE OR REPLACE FUNCTION prevent_past_sessione_pratica_deletion()
RETURNS TRIGGER AS $$  
BEGIN
    IF OLD.giorno_sessione < CURRENT_DATE THEN
        RAISE EXCEPTION 'Non è possibile eliminare una sessione già passata. La sessione del giorno % non può essere eliminata.', OLD.giorno_sessione;
    END IF;

    RETURN OLD;
END;

CREATE TRIGGER trg_prevent_past_sessione_pratica_deletion
BEFORE DELETE ON pratica
FOR EACH ROW EXECUTE FUNCTION prevent_past_sessione_pratica_deletion();