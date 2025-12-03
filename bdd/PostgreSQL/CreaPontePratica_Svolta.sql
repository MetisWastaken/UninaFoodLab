DROP TABLE IF EXISTS pratica_svolta CASCADE;
CREATE TABLE pratica_svolta
(
    pratica_id INT NOT NULL,
    ricetta_id INT NOT NULL,
    
    CONSTRAINT pk_pratica_svolta PRIMARY KEY (pratica_id, ricetta_id),
    CONSTRAINT fk_pratica
        FOREIGN KEY(pratica_id)
            REFERENCES pratica(id_pratica) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_ricetta
        FOREIGN KEY(ricetta_id)
            REFERENCES ricetta(id_ricetta) ON DELETE CASCADE ON UPDATE CASCADE
);
COMMENT ON TABLE pratica_svolta IS 'Tabella ponte tra pratica e ricetta, memorizza le ricette svolte in una sessione pratica';
COMMENT ON COLUMN pratica_svolta.pratica_id IS 'FK -> pratica(id_pratica)';
COMMENT ON COLUMN pratica_svolta.ricetta_id IS 'FK -> ricetta(id_ricetta)'; 

-- Funzione che verifica che pratica_id punti a una sessione pratica esistente e che ricetta_id punti a una ricetta esistente
CREATE OR REPLACE FUNCTION enforce_pratica_svolta_checks()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.pratica_id IS NOT NULL THEN
        PERFORM 1 FROM pratica WHERE id_pratica = NEW.pratica_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'pratica_id "%" non corrisponde a una sessione pratica esistente', NEW.pratica_id;
        END IF;
    END IF;

    IF NEW.ricetta_id IS NOT NULL THEN
        PERFORM 1 FROM ricetta WHERE id_ricetta = NEW.ricetta_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'ricetta_id "%" non corrisponde a una ricetta esistente', NEW.ricetta_id;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger per applicare la funzione enforce_pratica_svolta_checks prima di ogni inserimento o aggiornamento
CREATE TRIGGER trg_enforce_pratica_svolta_checks
BEFORE INSERT OR UPDATE ON pratica_svolta
FOR EACH ROW EXECUTE FUNCTION enforce_pratica_svolta_checks();