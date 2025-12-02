-- Active: 1764577701438@@127.0.0.1@5432@uninafoodlab@public
/*enum per unità di misura*/
DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'enum_i') THEN
        CREATE TYPE enum_i AS ENUM ('chili','litri');
    END IF;
END$$;

-- Creazione tabella ingrediente
DROP TABLE IF EXISTS ingrediente CASCADE;
CREATE TABLE ingrediente (
    nome VARCHAR(100) NOT NULL PRIMARY KEY,
    unit_misura enum_i 
);

COMMENT ON TABLE ingrediente IS 'Tabella ingrediente — PK: nome';
COMMENT ON COLUMN ingrediente.nome IS 'Primary key: nome';
COMMENT ON COLUMN ingrediente.unit_misura IS 'Unità di misura';