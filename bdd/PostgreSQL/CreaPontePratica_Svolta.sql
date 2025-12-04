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
COMMENT ON TABLE pratica_svolta IS 'Tabella ponte tra pratica e ricetta, memorizza le ricette svolte in una sessione pratica';
COMMENT ON COLUMN pratica_svolta.pratica_id IS 'FK -> pratica(id_pratica)';
COMMENT ON COLUMN pratica_svolta.ricetta_id IS 'FK -> ricetta(id_ricetta)'; 

