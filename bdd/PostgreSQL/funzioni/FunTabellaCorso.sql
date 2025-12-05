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

BEFORE INSERT OR UPDATE ON corso
FOR EACH ROW EXECUTE FUNCTION enforce_chef_id_is_chef();

--Funzione che controlla che data_in (inizio corso) sia minore di data_fin(fine corso)
CREATE OR REPLACE FUNCTION enforce_corso_data_in_is_minor_than_data_fin()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.data_in IS NOT NULL AND NEW.data_fin IS NOT NULL THEN
        IF NEW.data_in > NEW.data_fin THEN
            RAISE EXCEPTION 'data_in "%" deve essere minore o uguale di data_fin "%"', NEW.data_in, NEW.data_fin;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_corso_data_in_is_minor_than_data_fin
BEFORE INSERT OR UPDATE ON corso
FOR EACH ROW EXECUTE FUNCTION enforce_corso_data_in_is_minor_than_data_fin();