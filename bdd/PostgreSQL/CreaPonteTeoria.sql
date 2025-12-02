-- Creazione tabella ponte teoria
CREATE TABLE teoria (
    online_id INT NOT NULL,
    ricetta_id INT NOT NULL,

    CONSTRAINT pk_teoria PRIMARY KEY (online_id, ricetta_id),
    CONSTRAINT fk_online
        FOREIGN KEY(online_id)
            REFERENCES online(id_online) ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT fk_ricetta_teoria
        FOREIGN KEY(ricetta_id)
            REFERENCES ricetta(id_ricetta) ON DELETE CASCADE ON UPDATE CASCADE
);

--crea vincoli domani