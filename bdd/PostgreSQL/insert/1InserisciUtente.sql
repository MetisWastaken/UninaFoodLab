--Inserimento utenti di esempio
INSERT INTO utente (username, tipo_utente, password, nome, cognome) VALUES
('GEsposito', 'Chef', 'pizza123', 'Gennaro', 'Esposito'),
('MFerraro', 'Studente', '123456', 'Maria', 'Ferraro'),
('LBianchi', 'Studente', 'qwerty', 'Luca', 'Bianchi')
ON CONFLICT (username) DO NOTHING;

