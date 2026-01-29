DROP TABLE IF EXISTS notifica;
CREATE TABLE notifica(
    id_notifica SERIAL PRIMARY KEY,
    titolo VARCHAR(255) NOT NULL DEFAULT 'Notifica di sistema',
    messaggio TEXT NOT NULL DEFAULT 'Ci sono aggiornamenti nei tuoi corsi.',
    solo_iscritti BOOLEAN NOT NULL DEFAULT FALSE,
    data_creazione TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    username_chef VARCHAR(100) NOT NULL
        REFERENCES utente(username) ON UPDATE CASCADE ON DELETE RESTRICT,
    corso_id INT
        REFERENCES corso(id_corso) ON UPDATE CASCADE ON DELETE RESTRICT
);

COMMENT ON TABLE notifica IS 'Tabella che memorizza le notifiche inviate dagli Chef agli studenti';
COMMENT ON COLUMN notifica.id_notifica IS 'Identificativo numerico univoco della notifica (autoincrementato)';
COMMENT ON COLUMN notifica.titolo IS 'Titolo breve della notifica';
COMMENT ON COLUMN notifica.messaggio IS 'Contenuto dettagliato della notifica';
COMMENT ON COLUMN notifica.solo_iscritti IS 'Indica se la notifica è destinata solo agli studenti iscritti al corso associato';
COMMENT ON COLUMN notifica.data_creazione IS 'Data e ora di creazione della notifica';
COMMENT ON COLUMN notifica.username_chef IS 'Riferimento allo Chef che ha creato la notifica';
COMMENT ON COLUMN notifica.corso_id IS 'Riferimento al corso associato alla notifica, se applicabile';