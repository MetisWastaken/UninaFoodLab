--inserimento online di esempio
INSERT INTO online (giorno_sessione, codice_meeting, corso_id) 
VALUES ('2026-02-26', 'MEET12345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasta Fresca')),
    ('2026-07-17', 'MEET12346', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasta Fresca')),
    ('2026-07-09', 'MEET22345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasticceria Base')),
    ('2026-07-19', 'MEET22346', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasticceria Base'));



--inserimento teoria di esempio
INSERT INTO teoria (online_id, ricetta_id) 
VALUES ((SELECT id_online FROM online WHERE giorno_sessione = '2026-02-26'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Pasta Fresca')),
    ((SELECT id_online FROM online WHERE giorno_sessione = '2026-02-26'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Torta al Cioccolato')),
    ((SELECT id_online FROM online WHERE giorno_sessione = '2026-07-09'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Pasta Fresca')),
    ((SELECT id_online FROM online WHERE giorno_sessione = '2026-07-19'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Torta al Cioccolato'));
