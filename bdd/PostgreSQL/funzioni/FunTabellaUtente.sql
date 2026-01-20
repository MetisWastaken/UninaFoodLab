--creazione funzione e trigger per garantire che l'username sia univoco
CREATE OR REPLACE FUNCTION enforce_username_is_unique()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM utente WHERE username = NEW.username AND username <> OLD.username) THEN
        RAISE EXCEPTION 'Un utente con username "%" esiste gia''', NEW.username;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_username_is_unique
BEFORE INSERT OR UPDATE ON utente
FOR EACH ROW EXECUTE FUNCTION enforce_username_is_unique();

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