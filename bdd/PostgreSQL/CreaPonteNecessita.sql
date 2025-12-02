--Creazione tabella ponte Necessita
DROP TABLE IF EXISTS necessita CASCADE;
CREATE TABLE necessita (
    ricetta_id INT NOT NULL,
    ingrediente_id VARCHAR(100) NOT NULL,
    quant_ing DECIMAL NOT NULL,
    CONSTRAINT pk_necessita PRIMARY KEY (ricetta_id, ingrediente_id),
    CONSTRAINT fk_necessita_ricetta FOREIGN KEY (ricetta_id)
        REFERENCES ricetta(id_ricetta) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_necessita_ingrediente FOREIGN KEY (ingrediente_id)
        REFERENCES ingrediente(nome) ON UPDATE CASCADE ON DELETE CASCADE
);

COMMENT ON TABLE necessita IS 'Tabella ponte: ingredienti necessari per una ricetta — PK: (ricetta_id, ingrediente_id)';
COMMENT ON COLUMN necessita.ricetta_id IS 'FK -> ricetta(id_ricetta)';
COMMENT ON COLUMN necessita.ingrediente_id IS 'FK -> ingrediente(nome)';
COMMENT ON COLUMN necessita.quant_ing IS 'Quantità dell''ingrediente necessaria per la ricetta';

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