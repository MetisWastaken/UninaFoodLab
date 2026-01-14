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

COMMENT ON TABLE necessita IS 'Rappresenta la lista degli ingredienti che servono per preparare ogni ricetta, con le rispettive quantità. Una ricetta richiede più ingredienti e lo stesso ingrediente può essere usato in ricette diverse';
COMMENT ON COLUMN necessita.ricetta_id IS 'Riferimento alla ricetta per cui serve l''ingrediente';
COMMENT ON COLUMN necessita.ingrediente_id IS 'Nome dell''ingrediente necessario per realizzare la ricetta';
COMMENT ON COLUMN necessita.quant_ing IS 'Quantità specifica dell''ingrediente richiesta dalla ricetta (espressa nell''unità di misura dell''ingrediente: chili o litri)';

