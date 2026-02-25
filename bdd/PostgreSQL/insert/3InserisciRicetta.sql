--Inserimento ingredienti di esempio
INSERT INTO ingrediente (nome, unit_misura) VALUES
('Farina', 'Kg'),
('Uova', 'Pz'),
('Burro', 'Kg'),
('Cioccolato', 'Kg'),
('Zucchero', 'Kg'),
('Latte', 'L');


--Inserimento ricette di esempio
INSERT INTO ricetta (nome, descrizione) VALUES
('Pasta Fresca', 'Ricetta per preparare la pasta fresca fatta in casa.'),
('Torta al Cioccolato', 'Deliziosa torta al cioccolato per ogni occasione.');


--Inserimento necessita di esempio
INSERT INTO necessita (ricetta_id, ingrediente_id, quant_ing) VALUES
((SELECT id_ricetta FROM ricetta WHERE nome = 'Pasta Fresca'), 'Farina', 0.5),
((SELECT id_ricetta FROM ricetta WHERE nome = 'Pasta Fresca'), 'Uova', 3),
((SELECT id_ricetta FROM ricetta WHERE nome = 'Torta al Cioccolato'), 'Burro', 0.2),
((SELECT id_ricetta FROM ricetta WHERE nome = 'Torta al Cioccolato'), 'Cioccolato', 0.3),
((SELECT id_ricetta FROM ricetta WHERE nome = 'Torta al Cioccolato'), 'Zucchero', 0.25),
((SELECT id_ricetta FROM ricetta WHERE nome = 'Torta al Cioccolato'), 'Latte', 0.1);



 