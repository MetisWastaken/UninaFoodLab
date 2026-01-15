-- Funzione che verifica che stud_id punti a un utente di tipo 'Studente' e che corso_id punti a un corso esistente
CREATE OR REPLACE FUNCTION enforce_iscrittoc_checks()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.stud_id IS NOT NULL THEN
        PERFORM 1 FROM utente WHERE username = NEW.stud_id AND tipo_utente = 'Studente';
        IF NOT FOUND THEN
            RAISE EXCEPTION 'L''utente "%" non e'' uno Studente', NEW.stud_id;
        END IF;
    END IF;

    IF NEW.corso_id IS NOT NULL THEN
        PERFORM 1 FROM corso WHERE id_corso = NEW.corso_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'Il corso con id "%" non esiste', NEW.corso_id;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_iscrittoc_checks
BEFORE INSERT OR UPDATE ON iscritto_c
FOR EACH ROW EXECUTE FUNCTION enforce_iscrittoc_checks();
 
-- Trigger che controlla se uno studente può disiscriversi da un corso (non è iscritto a nessuna pratica associata a quel corso)
--(o se la pratica non è ancora finita)
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
            RAISE EXCEPTION 'Lo studente "%" non puo'' disiscriversi dal corso dal nome "%" perche'' e'' iscritto ad almeno una pratica non ancora terminata', OLD.stud_id, (SELECT nome FROM corso WHERE id_corso = OLD.corso_id);
        END IF;
    END LOOP;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_enforce_iscrittoc_unenroll_checks
BEFORE DELETE ON iscritto_c
FOR EACH ROW EXECUTE FUNCTION enforce_iscrittoc_unenroll_checks();

-- Trigger che controlla se è possibile iscriversi al corso: il bando di iscrizione apre una settimana prima dell'inizio e chiude quando il corso inizia
CREATE OR REPLACE FUNCTION enforce_enroll_corso_date_checks()
RETURNS TRIGGER AS $$
DECLARE
    corso_start_date DATE;
    corso_end_date DATE;
    enroll_open_date DATE;
    corso_nome VARCHAR;
BEGIN
    -- Recupera i dati del corso
    SELECT data_in, data_fin, nome INTO corso_start_date, corso_end_date, corso_nome FROM corso WHERE id_corso = NEW.corso_id;
    -- Calcola la data di apertura del bando (una settimana prima dell'inizio)
    enroll_open_date := corso_start_date - INTERVAL '7 days'; 
    -- Controlla se il corso è già terminato usando la funzione is_corso_finished()
    IF is_corso_finished(NEW.corso_id, NEW.data_iscrizione::DATE) THEN
        RAISE EXCEPTION 'Non e'' possibile iscriversi al corso di nome"%" perché e'' gia'' terminato il "%".', corso_nome, corso_end_date;
    END IF;
    -- Controlla se la data di iscrizione è prima dell'apertura del bando
    IF NEW.data_iscrizione::DATE < enroll_open_date THEN
        RAISE EXCEPTION 'Non e'' possibile iscriversi al corso di nome"%" perché il bando non è ancora aperto. Il bando aprirà il "%".', corso_nome, enroll_open_date;
    END IF;
    -- Controlla se il corso è già iniziato usando la funzione is_corso_started()
    IF is_corso_started(NEW.corso_id, NEW.data_iscrizione::DATE) THEN
        RAISE EXCEPTION 'Non e'' possibile iscriversi al corso di nome"%" perché il corso è già iniziato il "%".', corso_nome, corso_start_date;
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_enforce_enroll_corso_date_checks
BEFORE INSERT ON iscritto_c
FOR EACH ROW EXECUTE FUNCTION enforce_enroll_corso_date_checks();

-- Trigger che impedisce l'iscrizione doppia di uno studente a uno stesso corso
CREATE OR REPLACE FUNCTION prevent_duplicate_iscrittoc()
RETURNS TRIGGER AS $$
BEGIN
    PERFORM 1 FROM iscritto_c WHERE stud_id = NEW.stud_id AND corso_id = NEW.corso_id;
    IF FOUND THEN
        RAISE EXCEPTION 'Lo studente "%" e'' gia'' iscritto al corso con nome "%".', NEW.stud_id, (SELECT nome FROM corso WHERE id_corso = NEW.corso_id);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_prevent_duplicate_iscrittoc
BEFORE INSERT ON iscritto_c
FOR EACH ROW EXECUTE FUNCTION prevent_duplicate_iscrittoc();