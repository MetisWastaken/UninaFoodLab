 --inserimento pratica di esempio
INSERT INTO pratica (giorno_sessione, aula, corso_id, posti_totali) 
VALUES (CURRENT_DATE + INTERVAL '2 days', 'Aula 101', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasta Fresca'), 30),
    (CURRENT_DATE + INTERVAL '4 days', 'Aula 102', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasta Fresca'), 25),
    (CURRENT_DATE + INTERVAL '136 days', 'Aula 201', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasticceria Base'), 20),
    (CURRENT_DATE + INTERVAL '138 days', 'Aula 202', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasticceria Base'), 15);

--update data d'inizio corso
UPDATE corso SET data_in = CURRENT_DATE + INTERVAL '1 days' WHERE nome = 'Corso di Pasta Fresca';
UPDATE corso SET data_in = CURRENT_DATE + INTERVAL '1 days' WHERE nome = 'Corso di Pasticceria Base';

--inserimento iscritto_p di esempio
INSERT INTO iscritto_p (stud_id, pratica_id) 
VALUES ('MFerraro', (SELECT id_pratica FROM pratica WHERE giorno_sessione = CURRENT_DATE + INTERVAL '2 days' AND aula = 'Aula 101')),
    ('MFerraro', (SELECT id_pratica FROM pratica WHERE giorno_sessione = CURRENT_DATE + INTERVAL '4 days' AND aula = 'Aula 102')),
    ('MFerraro', (SELECT id_pratica FROM pratica WHERE giorno_sessione = CURRENT_DATE + INTERVAL '136 days' AND aula = 'Aula 201')),
    ('LBianchi', (SELECT id_pratica FROM pratica WHERE giorno_sessione = CURRENT_DATE + INTERVAL '4 days' AND aula = 'Aula 102'));

--inserimento pratica_svolta di esempio
INSERT INTO pratica_svolta (pratica_id, ricetta_id)
VALUES ((SELECT id_pratica FROM pratica WHERE giorno_sessione = CURRENT_DATE + INTERVAL '2 days' AND aula = 'Aula 101'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Pasta Fresca')),
    ((SELECT id_pratica FROM pratica WHERE giorno_sessione = CURRENT_DATE + INTERVAL '4 days' AND aula = 'Aula 102'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Pasta Fresca')),
    ((SELECT id_pratica FROM pratica WHERE giorno_sessione = CURRENT_DATE + INTERVAL '2 days' AND aula = 'Aula 101'), (SELECT id_ricetta FROM ricetta WHERE nome = 'Torta al Cioccolato'));