-- Funzione e trigger per garantire che il nome della ricetta sia univoco
CREATE OR REPLACE FUNCTION ENFORCED_ricetta_name_is_unique()
RETURNS TRIGGER AS $$
    BEGIN
        IF NEW.nome IS NOT NULL THEN
            PERFORM 1 FROM ricetta WHERE nome = NEW.nome AND id_ricetta <> NEW.id_ricetta;
            IF FOUND THEN
                RAISE EXCEPTION 'Una ricetta con nome "%" esiste già', NEW.nome;
            END IF;
        END IF;
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_ricetta_name_is_unique
BEFORE INSERT OR UPDATE ON ricetta
FOR EACH ROW EXECUTE FUNCTION ENFORCED_ricetta_name_is_unique();
