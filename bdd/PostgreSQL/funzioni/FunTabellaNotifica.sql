-- Funzione e trigger per garantire che corso_id in notifica faccia riferimento a un corso esistente(se solo_iscritti è TRUE)
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

--Funzione che impedisce l'inserimento di una notifica con titolo uguale per lo stesso chef
CREATE OR REPLACE FUNCTION prevent_notifica_titolo_duplicato()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.titolo IS NOT NULL THEN
        PERFORM 1 FROM notifica WHERE titolo = NEW.titolo AND  username_chef = NEW.username_chef AND id_notifica <> NEW.id_notifica;
        IF FOUND THEN
            RAISE EXCEPTION 'Esiste gia'' una notifica con il titolo "%" (per questo chef)', NEW.titolo;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_prevent_notifica_titolo_duplicato
BEFORE INSERT OR UPDATE ON notifica
FOR EACH ROW
EXECUTE FUNCTION prevent_notifica_titolo_duplicato();

--Funzione e trigger che impedisce la creazione di nuna notifica se il corso_id non e' associato allo chef che sta creando la notifica
CREATE OR REPLACE FUNCTION enforce_notifica_corso_belongs_to_chef()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.corso_id IS NOT NULL THEN
        PERFORM 1 FROM corso WHERE id_corso = NEW.corso_id AND chef_id = NEW.username_chef;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'Il corso con id "%" non e'' associato allo chef "%"', NEW.corso_id, NEW.username_chef;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_enforce_notifica_corso_belongs_to_chef
BEFORE INSERT OR UPDATE ON notifica
FOR EACH ROW
EXECUTE FUNCTION enforce_notifica_corso_belongs_to_chef();