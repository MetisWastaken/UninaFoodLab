-- Creazione tabella online
DROP TABLE IF EXISTS online CASCADE;
CREATE TABLE online (
    id_online SERIAL PRIMARY KEY,
    giorno_sessione DATE NOT NULL,
    codice_meeting VARCHAR(20),
    corso_id INTEGER NOT NULL,

    CONSTRAINT fk_corso
        FOREIGN KEY(corso_id)
            REFERENCES corso(id_corso) ON DELETE CASCADE
);
COMMENT ON TABLE online IS 'Tabella memorizza i dati relativi alle sessioni online dei corsi';
COMMENT ON COLUMN online.id_online IS 'Primary key: id della sessione online';
COMMENT ON COLUMN online.giorno_sessione IS 'Data della sessione online';
COMMENT ON COLUMN online.codice_meeting IS 'Codice meeting della sessione online';
COMMENT ON COLUMN online.corso_id IS 'FK -> corso(id_corso)';

