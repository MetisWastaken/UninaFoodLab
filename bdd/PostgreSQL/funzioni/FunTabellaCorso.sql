-- Funzione che verifica che chef_id punti a un utente di tipo 'Chef'
CREATE OR REPLACE FUNCTION enforce_chef_id_is_chef()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.chef_id IS NOT NULL THEN
        PERFORM 1 FROM utente WHERE username = NEW.chef_id AND tipo_utente = 'Chef';
        IF NOT FOUND THEN
            RAISE EXCEPTION 'chef_id "%" non corrisponde a un utente con tipo_utente = Chef', NEW.chef_id;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_chef_id_is_chef
BEFORE INSERT OR UPDATE ON corso
FOR EACH ROW EXECUTE FUNCTION enforce_chef_id_is_chef();