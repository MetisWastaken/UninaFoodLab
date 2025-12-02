--Creazione tabella corso
DROP TABLE IF EXISTS corso CASCADE;
CREATE TABLE corso (
    id_corso SERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    categoria VARCHAR(100),
    data_in DATE,
    data_fin DATE,
    frequenza_settimanale VARCHAR(100),
    chef_id VARCHAR(100) NOT NULL
        REFERENCES utente(username) ON UPDATE CASCADE ON DELETE RESTRICT
);

COMMENT ON TABLE corso IS 'Tabella corso — PK: id_corso';
COMMENT ON COLUMN corso.id_corso IS 'Primary key: id_corso';
COMMENT ON COLUMN corso.nome IS 'Nome corso';
COMMENT ON COLUMN corso.categoria IS 'Categoria';
COMMENT ON COLUMN corso.data_in IS 'Data inizio';
COMMENT ON COLUMN corso.data_fin IS 'Data fine';
COMMENT ON COLUMN corso.frequenza_settimanale IS 'Frequenza settimanale';
COMMENT ON COLUMN corso.chef_id IS 'FK -> utente(username); deve riferire un utente con tipo_utente = ''Chef''';

-- Funzione che verifica che chef_id punti a un utente di tipo 'Chef'
CREATE OR REPLACE FUNCTION enforce_chef_id_is_chef()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.chef_id IS NOT NULL THEN
        PERFORM 1 FROM utente WHERE username = NEW.chef_id AND tipo_utente = 'Chef';
        IF NOT FOUND THEN
            RAISE EXCEPTION 'chef_id "%" non corrisponde a un utente con tipo_utente = Chef', NEW.chef_id;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_chef_id_is_chef
BEFORE INSERT OR UPDATE ON corso
FOR EACH ROW EXECUTE FUNCTION enforce_chef_id_is_chef();