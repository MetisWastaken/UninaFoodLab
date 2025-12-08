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

-- Trigger che controlla se uno studente può disiscriversi da un corso
---(non è iscritto a nessuna pratica associata a quel corso)
---(o se la pratica non è ancora finita [is_pratica_finished(praticaId INT)])
CREATE OR REPLACE FUNCTION enforce_iscrittoc_unenroll_checks()
RETURNS TRIGGER AS $$
DECLARE
    pratica_record RECORD;
BEGIN
    FOR pratica_record IN
        SELECT p.id_pratica
        FROM pratica p
        JOIN iscritto_p ip ON p.id_pratica = ip.pratica_id
        WHERE ip.stud_id = OLD.stud_id AND p.corso_id = OLD.corso_id
    LOOP
        IF NOT is_pratica_finished(pratica_record.id_pratica) THEN
            RAISE EXCEPTION 'Lo studente "%" non può disiscriversi dal corso "%" perché è iscritto a una pratica non ancora terminata (id_pratica="%")', OLD.stud_id, OLD.corso_id, pratica_record.id_pratica;
        END IF;
    END LOOP;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_enforce_iscrittoc_unenroll_checks
BEFORE DELETE ON iscritto_c
FOR EACH ROW EXECUTE FUNCTION enforce_iscrittoc_unenroll_checks();

-- Trigger che impedisce l'iscrizione a corsi già terminati
    CREATE OR REPLACE FUNCTION no_enroll_finished_corso()
    RETURNS TRIGGER AS $$
    BEGIN
    IF is_corso_finished(NEW.corso_id) THEN
        RAISE EXCEPTION 'Non è possibile iscriversi al corso "%" perché è già terminato.', NEW.corso_id;
    END IF;
    RETURN NEW;
    END;
    $$ LANGUAGE plpgsql;

CREATE TRIGGER trg_cant_enroll_finished_corso
BEFORE INSERT ON iscritto_c
FOR EACH ROW EXECUTE FUNCTION no_enroll_finished_corso();