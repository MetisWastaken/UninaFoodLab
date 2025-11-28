/* Enum per tipo utente */
DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'enum_u') THEN
        CREATE TYPE enum_u AS ENUM ('Chef','Studente');
    END IF;
END$$;

-- Tabella utente
DROP TABLE IF EXISTS utente;
CREATE TABLE utente (
    username VARCHAR(100) NOT NULL PRIMARY KEY,
    tipo_utente enum_u NOT NULL DEFAULT 'Studente',
    password VARCHAR(255) NOT NULL,
    nome VARCHAR(100),
    cognome VARCHAR(100)
);

COMMENT ON TABLE utente IS 'Tabella utenti — PK: username';
COMMENT ON COLUMN utente.username IS 'Primary key: username';
COMMENT ON COLUMN utente.tipo_utente IS 'Tipo utente: enum_u (Chef, Studente)';
COMMENT ON COLUMN utente.password IS 'Password (salvatela hashed)';
COMMENT ON COLUMN utente.nome IS 'Nome';
COMMENT ON COLUMN utente.cognome IS 'Cognome';
