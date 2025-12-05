-- Funzione e trigger per garantire che ricetta_id faccia riferimento a una ricetta non esistente
CREATE OR REPLACE FUNCTION ENFORCED_ricetta_name_is_ricetta()
RETURNS TRIGGER AS $$
    BEGIN
        IF NEW.nome IS NOT NULL THEN
        PERFORM 1 FROM ricetta WHERE nome = NEW.ricetta.nome;
        IF FOUND THEN
            RAISE EXCEPTION 'ricetta_id "%" corrisponde a una ricetta esistente', NEW.nome;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_ricetta_name_is_ricetta
BEFORE INSERT OR UPDATE ON ricetta
FOR EACH ROW EXECUTE FUNCTION ENFORCED_ricetta_name_is_ricetta();
