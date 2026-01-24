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