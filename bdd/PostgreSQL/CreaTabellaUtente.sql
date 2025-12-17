/* Enum per tipo utente */
DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'enum_u') THEN
        CREATE TYPE enum_u AS ENUM ('Chef','Studente');
    END IF;
END$$;
-- Creazione tabella utente
DROP TABLE IF EXISTS utente;
CREATE TABLE utente (
    username VARCHAR(100) NOT NULL PRIMARY KEY,
    tipo_utente enum_u NOT NULL DEFAULT 'Studente',
    password VARCHAR(255) NOT NULL,
    nome VARCHAR(100),
    cognome VARCHAR(100)
);

COMMENT ON TABLE utente IS 'Registro di tutti gli utenti del sistema (Chef e Studenti)';
COMMENT ON COLUMN utente.username IS 'Identificativo(anche detto "nickname") univoco scelto dall''utente per l''accesso';
COMMENT ON COLUMN utente.tipo_utente IS 'Ruolo assegnato all''utente: può essere ''Chef'' o ''Studente''';
COMMENT ON COLUMN utente.password IS 'Codice di accesso';
COMMENT ON COLUMN utente.nome IS 'Nome di battesimo dell''utente';
COMMENT ON COLUMN utente.cognome IS 'Cognome dell''utente';