-- funzione che verifica che stud_id punti a un utente di tipo 'Studente' e che pratica_id punti a una pratica esistente
CREATE OR REPLACE FUNCTION enforce_iscrittop_checks()
RETURNS TRIGGER AS $$   
BEGIN
    IF NEW.stud_id IS NOT NULL THEN
        PERFORM 1 FROM utente WHERE username = NEW.stud_id AND tipo_utente = 'Studente';
        IF NOT FOUND THEN
            RAISE EXCEPTION 'L''utente "%" non e'' uno Studente', NEW.stud_id;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_enforce_iscrittop_checks
BEFORE INSERT OR UPDATE ON iscritto_p
FOR EACH ROW EXECUTE FUNCTION enforce_iscrittop_checks();

--trigger che vieta l'aggiunta di un nuovo iscritto se la pratica ha gia raggiunto il numero massimo di iscritti
-- Utilizza view_pratica_posti per il calcolo dei posti rimanenti
CREATE OR REPLACE FUNCTION enforce_max_iscritti_pratica()
RETURNS TRIGGER AS $$   
BEGIN
    IF (SELECT posti_rimanenti FROM view_studenti_iscritti WHERE id_pratica = NEW.pratica_id) <= 0 THEN
        RAISE EXCEPTION 'La pratica del giorno "%" ha raggiunto il numero massimo di iscritti', (SELECT giorno_sessione FROM pratica WHERE id_pratica = NEW.pratica_id);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_enforce_max_iscritti_pratica
BEFORE INSERT ON iscritto_p
FOR EACH ROW EXECUTE FUNCTION enforce_max_iscritti_pratica();

-- Funzione che verifica che lo studente sia iscritto al corso prima di iscriversi a una pratica di quel corso
CREATE OR REPLACE FUNCTION enforce_student_enrolled_in_corso()
RETURNS TRIGGER AS $$
DECLARE
    corso_id_pratica INT;
BEGIN
    SELECT corso_id INTO corso_id_pratica FROM pratica WHERE id_pratica = NEW.pratica_id;
    
    PERFORM 1 FROM iscritto_c WHERE stud_id = NEW.stud_id AND corso_id = corso_id_pratica;
    
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Lo studente "%" non e'' iscritto al corso dal nome "%" a cui appartiene la pratica avente data "%". Iscrizione non effettuata', 
        NEW.stud_id, (SELECT nome FROM corso WHERE id_corso = corso_id_pratica), (SELECT giorno_sessione FROM pratica WHERE id_pratica = NEW.pratica_id);
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_enforce_student_enrolled_in_corso
BEFORE INSERT OR UPDATE ON iscritto_p
FOR EACH ROW EXECUTE FUNCTION enforce_student_enrolled_in_corso(); 

-- Trigger che impedisce l'iscrizione a pratiche terminate (uno studente può iscriversi fino un giorno prima della fine)
CREATE OR REPLACE FUNCTION no_enroll_finished_pratica()
RETURNS TRIGGER AS $$   
BEGIN
    IF is_pratica_finished(NEW.pratica_id, (CURRENT_DATE + INTERVAL '1 d')::DATE) THEN
        RAISE EXCEPTION 'Non e'' possibile iscriversi alla pratica del giorno "%" in quanto e'' gia'' terminato il periodo d''iscrizione.', (SELECT giorno_sessione FROM pratica WHERE id_pratica = NEW.pratica_id);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_no_enroll_finished_pratica
BEFORE INSERT OR UPDATE ON iscritto_p
FOR EACH ROW EXECUTE FUNCTION no_enroll_finished_pratica();

-- Trigger che impedisce l'iscrizione a pratiche dove il corso non è ancora iniziato
CREATE OR REPLACE FUNCTION no_enroll_pratica_before_corso_start()
RETURNS TRIGGER AS $$
DECLARE
    corso_id_pratica INT;
BEGIN
    SELECT corso_id INTO corso_id_pratica FROM pratica WHERE id_pratica = NEW.pratica_id;
    
    IF NOT is_corso_started(corso_id_pratica) THEN
        RAISE EXCEPTION 'Non e'' possibile iscriversi alla pratica del giorno "%" prima dell''inizio del corso previsto per il "%".', 
        (SELECT giorno_sessione FROM pratica WHERE id_pratica = NEW.pratica_id), (SELECT data_in FROM corso WHERE id_corso = corso_id_pratica);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_no_enroll_pratica_before_corso_start
BEFORE INSERT OR UPDATE ON iscritto_p
FOR EACH ROW EXECUTE FUNCTION no_enroll_pratica_before_corso_start();

--Trigger che impedisce l'iscrizione doppia di uno studente a una stessa pratica
CREATE OR REPLACE FUNCTION prevent_duplicate_iscrittop()
RETURNS TRIGGER AS $$
BEGIN
    PERFORM 1 FROM iscritto_p WHERE stud_id = NEW.stud_id AND pratica_id = NEW.pratica_id;
    IF FOUND THEN
        RAISE EXCEPTION 'Lo studente "%" e'' gia'' iscritto alla pratica del giorno "%".', NEW.stud_id, (SELECT giorno_sessione FROM pratica WHERE id_pratica = NEW.pratica_id);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_prevent_duplicate_iscrittop
BEFORE INSERT ON iscritto_p
FOR EACH ROW EXECUTE FUNCTION prevent_duplicate_iscrittop();