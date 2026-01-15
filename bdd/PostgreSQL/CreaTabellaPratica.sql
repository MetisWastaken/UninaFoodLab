
DROP TABLE IF EXISTS pratica CASCADE;;
CREATE TABLE pratica (
    id_pratica SERIAL PRIMARY KEY,
    giorno_sessione DATE NOT NULL,
    aula VARCHAR(50),
    posti_totali INTEGER NOT NULL,
    corso_id INTEGER NOT NULL,

    CONSTRAINT fk_corso
        FOREIGN KEY(corso_id)
            REFERENCES corso(id_corso) ON DELETE CASCADE
);
COMMENT ON TABLE pratica IS 'Tabella nel quale si memorizzano i dati relativi alle sessioni pratiche dei corsi';
COMMENT ON COLUMN pratica.giorno_sessione IS 'Data in cui si svolge la sessione pratica';
COMMENT ON COLUMN pratica.aula IS 'Aula in cui si svolge la sessione pratica';
COMMENT ON COLUMN pratica.posti_totali IS 'Numero totale di posti disponibili per la sessione pratica';
COMMENT ON COLUMN pratica.corso_id IS 'Identificativo del corso associato alla sessione pratica';

