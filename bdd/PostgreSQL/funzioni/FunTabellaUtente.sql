--creazione funzione e trigger per garantire che l'username sia univoco e tipo_utente sia valido
CREATE OR REPLACE FUNCTION enforce_username_is_unique_and_valid_type()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.username IS NOT NULL THEN
        PERFORM 1 FROM utente WHERE username = NEW.username AND username <> NEW.username;
        IF FOUND THEN
            RAISE EXCEPTION 'Un utente con username "%" esiste gia''', NEW.username;
        END IF;
    END IF;

    IF NEW.tipo_utente NOT IN ('Chef', 'Studente') THEN
        RAISE EXCEPTION 'Tipo utente "%" non valido. Deve essere "Chef" o "Studente"', NEW.tipo_utente;
    END IF;

    RETURN NEW;
END;$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_username_is_unique_and_valid_type
BEFORE INSERT OR UPDATE ON utente
FOR EACH ROW EXECUTE FUNCTION enforce_username_is_unique_and_valid_type();

--Creazione funzione e trigger per controllare la forza della password
CREATE OR REPLACE FUNCTION password_is_strong()
RETURNS TRIGGER AS $$
BEGIN
    IF length(NEW.password)<6 THEN 
        RAISE EXCEPTION 'La password deve essere lunga almeno 6 caratteri';
    END IF;
    RETURN NEW;
END;
$$LANGUAGE plpgsql;

CREATE TRIGGER trg_password_is_strong
BEFORE INSERT OR UPDATE ON utente
FOR EACH ROW EXECUTE FUNCTION password_is_strong();