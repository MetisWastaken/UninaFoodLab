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

COMMENT ON TABLE iscritto_c IS 'Registra le iscrizioni degli studenti ai corsi. Uno studente può iscriversi a più corsi e un corso può avere più studenti iscritti';
COMMENT ON COLUMN iscritto_c.stud_id IS 'Username dello studente iscritto al corso (deve essere un utente di tipo Studente)';
COMMENT ON COLUMN iscritto_c.corso_id IS 'Riferimento al corso a cui lo studente si è iscritto';
COMMENT ON COLUMN iscritto_c.data_iscrizione IS 'Momento esatto in cui lo studente si è iscritto al corso (impostato automaticamente al momento dell''inserimento)';

