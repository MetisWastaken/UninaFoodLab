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