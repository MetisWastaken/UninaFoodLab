--Creazione tabella ponte pratica_svolta
DROP TABLE IF EXISTS pratica_svolta CASCADE;
CREATE TABLE pratica_svolta
(
    pratica_id INT NOT NULL,
    ricetta_id INT NOT NULL,
    
    CONSTRAINT pk_pratica_svolta PRIMARY KEY (pratica_id, ricetta_id),
    CONSTRAINT fk_pratica
        FOREIGN KEY(pratica_id)
            REFERENCES pratica(id_pratica) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_ricetta
        FOREIGN KEY(ricetta_id)
            REFERENCES ricetta(id_ricetta) ON DELETE CASCADE ON UPDATE CASCADE
);
COMMENT ON TABLE pratica_svolta IS 'Collega le pratiche alle ricette che sono state effettivamente svolte durante la sessione';
COMMENT ON COLUMN pratica_svolta.pratica_id IS 'Identificativo della pratica svolta';
COMMENT ON COLUMN pratica_svolta.ricetta_id IS 'Identificativo della ricetta utilizzata'; 

