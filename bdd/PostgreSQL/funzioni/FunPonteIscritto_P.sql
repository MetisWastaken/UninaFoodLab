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