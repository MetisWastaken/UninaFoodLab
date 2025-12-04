--Creazione tabella ponte iscritto_c
DROP TABLE IF EXISTS iscritto_c CASCADE;
CREATE TABLE iscritto_c (
    stud_id VARCHAR(100) NOT NULL,
    corso_id INT NOT NULL,
    data_iscrizione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_iscritto_c PRIMARY KEY (stud_id, corso_id),
    CONSTRAINT fk_studente
        FOREIGN KEY (stud_id)
            REFERENCES utente(username) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_corso
        FOREIGN KEY (corso_id)
            REFERENCES corso(id_corso) ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE iscritto_c IS 'Tabella ponte: studenti iscritti ai corsi — PK: (stud_id, corso_id)';
COMMENT ON COLUMN iscritto_c.stud_id IS 'FK -> utente(username); deve riferire un utente con tipo_utente = ''Studente''';
COMMENT ON COLUMN iscritto_c.corso_id IS 'FK -> corso(id_corso)';
COMMENT ON COLUMN iscritto_c.data_iscrizione IS 'Data e ora di iscrizione';

-- Funzione che verifica che stud_id punti a un utente di tipo 'Studente' e che corso_id punti a un corso esistente
CREATE OR REPLACE FUNCTION enforce_iscrittoc_checks()
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

CREATE TRIGGER trg_iscrittoc_checks
BEFORE INSERT OR UPDATE ON iscritto_c
FOR EACH ROW EXECUTE FUNCTION enforce_iscrittoc_checks();