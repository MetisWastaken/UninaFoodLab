--Procedura per l'inserimento di un nuovo record nella tabella pratica
CREATE OR REPLACE PROCEDURE InsertPratica(
    p_giorno_sessione DATE,
    p_aula VARCHAR(50),
    p_posti_totali INTEGER,
    p_corso_id INTEGER
)LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO pratica (giorno_sessione, aula, posti_totali, corso_id)
    VALUES (p_giorno_sessione, p_aula, p_posti_totali, p_corso_id);
END;
$$;

--Procedura per l'aggiornamento di un record esistente nella tabella pratica
CREATE OR REPLACE PROCEDURE UpdatePratica(
    p_id_pratica INTEGER,
    p_giorno_sessione DATE,
    p_aula VARCHAR(50),
    p_posti_totali INTEGER,
    p_corso_id INTEGER
)LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE pratica
    SET giorno_sessione = p_giorno_sessione,
        aula = p_aula,
        posti_totali = p_posti_totali,
        corso_id = p_corso_id
    WHERE id_pratica = p_id_pratica;
END;
$$;

--Proceduce per la cancellazione di un record dalla tabella pratica
CREATE OR REPLACE PROCEDURE DeletePratica(
    p_id_pratica INTEGER
)LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM pratica
    WHERE id_pratica = p_id_pratica;
END;
$$;