-- Funzione che verifica che ricetta_id punti a una ricetta esistente e che ingrediente_id punti a un ingrediente esistente
CREATE OR REPLACE FUNCTION enforce_necessita_checks()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.ricetta_id IS NOT NULL THEN
        PERFORM 1 FROM ricetta WHERE id_ricetta = NEW.ricetta_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'ricetta_id "%" non corrisponde a una ricetta esistente', NEW.ricetta_id;
        END IF;
    END IF;

    IF NEW.ingrediente_id IS NOT NULL THEN
        PERFORM 1 FROM ingrediente WHERE nome = NEW.ingrediente_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'ingrediente_id "%" non corrisponde a un ingrediente esistente', NEW.ingrediente_id;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger per applicare la funzione enforce_necessita_checks prima di ogni inserimento o aggiornamento
CREATE TRIGGER trg_enforce_necessita_checks
BEFORE INSERT OR UPDATE ON necessita
FOR EACH ROW EXECUTE FUNCTION enforce_necessita_checks();

--Trigger che la quantità dell'ingrediente sia sempre positiva
CREATE OR REPLACE FUNCTION enforce_positive_quant_ing()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.quant_ing <= 0 THEN
        RAISE EXCEPTION 'La quantità dell''ingrediente deve essere positiva. Valore fornito: %', NEW.quant_ing;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_enforce_positive_quant_ing
BEFORE INSERT OR UPDATE ON necessita
FOR EACH ROW EXECUTE FUNCTION enforce_positive_quant_ing();