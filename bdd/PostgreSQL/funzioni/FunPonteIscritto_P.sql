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

--funzione che conta il numero di iscritti a una pratica
CREATE OR REPLACE FUNCTION count_iscritti_pratica(praticaId INT)    
RETURNS INT AS $$
DECLARE
    iscritti_count INT;
BEGIN
    SELECT COUNT(*) INTO iscritti_count FROM iscritto_p WHERE pratica_id = praticaId;
    RETURN iscritti_count;
END;
$$ LANGUAGE plpgsql;
COMMENT ON FUNCTION count_iscritti_pratica(INT) IS 'Funzione che conta il numero di studenti iscritti a una specifica pratica identificata da praticaId';

--trigger che vieta l'aggiunta di un nuovo iscrtto se la pratica ha gia raggiunto il numero massimo di iscritti
CREATE OR REPLACE FUNCTION enforce_max_iscritti_pratica()
RETURNS TRIGGER AS $$   
DECLARE
    current_iscritti INT;
BEGIN
    current_iscritti := count_iscritti_pratica(NEW.pratica_id);
    IF current_iscritti >= (SELECT posti_totali FROM pratica WHERE id_pratica = NEW.pratica_id) THEN
        RAISE EXCEPTION 'La pratica con id "%" ha raggiunto il numero massimo di iscritti', NEW.pratica_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_enforce_max_iscritti_pratica
BEFORE INSERT ON iscritto_p
FOR EACH ROW EXECUTE FUNCTION enforce_max_iscritti_pratica();