--Creazione tabella ponte iscritto_p 
DROP TABLE IF EXISTS iscritto_p CASCADE;
CREATE TABLE iscritto_p (
    stud_id VARCHAR(100) NOT NULL,
    pratica_id INT NOT NULL,
    CONSTRAINT pk_iscritto_p PRIMARY KEY (stud_id, pratica_id),
    CONSTRAINT fk_studente
        FOREIGN KEY (stud_id)
            REFERENCES utente(username) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_pratica
        FOREIGN KEY (pratica_id)
            REFERENCES pratica(id_pratica) ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE iscritto_p IS 'Tabella ponte rappresentante gli studenti iscritti alla sessione pratica';
COMMENT ON COLUMN iscritto_p.stud_id IS 'id dello studente che si iscrive alla sessione pratica';
COMMENT ON COLUMN iscritto_p.pratica_id IS 'id della sessione pratica a cui lo studente si iscrive';

