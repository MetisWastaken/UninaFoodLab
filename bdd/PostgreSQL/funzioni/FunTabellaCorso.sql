-- Funzione che verifica che chef_id punti a un utente di tipo 'Chef'
CREATE OR REPLACE FUNCTION enforce_chef_id_is_chef()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.chef_id IS NOT NULL THEN
        PERFORM 1 FROM utente WHERE username = NEW.chef_id AND tipo_utente = 'Chef';
        IF NOT FOUND THEN
            RAISE EXCEPTION 'L''utente di nome "%" non e'' uno Chef', NEW.chef_id;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_enforce_chef_id_is_chef
BEFORE INSERT OR UPDATE ON corso
FOR EACH ROW EXECUTE FUNCTION enforce_chef_id_is_chef();

--Funzione che controlla che data_in (inizio corso) sia minore di data_fin(fine corso)
CREATE OR REPLACE FUNCTION enforce_corso_data_in_is_minor_than_data_fin()
RETURNS TRIGGER AS $$
BEGIN
        IF NEW.data_in > NEW.data_fin THEN
            RAISE EXCEPTION 'La data d''inizio "%" deve essere minore o uguale di data di fine "%"', NEW.data_in, NEW.data_fin;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_corso_data_in_is_minor_than_data_fin
BEFORE INSERT OR UPDATE ON corso
FOR EACH ROW EXECUTE FUNCTION enforce_corso_data_in_is_minor_than_data_fin();

-- Funzione che verifica che il nome del corso non sia già in uso da un corso attivo
CREATE OR REPLACE FUNCTION enforce_unique_nome_corso_attivo()
RETURNS TRIGGER AS $$
BEGIN
    PERFORM 1 FROM corso 
    WHERE nome = NEW.nome AND data_fin >= NEW.data_in AND id_corso <> NEW.id_corso;
    
    IF FOUND THEN
        RAISE EXCEPTION 'Esiste gia'' un corso attivo con nome "%". Il nome puo'' essere riutilizzato solo dopo il completamento del corso precedente.', NEW.nome;
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_unique_nome_corso_attivo
BEFORE INSERT OR UPDATE ON corso
FOR EACH ROW EXECUTE FUNCTION enforce_unique_nome_corso_attivo();

--Questo trigger evita che la data inizio corso sia precedente alla data corrente
CREATE OR REPLACE FUNCTION enforce_corso_data_in_not_past()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.data_in < CURRENT_DATE THEN
        RAISE EXCEPTION 'La data d''inizio "%" non puo'' essere precedente alla data corrente "%"', NEW.data_in, CURRENT_DATE;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_corso_data_in_not_past
BEFORE INSERT OR UPDATE ON corso
FOR EACH ROW EXECUTE FUNCTION enforce_corso_data_in_not_past();

--trigger che vieta l'inserimento di una sessione al di fuori delle date di inizio e fine corso
CREATE OR REPLACE FUNCTION enforce_sessione_date_within_corso()
RETURNS TRIGGER AS $$
DECLARE
    corso_start_date DATE;
    corso_end_date DATE;
BEGIN
    SELECT data_in, data_fin INTO corso_start_date, corso_end_date FROM corso WHERE id_corso = NEW.corso_id;
    IF corso_start_date IS NULL OR corso_end_date IS NULL THEN
        RAISE EXCEPTION 'Il corso con id "%" non esiste', NEW.corso_id;
    END IF;
    IF NEW.giorno_sessione < corso_start_date OR NEW.giorno_sessione > corso_end_date THEN
        RAISE EXCEPTION 'La data della sessione "%" deve essere compresa tra la data di inizio "%" e la data di fine "%" del corso', NEW.giorno_sessione, corso_start_date, corso_end_date;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--Funzione che restituisce true se un corso(id) è finito(data_fin passato), false altrimenti
CREATE OR REPLACE FUNCTION is_corso_finished(corso_id INT, checkDate DATE DEFAULT CURRENT_DATE)
RETURNS BOOLEAN AS $$
DECLARE
    corso_end_date DATE;
BEGIN
    SELECT data_fin INTO corso_end_date FROM corso WHERE id_corso = corso_id;
    IF corso_end_date IS NULL THEN
        RAISE EXCEPTION 'Il corso con id "%" non esiste', corso_id;
    END IF;
    RETURN corso_end_date < checkDate;
END;
$$ LANGUAGE plpgsql;