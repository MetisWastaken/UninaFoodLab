--Inserimento corsi di esempio
INSERT INTO corso (nome,categoria,data_in,data_fin,frequenza_settimanale,chef_id) VALUES
('Corso di Pasta Fresca','Cucina Italiana',CURRENT_DATE + INTERVAL '7 days','2026-07-31','Lunedi, Mercoledi','GEsposito'),
('Corso di Pasticceria Base','Dolci e Dessert','2026-07-05','2026-08-30','Martedi, Giovedi','GEsposito');


--Inserimento iscritto_c di esempio
INSERT INTO iscritto_c (stud_id,corso_id,data_iscrizione) VALUES
('MFerraro', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasta Fresca'), CURRENT_DATE + INTERVAL '1 days'),
('LBianchi', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasta Fresca'), CURRENT_DATE + INTERVAL '1 days'),
('MFerraro', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasticceria Base'), '2026-06-28');
