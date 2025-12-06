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

CREATE TRIGGER trg_enforce_chef_id_is_chef
BEFORE INSERT OR UPDATE ON corso
FOR EACH ROW EXECUTE FUNCTION enforce_chef_id_is_chef();

--Funzione che controlla che data_in (inizio corso) sia minore di data_fin(fine corso)
CREATE OR REPLACE FUNCTION enforce_corso_data_in_is_minor_than_data_fin()
RETURNS TRIGGER AS $$
BEGIN
        IF NEW.data_in > NEW.data_fin THEN
            RAISE EXCEPTION 'data_in "%" deve essere minore o uguale di data_fin "%"', NEW.data_in, NEW.data_fin;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_corso_data_in_is_minor_than_data_fin
BEFORE INSERT OR UPDATE ON corso
FOR EACH ROW EXECUTE FUNCTION enforce_corso_data_in_is_minor_than_data_fin();

-- Funzione che verifica che il nome del corso non sia già in uso da un corso attivo
CREATE OR REPLACE FUNCTION enforce_unique_nome_corso_attivo()
RETURNS TRIGGER AS $$
BEGIN
    PERFORM 1 FROM corso 
    WHERE nome = NEW.nome 
      AND (data_fin IS NULL OR data_fin >= CURRENT_DATE);
    
    IF FOUND THEN
        RAISE EXCEPTION 'Esiste già un corso attivo con nome "%". Il nome può essere riutilizzato solo dopo il completamento del corso precedente.', NEW.nome;
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_unique_nome_corso_attivo
BEFORE INSERT OR UPDATE ON corso
FOR EACH ROW EXECUTE FUNCTION enforce_unique_nome_corso_attivo();