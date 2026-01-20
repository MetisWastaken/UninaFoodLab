--trigger che controlla che il nome dell'ingrediente sia univoco
CREATE OR REPLACE FUNCTION check_ingrediente_nome_univoco()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM ingrediente WHERE nome = NEW.nome AND nome <> OLD.nome) THEN
        RAISE EXCEPTION 'Errore: esiste gia'' un ingrediente con il nome %', NEW.nome;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_ingrediente_nome_univoco
BEFORE INSERT OR UPDATE ON ingrediente
FOR EACH ROW EXECUTE FUNCTION check_ingrediente_nome_univoco();

--trigger che assicura che il nome dell'ingrediente sia valido (non numeri,simboli,spazi o vuoto)
CREATE OR REPLACE FUNCTION check_ingrediente_nome_valido()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.nome !~ '^[A-Za-zàáâãäåèéêëìíîïòóôõöùúûüñç]+$' THEN
        RAISE EXCEPTION 'Errore: il nome dell''ingrediente % non e'' valido. Deve contenere solo lettere.', NEW.nome;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_ingrediente_nome_valido
BEFORE INSERT OR UPDATE ON ingrediente
FOR EACH ROW EXECUTE FUNCTION check_ingrediente_nome_valido();