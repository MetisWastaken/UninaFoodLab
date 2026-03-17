--inserimento online di esempio
INSERT INTO online (giorno_sessione, codice_meeting, corso_id) 
VALUES (CURRENT_DATE + INTERVAL '1 days', 'MEET12345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasta Fresca')),
    (CURRENT_DATE + INTERVAL '143 days', 'MEET12346', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasta Fresca')),
    (CURRENT_DATE + INTERVAL '135 days', 'MEET22345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasticceria Base')),
    (CURRENT_DATE + INTERVAL '145 days', 'MEET22346', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasticceria Base')),
    (CURRENT_DATE + INTERVAL '12 days', 'MEET32345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina per Principianti')),
    (CURRENT_DATE + INTERVAL '155 days', 'MEET42345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina Tradizionale')),
    (CURRENT_DATE + INTERVAL '155 days', 'MEET42346', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina Tradizionale')),
    (CURRENT_DATE + INTERVAL '17 days', 'MEET52345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina Vegetariana')),
    (CURRENT_DATE + INTERVAL '17 days', 'MEET52346', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina Vegetariana')),
    (CURRENT_DATE + INTERVAL '32 days', 'MEET62345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina Vegana')),
    (CURRENT_DATE + INTERVAL '32 days', 'MEET62346', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina Vegana')),
    (CURRENT_DATE + INTERVAL '110 days', 'MEET72345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Sfilettatura')),
    (CURRENT_DATE + INTERVAL '110 days', 'MEET72346', (SELECT id_corso FROM corso WHERE nome = 'Corso di Sfilettatura')),
    (CURRENT_DATE + INTERVAL '42 days', 'MEET82345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina Celiaca')),
    (CURRENT_DATE + INTERVAL '42 days', 'MEET82346', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina Celiaca')),
    (CURRENT_DATE + INTERVAL '57 days', 'MEET92345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina per Diabetici')),
    (CURRENT_DATE + INTERVAL '57 days', 'MEET92346', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina per Diabetici')),
    (CURRENT_DATE + INTERVAL '12 days', 'MEET102345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina per Principianti')),
    (CURRENT_DATE + INTERVAL '12 days', 'MEET102346', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina per Principianti')),
    (CURRENT_DATE + INTERVAL '37 days', 'MEET112345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina per Anziani')),
    (CURRENT_DATE + INTERVAL '37 days', 'MEET112346', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina per Anziani')),
    (CURRENT_DATE + INTERVAL '155 days', 'MEET122345', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina Tradizionale')),
    (CURRENT_DATE + INTERVAL '155 days', 'MEET122346', (SELECT id_corso FROM corso WHERE nome = 'Corso di Cucina Tradizionale'));   



--inserimento teoria di esempio
INSERT INTO teoria (online_id, ricetta_id) 
VALUES ((SELECT id_online FROM online WHERE giorno_sessione = CURRENT_DATE + INTERVAL '1 days'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Pasta Fresca')),
    ((SELECT id_online FROM online WHERE giorno_sessione = CURRENT_DATE + INTERVAL '1 days'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Torta al Cioccolato')),
    ((SELECT id_online FROM online WHERE giorno_sessione = CURRENT_DATE + INTERVAL '135 days'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Pasta Fresca')),
    ((SELECT id_online FROM online WHERE giorno_sessione = CURRENT_DATE + INTERVAL '145 days'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Torta al Cioccolato'));
