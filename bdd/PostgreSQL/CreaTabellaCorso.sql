--Creazione tabella corso
DROP TABLE IF EXISTS corso CASCADE;
CREATE TABLE corso (
    id_corso SERIAL PRIMARY KEY,
    nome VARCHAR(25) NOT NULL,
    categoria VARCHAR(25),
    data_in DATE NOT NULL,
    data_fin DATE NOT NULL,
    frequenza_settimanale VARCHAR(100),
    chef_id VARCHAR(100) NOT NULL
        REFERENCES utente(username) ON UPDATE CASCADE ON DELETE RESTRICT
);

COMMENT ON TABLE corso IS 'Catalogo dei corsi di cucina disponibili nel database';
COMMENT ON COLUMN corso.id_corso IS 'Identificativo numerico univoco del corso (autoincrementato)';
COMMENT ON COLUMN corso.nome IS 'Titolo descrittivo del corso';
COMMENT ON COLUMN corso.categoria IS 'Tipologia culinaria del corso (es. Pasticceria, Primi piatti)';
COMMENT ON COLUMN corso.data_in IS 'Data di inizio delle lezioni';
COMMENT ON COLUMN corso.data_fin IS 'Data di conclusione del corso';
COMMENT ON COLUMN corso.frequenza_settimanale IS 'Indicazione sulla frequenza delle lezioni (es. "Lun-Mer-Ven", "2 volte a settimana")';
COMMENT ON COLUMN corso.chef_id IS 'Riferimento allo Chef responsabile del corso (tramite username)';

