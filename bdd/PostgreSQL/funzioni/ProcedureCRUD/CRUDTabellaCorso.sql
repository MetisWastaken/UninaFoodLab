-- procedura per l'inserimento di un nuovo record nella tabella corso
CREATE OR REPLACE PROCEDURE InsertCorso(
    p_nome VARCHAR(200),
    p_categoria VARCHAR(100),
    p_data_in DATE,
    p_data_fin DATE,
    p_frequenza_settimanale VARCHAR(100),
    p_chef_id VARCHAR(100)
)LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO corso (nome, categoria, data_in, data_fin, frequenza_settimanale, chef_id)
    VALUES (p_nome, p_categoria, p_data_in, p_data_fin, p_frequenza_settimanale, p_chef_id);
END;
$$;