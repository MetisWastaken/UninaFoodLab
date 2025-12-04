--Creazione tabella ponte iscritto_c
DROP TABLE IF EXISTS iscritto_c CASCADE;
CREATE TABLE iscritto_c (
    stud_id VARCHAR(100) NOT NULL,
    corso_id INT NOT NULL,
    data_iscrizione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_iscritto_c PRIMARY KEY (stud_id, corso_id),
    CONSTRAINT fk_studente
        FOREIGN KEY (stud_id)
            REFERENCES utente(username) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_corso
        FOREIGN KEY (corso_id)
            REFERENCES corso(id_corso) ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE iscritto_c IS 'Tabella ponte: studenti iscritti ai corsi — PK: (stud_id, corso_id)';
COMMENT ON COLUMN iscritto_c.stud_id IS 'FK -> utente(username); deve riferire un utente con tipo_utente = ''Studente''';
COMMENT ON COLUMN iscritto_c.corso_id IS 'FK -> corso(id_corso)';
COMMENT ON COLUMN iscritto_c.data_iscrizione IS 'Data e ora di iscrizione';

