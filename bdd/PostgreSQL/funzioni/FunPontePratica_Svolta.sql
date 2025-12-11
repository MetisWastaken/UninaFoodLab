-- Funzione che verifica che pratica_id punti a una sessione pratica esistente e che ricetta_id punti a una ricetta esistente
CREATE OR REPLACE FUNCTION enforce_pratica_svolta_checks()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.pratica_id IS NOT NULL THEN
        PERFORM 1 FROM pratica WHERE id_pratica = NEW.pratica_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'pratica_id "%" non corrisponde a una sessione pratica esistente', NEW.pratica_id;
        END IF;
    END IF;

    IF NEW.ricetta_id IS NOT NULL THEN
        PERFORM 1 FROM ricetta WHERE id_ricetta = NEW.ricetta_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'ricetta_id "%" non corrisponde a una ricetta esistente', NEW.ricetta_id;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger per applicare la funzione enforce_pratica_svolta_checks prima di ogni inserimento o aggiornamento
CREATE TRIGGER trg_enforce_pratica_svolta_checks
BEFORE INSERT OR UPDATE ON pratica_svolta
FOR EACH ROW EXECUTE FUNCTION enforce_pratica_svolta_checks();

--Trigger che vieta l'inserimento di una ricetta non svolta in una sessione online precedente alla pratica
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
        RAISE EXCEPTION 'La ricetta_id "%" non e'' stata presentata in una sessione online prima della pratica_id "%"', NEW.ricetta_id, NEW.pratica_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_ricetta_if_presentata
BEFORE INSERT OR UPDATE ON pratica_svolta
FOR EACH ROW EXECUTE FUNCTION check_ricetta_if_presentata();