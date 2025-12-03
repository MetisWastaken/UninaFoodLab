-- Active: 1764577701438@@127.0.0.1@5432@uninafoodlab@public
-- Creazione tabella ponte teoria
DROP TABLE IF EXISTS teoria CASCADE;
CREATE TABLE teoria (
    online_id INT NOT NULL,
    ricetta_id INT NOT NULL,

    CONSTRAINT pk_teoria PRIMARY KEY (online_id, ricetta_id),
    CONSTRAINT fk_online
        FOREIGN KEY(online_id)
            REFERENCES online(id_online) ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT fk_ricetta_teoria
        FOREIGN KEY(ricetta_id)
            REFERENCES ricetta(id_ricetta) ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE teoria IS 'Tabella ponte: ricette trattate nelle sessioni online — PK: (online_id, ricetta_id)';
COMMENT ON COLUMN teoria.online_id IS 'FK -> online(id_online)';
COMMENT ON COLUMN teoria.ricetta_id IS 'FK -> ricetta(id_ricetta)';

-- Funzione che verifica che online_id punti a una sessione online esistente e che ricetta_id punti a una ricetta esistente
CREATE OR REPLACE FUNCTION enforce_teoria_checks()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.online_id IS NOT NULL THEN
        PERFORM 1 FROM online WHERE id_online = NEW.online_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'online_id "%" non corrisponde a una sessione online esistente', NEW.online_id;
        END IF;
    END IF;

    IF NEW.ricetta_id IS NOT NULL THEN
        PERFORM 1 FROM ricetta WHERE id_ricetta = NEW.ricetta_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'ricetta_id "%" non corrisponde a una ricetta esistente', NEW.ricetta_id;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger che utilizza la funzione enforce_teoria_checks per applicare i controlli
CREATE TRIGGER trg_enforce_teoria_checks
BEFORE INSERT OR UPDATE ON teoria
FOR EACH ROW EXECUTE FUNCTION enforce_teoria_checks();