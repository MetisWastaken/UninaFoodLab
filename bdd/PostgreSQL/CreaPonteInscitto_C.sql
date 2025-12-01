DROP TABLE IF EXISTS inscritto_c CASCADE;

CREATE TABLE inscritto_c (
    stud_id VARCHAR(100) NOT NULL,
    corso_id INT NOT NULL,
    CONSTRAINT pk_inscritto_c PRIMARY KEY (stud_id, corso_id),
    CONSTRAINT fk_inscrittoc_utente FOREIGN KEY (stud_id)
        REFERENCES utente(username) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_inscrittoc_corso FOREIGN KEY (corso_id)
        REFERENCES corso(id_corso) ON UPDATE CASCADE ON DELETE CASCADE
);

COMMENT ON TABLE inscritto_c IS 'Tabella ponte: studenti iscritti ai corsi — PK: (stud_id, corso_id)';
COMMENT ON COLUMN inscritto_c.stud_id IS 'FK -> utente(username); deve riferire un utente con tipo_utente = ''Studente''';
COMMENT ON COLUMN inscritto_c.corso_id IS 'FK -> corso(id_corso)';

-- Funzione che verifica che stud_id punti a un utente di tipo 'Studente' e che corso_id punti a un corso esistente
CREATE OR REPLACE FUNCTION enforce_inscrittoc_checks()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.stud_id IS NOT NULL THEN
        PERFORM 1 FROM utente WHERE username = NEW.stud_id AND tipo_utente = 'Studente';
        IF NOT FOUND THEN
            RAISE EXCEPTION 'stud_id "%" non corrisponde a un utente con tipo_utente = Studente', NEW.stud_id;
        END IF;
    END IF;

    IF NEW.corso_id IS NOT NULL THEN
        PERFORM 1 FROM corso WHERE id_corso = NEW.corso_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'corso_id "%" non corrisponde a un corso esistente', NEW.corso_id;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_inscrittoc_checks
BEFORE INSERT OR UPDATE ON inscritto_c
FOR EACH ROW EXECUTE FUNCTION enforce_inscrittoc_checks();
