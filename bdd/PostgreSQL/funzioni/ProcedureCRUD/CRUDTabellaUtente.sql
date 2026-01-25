--Procedura per la creazione di un nuovo utente
CREATE OR REPLACE PROCEDURE create_utente(
    p_username VARCHAR,
    p_tipo_utente enum_u,
    p_password VARCHAR,
    p_nome VARCHAR,
    p_cognome VARCHAR
)LANGUAGE plpgsql
AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM utente WHERE username = p_username) THEN
        RAISE EXCEPTION 'Username % già esistente.', p_username;
    END IF;
    INSERT INTO utente (username, tipo_utente, password, nome, cognome)
    VALUES (p_username, p_tipo_utente, p_password, p_nome, p_cognome);
END;
$$;

--Procedura per la lettura delle informazioni di un utente
CREATE OR REPLACE PROCEDURE read_utente(p_username VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM utente WHERE username = p_username) THEN
        RAISE EXCEPTION 'Username % non trovato.', p_username;
    END IF;
    PERFORM * FROM utente WHERE username = p_username;
END;
$$;

--Procedura per l'aggiornamento delle informazioni di un utente
CREATE OR REPLACE PROCEDURE update_utente(
    p_username VARCHAR,
    v_password VARCHAR,
    n_password VARCHAR
)LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM utente WHERE username = p_username) THEN
        RAISE EXCEPTION 'Username % non trovato.', p_username;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM utente WHERE username = p_username AND password = v_password) THEN
        RAISE EXCEPTION 'Password attuale non corretta per l''utente %.', p_username;
    END IF;
    UPDATE utente
    SET password = n_password
    WHERE username = p_username;
END;
$$;

--Procedura per la cancellazione di un utente
CREATE OR REPLACE PROCEDURE delete_utente(
    p_username VARCHAR, 
    p_password VARCHAR
)language plpgsql
AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM utente WHERE username = p_username) THEN
        RAISE EXCEPTION 'Username % non trovato.', p_username;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM utente WHERE username = p_username AND password = p_password) THEN
        RAISE EXCEPTION 'Password non corretta per l''utente %.', p_username;
    END IF;
    DELETE FROM utente WHERE username = p_username;
END;
$$;