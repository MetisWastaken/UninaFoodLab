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


--Trigger utile a controllare se una ricetta ha al suo interno già salvato un ingrediente con quel nome
CREATE OR REPLACE FUNCTION prevent_duplicate_Necessita()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS(
        SELECT 1 FROM necessita
        WHERE ricetta_id = NEW.ricetta_id AND ingrediente_id = NEW.ingrediente_id
    ) THEN
        RAISE EXCEPTION 'L''ingrediente "%" e'' già presente nella ricetta con nome %', NEW.ingrediente_id, (SELECT nome FROM ricetta WHERE id_ricetta = NEW.ricetta_id);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_prevent_duplicate_Necessita_trigger
BEFORE INSERT OR UPDATE ON necessita
FOR EACH ROW EXECUTE FUNCTION prevent_duplicate_Necessita();
