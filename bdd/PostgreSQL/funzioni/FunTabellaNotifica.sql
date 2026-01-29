--- Funzione e trigger per garantire che username_chef in notifica sia uno Chef
CREATE OR REPLACE FUNCTION enforce_notifica_username_chef_is_chef()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.username_chef IS NOT NULL THEN
        PERFORM 1 FROM utente WHERE username = NEW.username_chef AND tipo_utente = 'Chef';
        IF NOT FOUND THEN
            RAISE EXCEPTION 'L''utente di nome "%" non e'' uno Chef', NEW.username_chef;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_enforce_notifica_username_chef_is_chef
BEFORE INSERT OR UPDATE ON notifica
FOR EACH ROW
EXECUTE FUNCTION enforce_notifica_username_chef_is_chef();

-- Funzione e trigger per garantire che corso_id in notifica faccia riferimento a un corso esistente(se sollo_iscritti è TRUE)
CREATE OR REPLACE FUNCTION enforce_notifica_corso_id_exists()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.solo_iscritti = TRUE THEN
        IF NEW.corso_id IS NULL THEN
            RAISE EXCEPTION 'il corso deve essere specificato quando la notifica e'' destinata solo agli iscritti';
        END IF;
        PERFORM 1 FROM corso WHERE id_corso = NEW.corso_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'Il corso con id "%" non esiste', NEW.corso_id;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_enforce_notifica_corso_id_exists
BEFORE INSERT OR UPDATE ON notifica
FOR EACH ROW
EXECUTE FUNCTION enforce_notifica_corso_id_exists();

--Funzione evitare che venga messo corso_id se solo_iscritti è FALSE
CREATE OR REPLACE FUNCTION enforce_notifica_no_corso_id_if_not_solo_iscritti()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.solo_iscritti = FALSE AND NEW.corso_id IS NOT NULL THEN
        RAISE EXCEPTION 'il corso non deve essere specificato quando la notifica non e'' destinata solo agli iscritti';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_enforce_notifica_no_corso_id_if_not_solo_iscritti
BEFORE INSERT OR UPDATE ON notifica
FOR EACH ROW
EXECUTE FUNCTION enforce_notifica_no_corso_id_if_not_solo_iscritti();

