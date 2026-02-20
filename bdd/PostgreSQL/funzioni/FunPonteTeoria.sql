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