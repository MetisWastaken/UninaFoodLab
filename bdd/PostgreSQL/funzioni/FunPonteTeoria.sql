-- Funzione che verifica che online_id punti a una sessione online esistente e che ricetta_id punti a una ricetta esistente
CREATE OR REPLACE FUNCTION enforce_teoria_checks()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.online_id IS NOT NULL THEN
        PERFORM 1 FROM online WHERE id_online = NEW.online_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'La sessione online con id "%" non esiste', NEW.online_id;
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

-- Trigger che utilizza la funzione enforce_teoria_checks per applicare i controlli
CREATE TRIGGER trg_enforce_teoria_checks
BEFORE INSERT OR UPDATE ON teoria
FOR EACH ROW EXECUTE FUNCTION enforce_teoria_checks();

-- Trigger che impedisce l'assegnazione doppia di una stessa ricetta in una sessione online
CREATE OR REPLACE FUNCTION prevent_duplicate_ricetta_in_online()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM teoria WHERE online_id = NEW.online_id AND ricetta_id = NEW.ricetta_id
    ) THEN
        RAISE EXCEPTION 'La ricetta con nome "%" e'' gia'' stata assegnata alla sessione online del giorno "%".', (SELECT nome FROM ricetta WHERE id_ricetta = NEW.ricetta_id), (SELECT giorno_sessione FROM online WHERE id_online = NEW.online_id);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_prevent_duplicate_ricetta_in_online
BEFORE INSERT ON teoria
FOR EACH ROW EXECUTE FUNCTION prevent_duplicate_ricetta_in_online();