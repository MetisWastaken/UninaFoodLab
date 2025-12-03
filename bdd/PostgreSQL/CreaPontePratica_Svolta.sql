DROP TABLE IF EXISTS pratica_svolta CASCADE;
CREATE TABLE pratica_svolta
(
    pratica_id INT NOT NULL,
    ricetta_id INT NOT NULL,
    
    PRIMARY KEY (pratica_id, ricetta_id),
        FOREIGN KEY(pratica_id) REFERENCES pratica(id_pratica) ON DELETE CASCADE,
        FOREIGN KEY(ricetta_id) REFERENCES ricetta(id_ricetta) ON DELETE CASCADE
);
