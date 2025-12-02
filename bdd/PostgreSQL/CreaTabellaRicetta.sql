--Creazione tabella ricetta
DROP TABLE IF EXISTS ricetta CASCADE;
CREATE TABLE ricetta (
    id_ricetta SERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    descrizione TEXT
);
COMMENT ON TABLE ricetta IS 'Tabella ricetta — PK: id_ricetta';
COMMENT ON COLUMN ricetta.id_ricetta IS 'Primary key: id_ricetta';
COMMENT ON COLUMN ricetta.nome IS 'Nome ricetta';
COMMENT ON COLUMN ricetta.descrizione IS 'Descrizione ricetta';