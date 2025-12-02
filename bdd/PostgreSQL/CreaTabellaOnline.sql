DROP TABLE IF EXISTS online CASCADE;
CREATE TABLE online (
    id_online SERIAL PRIMARY KEY,
    giorno_sessione DATE NOT NULL,
    codice_meeting VARCHAR(20) NOT NULL,
    corso_id INTEGER NOT NULL,

    CONSTRAINT fk_corso
        FOREIGN KEY(corso_id)
            REFERENCES corso(id_corso) ON DELETE CASCADE
);
COMMENT ON TABLE online IS 'Tabella memorizza i dati relativi alle sessioni online dei corsi';