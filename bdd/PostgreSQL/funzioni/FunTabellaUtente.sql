--Creazione funzione e trigger per controllare la forza della password
CREATE OR REPLACE FUNCTION password_is_strong()
RETURNS TRIGGER AS $$
BEGIN
    IF length(NEW.password)<6 THEN 
        RAISE EXECEPTION 'La password deve essere lunga almeno 6 caratteri';
    END IF;
    RETURN NEW;
END;
$$LANGUAGE plpgsql;

CREATE TRIGGER trg_password_is_strong
BEFORE INSERT OR UPDATE ON utente
FOR EACH ROW EXECUTE FUNCTION password_is_strong();