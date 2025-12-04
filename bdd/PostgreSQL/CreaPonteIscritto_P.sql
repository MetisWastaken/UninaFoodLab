--Creazione della tabella ponte iscritto_p 
DROP TABLE IF EXISTS iscritto_p CASCADE;
CREATE TABLE iscritto_p (
    stud_id VARCHAR(100) NOT NULL,
    pratica_id INT NOT NULL,
    CONSTRAINT pk_iscritto_p PRIMARY KEY (stud_id, pratica_id),
    CONSTRAINT fk_studente
        FOREIGN KEY (stud_id)
            REFERENCES utente(username) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_pratica
        FOREIGN KEY (pratica_id)
            REFERENCES pratica(id_pratica) ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE iscritto_p IS 'Tabella ponte rappresentante gli studenti iscritti alla sessione pratica';
COMMENT ON COLUMN iscritto_p.stud_id IS 'id dello studente che si iscrive alla sessione pratica';
COMMENT ON COLUMN iscritto_p.pratica_id IS 'id della sessione pratica a cui lo studente si iscrive';

-- funzione che verifica che stud_id punti a un utente di tipo 'Studente' e che pratica_id punti a una pratica esistente
CREATE OR REPLACE FUNCTION enforce_iscrittop_checks()
RETURNS TRIGGER AS $$   
BEGIN
    IF NEW.stud_id IS NOT NULL THEN
        PERFORM 1 FROM utente WHERE username = NEW.stud_id AND tipo_utente = 'Studente';
        IF NOT FOUND THEN
            RAISE EXCEPTION 'stud_id "%" non corrisponde a un utente con tipo_utente = Studente', NEW.stud_id;
        END IF;
    END IF;

    IF NEW.pratica_id IS NOT NULL THEN
        PERFORM 1 FROM pratica WHERE id_pratica = NEW.pratica_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'pratica_id "%" non corrisponde a una pratica esistente', NEW.pratica_id;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_enforce_iscrittop_checks
BEFORE INSERT OR UPDATE ON iscritto_p
FOR EACH ROW EXECUTE FUNCTION enforce_iscrittop_checks();