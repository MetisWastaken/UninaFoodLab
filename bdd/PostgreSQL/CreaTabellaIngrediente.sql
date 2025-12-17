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

COMMENT ON TABLE ingrediente IS 'Elenco degli ingredienti utilizzabili nelle ricette';
COMMENT ON COLUMN ingrediente.nome IS 'Identificatore univoco dell''ingrediente (es. Farina, Zucchero)';
COMMENT ON COLUMN ingrediente.unit_misura IS 'Unità di misura standard per l''ingrediente (chili o litri)';