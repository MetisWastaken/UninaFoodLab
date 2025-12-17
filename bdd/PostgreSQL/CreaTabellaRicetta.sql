--Creazione tabella ricetta
DROP TABLE IF EXISTS ricetta CASCADE;
CREATE TABLE ricetta (
    id_ricetta SERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    descrizione TEXT
);
COMMENT ON TABLE ricetta IS 'Registro delle ricette culinarie disponibili';
COMMENT ON COLUMN ricetta.id_ricetta IS 'Identificativo univoco della ricetta autoincrementato';
COMMENT ON COLUMN ricetta.nome IS 'Nome della ricetta (es. Carbonara, Tiramisù)';
COMMENT ON COLUMN ricetta.descrizione IS 'Descrizione dettagliata della ricetta';