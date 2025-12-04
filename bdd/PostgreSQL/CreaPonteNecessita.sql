--Creazione tabella ponte Necessita
DROP TABLE IF EXISTS necessita CASCADE;
CREATE TABLE necessita (
    ricetta_id INT NOT NULL,
    ingrediente_id VARCHAR(100) NOT NULL,
    quant_ing DECIMAL NOT NULL,
    CONSTRAINT pk_necessita PRIMARY KEY (ricetta_id, ingrediente_id),
    CONSTRAINT fk_ricetta
        FOREIGN KEY (ricetta_id)
            REFERENCES ricetta(id_ricetta) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_ingrediente
        FOREIGN KEY (ingrediente_id)
            REFERENCES ingrediente(nome) ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE necessita IS 'Tabella ponte: ingredienti necessari per una ricetta — PK: (ricetta_id, ingrediente_id)';
COMMENT ON COLUMN necessita.ricetta_id IS 'FK -> ricetta(id_ricetta)';
COMMENT ON COLUMN necessita.ingrediente_id IS 'FK -> ingrediente(nome)';
COMMENT ON COLUMN necessita.quant_ing IS 'Quantità dell''ingrediente necessaria per la ricetta';

