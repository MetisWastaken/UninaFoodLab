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