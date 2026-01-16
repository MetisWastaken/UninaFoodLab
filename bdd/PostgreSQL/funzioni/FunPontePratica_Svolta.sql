-- Funzione che verifica che pratica_id punti a una sessione pratica esistente e che ricetta_id punti a una ricetta esistente
CREATE OR REPLACE FUNCTION enforce_pratica_svolta_checks()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.pratica_id IS NOT NULL THEN
        PERFORM 1 FROM pratica WHERE id_pratica = NEW.pratica_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'La pratica con id "%" non corrisponde a una sessione pratica esistente', NEW.pratica_id;
        END IF;
    END IF;

    IF NEW.ricetta_id IS NOT NULL THEN
        PERFORM 1 FROM ricetta WHERE id_ricetta = NEW.ricetta_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'La ricetta con id "%" non esiste', NEW.ricetta_id;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger per applicare la funzione enforce_pratica_svolta_checks prima di ogni inserimento o aggiornamento
CREATE TRIGGER trg_enforce_pratica_svolta_checks
BEFORE INSERT OR UPDATE ON pratica_svolta
FOR EACH ROW EXECUTE FUNCTION enforce_pratica_svolta_checks();

--Trigger che vieta l'inserimento di una ricetta che non è stata svolta in una sessione pratica precedente alla online
CREATE OR REPLACE FUNCTION check_ricetta_if_presentata()
RETURNS TRIGGER AS $$
DECLARE
    corso_pratica INTEGER;
    giorno_pratica DATE;
    giorno_online DATE; 
BEGIN
    SELECT corso_id, giorno_sessione INTO corso_pratica, giorno_pratica
    FROM pratica WHERE id_pratica=NEW.pratica_id;

    SELECT onl.giorno_sessione INTO giorno_online
    FROM teoria t JOIN online onl ON t.online_id = onl.id_online
    WHERE t.ricetta_id=NEW.ricetta_id AND onl.corso_id=corso_pratica AND onl.giorno_sessione < giorno_pratica
    ORDER BY onl.giorno_sessione DESC LIMIT 1;

    IF giorno_online IS NULL THEN
        RAISE EXCEPTION 'Il nome della "%" non e'' stata presentata in una sessione online prima della pratica del giorno "%"',(SELECT nome FROM ricetta WHERE id_ricetta = NEW.ricetta_id), (SELECT giorno_sessione FROM pratica WHERE id_pratica = NEW.pratica_id);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_ricetta_if_presentata
BEFORE INSERT OR UPDATE ON pratica_svolta
FOR EACH ROW EXECUTE FUNCTION check_ricetta_if_presentata();

-- Trigger che impedisce l'iscrizione doppia di una stessa ricetta in una pratica
CREATE OR REPLACE FUNCTION prevent_duplicate_ricetta_in_pratica()
RETURNS TRIGGER AS $$   
BEGIN
    IF EXISTS (
        SELECT 1 FROM pratica_svolta 
        WHERE pratica_id = NEW.pratica_id AND ricetta_id = NEW.ricetta_id
    ) THEN
        RAISE EXCEPTION 'La ricetta con nome "%" e'' gia'' stata inserita per la pratica avente data "%"', (SELECT nome FROM ricetta WHERE id_ricetta = NEW.ricetta_id), (SELECT giorno_sessione FROM pratica WHERE id_pratica = NEW.pratica_id);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_prevent_duplicate_ricetta_in_pratica
BEFORE INSERT ON pratica_svolta
FOR EACH ROW EXECUTE FUNCTION prevent_duplicate_ricetta_in_pratica();