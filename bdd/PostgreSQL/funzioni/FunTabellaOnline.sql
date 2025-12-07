--trigger che verifica che corso_id punti a un corso esistente
CREATE OR REPLACE FUNCTION enforce_online_checks()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.corso_id IS NOT NULL THEN
        PERFORM 1 FROM corso WHERE id_corso = NEW.corso_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'corso_id "%" non corrisponde a un corso esistente', NEW.corso_id;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger che utilizza la funzione enforce_online_checks per applicare i controlli
CREATE TRIGGER trg_enforce_online_checks
BEFORE INSERT OR UPDATE ON online
FOR EACH ROW EXECUTE FUNCTION enforce_online_checks();

--trigger per stesso giorno_sessione in online
CREATE OR REPLACE FUNCTION prevent_online_date_conflict()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.giorno_sessione IS NOT NULL AND NEW.corso_id IS NOT NULL THEN
        IF EXISTS (
            SELECT 1 FROM online
            WHERE giorno_sessione = NEW.giorno_sessione AND corso_id = NEW.corso_id AND id_online <> NEW.id_online
        ) THEN
            RAISE EXCEPTION 'Conflitto di data: esiste già una sessione online per il corso_id "%" nella data "%" ', NEW.corso_id, NEW.giorno_sessione;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
-- Trigger che utilizza la funzione prevent_online_date_conflict per applicare i controlli
CREATE TRIGGER trg_prevent_online_date_conflict
BEFORE INSERT OR UPDATE ON online
FOR EACH ROW EXECUTE FUNCTION prevent_online_date_conflict();

--trigger per evitare che sessioni online e pratica abbiano lo stesso giorno_sessione per lo stesso corso
CREATE OR REPLACE FUNCTION prevent_online_pratica_date_conflict()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.giorno_sessione IS NOT NULL AND NEW.corso_id IS NOT NULL THEN
        IF EXISTS (
            SELECT 1 FROM pratica
            WHERE giorno_sessione = NEW.giorno_sessione AND corso_id = NEW.corso_id
        ) THEN
            RAISE EXCEPTION 'Conflitto di data: esiste già una sessione pratica per il corso_id "%" nella data "%" ', NEW.corso_id, NEW.giorno_sessione;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger che utilizza la funzione prevent_online_pratica_date_conflict per applicare i controlli
CREATE TRIGGER trg_prevent_online_pratica_date_conflict
BEFORE INSERT OR UPDATE ON online
FOR EACH ROW EXECUTE FUNCTION prevent_online_pratica_date_conflict();
 