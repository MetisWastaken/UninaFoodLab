--Inserimento corsi di esempio
INSERT INTO corso (nome,categoria,data_in,data_fin,frequenza_settimanale,chef_id) VALUES
('Corso di Pasta Fresca','Cucina Italiana',CURRENT_DATE + INTERVAL '1 days',CURRENT_DATE + INTERVAL '157 days','Lunedi, Mercoledi','GEsposito'),
('Corso di Pasticceria Base','Dolci e Dessert',CURRENT_DATE + INTERVAL '131 days',CURRENT_DATE + INTERVAL '188 days','Martedi, Giovedi','GEsposito');


--Inserimento iscritto_c di esempio
INSERT INTO iscritto_c (stud_id,corso_id,data_iscrizione) VALUES
('MFerraro', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasta Fresca'), CURRENT_DATE + INTERVAL '1 days'),
('LBianchi', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasta Fresca'), CURRENT_DATE + INTERVAL '1 days'),
('MFerraro', (SELECT id_corso FROM corso WHERE nome = 'Corso di Pasticceria Base'), CURRENT_DATE + INTERVAL '124 days');
