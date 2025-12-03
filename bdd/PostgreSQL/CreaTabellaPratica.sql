DROP TABLE IF EXISTS pratica CASCADE;;
CREATE TABLE pratica (
    id_pratica SERIAL PRIMARY KEY,
    giorno_sessione DATE NOT NULL,
    aula VARCHAR(50),
    posti_totali INTEGER,
    corso_id INTEGER NOT NULL,

    CONSTRAINT fk_corso
        FOREIGN KEY(corso_id)
            REFERENCES corso(id_corso) ON DELETE CASCADE
);
COMMENT ON TABLE pratica IS 'Tabella nel quale si memorizzano i dati relativi alle sessioni pratiche dei corsi';