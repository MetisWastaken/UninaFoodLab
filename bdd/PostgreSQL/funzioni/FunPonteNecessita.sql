-- Funzione che verifica che ricetta_id punti a una ricetta esistente e che ingrediente_id punti a un ingrediente esistente
CREATE OR REPLACE FUNCTION enforce_necessita_checks()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.ricetta_id IS NOT NULL THEN
        PERFORM 1 FROM ricetta WHERE id_ricetta = NEW.ricetta_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'La ricetta con id "%" non esiste', NEW.ricetta_id;
        END IF;
    END IF;

    IF NEW.ingrediente_id IS NOT NULL THEN
        PERFORM 1 FROM ingrediente WHERE nome = NEW.ingrediente_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'L''ingrediente di nome "%" non esiste', NEW.ingrediente_id;
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
        RAISE EXCEPTION 'La quantita'' dell''ingrediente deve essere positiva e diversa da zero. Valore fornito: %', NEW.quant_ing;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_enforce_positive_quant_ing
BEFORE INSERT OR UPDATE ON necessita
FOR EACH ROW EXECUTE FUNCTION enforce_positive_quant_ing();

--Trigger che impedice l'inserimento di valori non interi se nell unit_misura di ingrediente è NULL(un pezzo)
CREATE OR REPLACE FUNCTION enforce_non_integer_for_null_unit_misura()
RETURNS TRIGGER AS $$
DECLARE
    unit_misura_val enum_i;
BEGIN
    SELECT unit_misura INTO unit_misura_val FROM ingrediente WHERE nome = NEW.ingrediente_id;

    IF unit_misura_val IS NULL AND NEW.quant_ing != FLOOR(NEW.quant_ing) THEN
        RAISE EXCEPTION 'Per l''ingrediente "%", la quantita'' deve essere un numero intero. Valore fornito: %', NEW.ingrediente_id, NEW.quant_ing;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_enforce_non_integer_for_null_unit_misura
BEFORE INSERT OR UPDATE ON necessita
FOR EACH ROW EXECUTE FUNCTION enforce_non_integer_for_null_unit_misura();