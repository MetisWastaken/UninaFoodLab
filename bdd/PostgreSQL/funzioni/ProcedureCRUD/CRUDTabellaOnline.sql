--Procedura per l'inserimento di un nuovo record nella tabella online
CREATE OR REPLACE PROCEDURE InsertOnline(
    p_giorno_sessione DATE,
    p_codice_meeting VARCHAR(20),
    p_corso_id INTEGER
)LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO online (giorno_sessione, codice_meeting, corso_id)
    VALUES (p_giorno_sessione, p_codice_meeting, p_corso_id);
END;
$$;

--Procedura per l'aggiornamento di un record esistente nella tabella online
CREATE OR REPLACE PROCEDURE UpdateOnline(
    p_id_online INTEGER,
    p_giorno_sessione DATE,
    p_codice_meeting VARCHAR(20),
    p_corso_id INTEGER
)LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE online
    SET giorno_sessione = p_giorno_sessione,
        codice_meeting = p_codice_meeting,
        corso_id = p_corso_id
    WHERE id_online = p_id_online;
END;
$$;

--Procedura per la cancellazione di un record dalla tabella online
CREATE OR REPLACE PROCEDURE DeleteOnline(
    p_id_online INTEGER
)LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM online
    WHERE id_online = p_id_online;
END;
$$;