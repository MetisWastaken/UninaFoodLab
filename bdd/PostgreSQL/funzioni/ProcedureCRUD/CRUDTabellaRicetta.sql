--Procedura per la creazione di una nuova ricetta
CREATE OR REPLACE PROCEDURE create_ricetta(
    p_nome VARCHAR,
    p_descrizione TEXT
)LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO ricetta (nome, descrizione)
    VALUES (p_nome, p_descrizione);
END;
$$;
--Funzione per la lettura delle informazioni di una ricetta
CREATE OR REPLACE FUNCTION read_ricetta(p_nome VARCHAR)
RETURNS SETOF ricetta
LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM ricetta WHERE nome = p_nome) THEN
        RAISE EXCEPTION 'Ricetta % non trovata.', p_nome;
    END IF;
    RETURN QUERY SELECT * FROM ricetta WHERE nome = p_nome;
END;
$$;


--Procedura per l'aggiornamento delle informazioni di una ricetta
CREATE OR REPLACE PROCEDURE update_ricetta(
    p_nome VARCHAR,
    n_descrizione TEXT
)LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM ricetta WHERE nome = p_nome) THEN
        RAISE EXCEPTION 'Ricetta % non trovata.', p_nome;
    END IF;
    UPDATE ricetta
    SET descrizione = n_descrizione
    WHERE nome = p_nome;
END;
$$;

--Procedura per la cancellazione di una ricetta
CREATE OR REPLACE PROCEDURE delete_ricetta(
    p_nome VARCHAR
)LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM ricetta WHERE nome = p_nome) THEN
        RAISE EXCEPTION 'Ricetta % non trovata.', p_nome;
    END IF;
    DELETE FROM ricetta WHERE nome = p_nome;
END;
$$;