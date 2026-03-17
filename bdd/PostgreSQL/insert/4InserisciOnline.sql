--inserimento online di esempio
INSERT INTO online (giorno_sessione, codice_meeting, corso_id) 
VALUES (CURRENT_DATE + INTERVAL '1 days', 'MEET12345', (SELECT id_corso FROM corso WHERE nome = 'Pasta Fresca')),
    (CURRENT_DATE + INTERVAL '143 days', 'MEET12346', (SELECT id_corso FROM corso WHERE nome = 'Pasta Fresca')),
    (CURRENT_DATE + INTERVAL '135 days', 'MEET22345', (SELECT id_corso FROM corso WHERE nome = 'Pasticceria Base')),
    (CURRENT_DATE + INTERVAL '145 days', 'MEET22346', (SELECT id_corso FROM corso WHERE nome = 'Pasticceria Base')),
    (CURRENT_DATE + INTERVAL '12 days', 'MEET32345', (SELECT id_corso FROM corso WHERE nome = 'Cucina per Novizi')),
    (CURRENT_DATE + INTERVAL '155 days', 'MEET42345', (SELECT id_corso FROM corso WHERE nome = 'Cucina Tradizionale')),
    (CURRENT_DATE + INTERVAL '17 days', 'MEET52345', (SELECT id_corso FROM corso WHERE nome = 'Cucina Vegetariana')),
    (CURRENT_DATE + INTERVAL '32 days', 'MEET62345', (SELECT id_corso FROM corso WHERE nome = 'Cucina Vegana')),
    (CURRENT_DATE + INTERVAL '110 days', 'MEET72345', (SELECT id_corso FROM corso WHERE nome = 'Sfilettatura')),
    (CURRENT_DATE + INTERVAL '111 days', 'MEET72346', (SELECT id_corso FROM corso WHERE nome = 'Sfilettatura')),
    (CURRENT_DATE + INTERVAL '42 days', 'MEET82345', (SELECT id_corso FROM corso WHERE nome = 'Cucina Celiaca')),
    (CURRENT_DATE + INTERVAL '43 days', 'MEET82346', (SELECT id_corso FROM corso WHERE nome = 'Cucina Celiaca')),
    (CURRENT_DATE + INTERVAL '57 days', 'MEET92345', (SELECT id_corso FROM corso WHERE nome = 'Cucina per Diabetici')),
    (CURRENT_DATE + INTERVAL '58 days', 'MEET92346', (SELECT id_corso FROM corso WHERE nome = 'Cucina per Diabetici')),
    (CURRENT_DATE + INTERVAL '14 days', 'MEET102345', (SELECT id_corso FROM corso WHERE nome = 'Cucina per Novizi')),
    (CURRENT_DATE + INTERVAL '13 days', 'MEET102346', (SELECT id_corso FROM corso WHERE nome = 'Cucina per Novizi')),
    (CURRENT_DATE + INTERVAL '37 days', 'MEET112345', (SELECT id_corso FROM corso WHERE nome = 'Cucina per Anziani')),
    (CURRENT_DATE + INTERVAL '38 days', 'MEET112346', (SELECT id_corso FROM corso WHERE nome = 'Cucina per Anziani')),
    (CURRENT_DATE + INTERVAL '157 days', 'MEET122345', (SELECT id_corso FROM corso WHERE nome = 'Cucina Tradizionale')),
    (CURRENT_DATE + INTERVAL '156 days', 'MEET122346', (SELECT id_corso FROM corso WHERE nome = 'Cucina Tradizionale'));   



--inserimento teoria di esempio
INSERT INTO teoria (online_id, ricetta_id) 
VALUES ((SELECT id_online FROM online WHERE codice_meeting = 'MEET12345'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Pasta Fresca')),
    ((SELECT id_online FROM online WHERE codice_meeting = 'MEET12345'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Torta al Cioccolato')),
    ((SELECT id_online FROM online WHERE codice_meeting = 'MEET22345'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Pasta Fresca')),
    ((SELECT id_online FROM online WHERE codice_meeting = 'MEET22346'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Torta al Cioccolato'));
