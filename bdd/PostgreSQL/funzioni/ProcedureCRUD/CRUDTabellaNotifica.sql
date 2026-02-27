--Procedura per l'inserimento di un nuovo record nella tabella notifica
CREATE OR REPLACE PROCEDURE InsertNotifica(
    p_titolo VARCHAR(255),
    p_messaggio TEXT,
    p_solo_iscritti BOOLEAN,
    p_data_creazione TIMESTAMP(0),
    p_username_chef VARCHAR(100),
    p_corso_id INTEGER
)LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO notifica (titolo, messaggio, solo_iscritti, data_creazione, username_chef, corso_id)
    VALUES (p_titolo, p_messaggio, p_solo_iscritti, p_data_creazione, p_username_chef, p_corso_id);
END;
$$;

--Procedura per l'eliminazione di un record dalla tabella notifica
CREATE OR REPLACE PROCEDURE DeleteNotifica(
    p_id_notifica INTEGER
)LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM notifica
    WHERE id_notifica = p_id_notifica;
END;
$$;