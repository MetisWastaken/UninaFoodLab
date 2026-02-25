--inserimento online di esempio
INSERT INTO online (giorno_sessione, codice_meeting, corso_id) 
VALUES (CURRENT_DATE + INTERVAL '1 days', 'MEET12345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasta Fresca')),
    (CURRENT_DATE + INTERVAL '143 days', 'MEET12346', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasta Fresca')),
    (CURRENT_DATE + INTERVAL '135 days', 'MEET22345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasticceria Base')),
    (CURRENT_DATE + INTERVAL '145 days', 'MEET22346', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasticceria Base'));



--inserimento teoria di esempio
INSERT INTO teoria (online_id, ricetta_id) 
VALUES ((SELECT id_online FROM online WHERE giorno_sessione = CURRENT_DATE + INTERVAL '1 days'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Pasta Fresca')),
    ((SELECT id_online FROM online WHERE giorno_sessione = CURRENT_DATE + INTERVAL '1 days'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Torta al Cioccolato')),
    ((SELECT id_online FROM online WHERE giorno_sessione = CURRENT_DATE + INTERVAL '135 days'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Pasta Fresca')),
    ((SELECT id_online FROM online WHERE giorno_sessione = CURRENT_DATE + INTERVAL '145 days'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Torta al Cioccolato'));
