--Inserimento utenti di esempio
INSERT INTO utente (username, tipo_utente, password, nome, cognome) VALUES
('GEsposito', 'Chef', 'pizza123', 'Gennaro', 'Esposito'),
('MFerraro', 'Studente', '123456', 'Maria', 'Ferraro'),
('LBianchi', 'Studente', 'qwerty', 'Luca', 'Bianchi'),
('ASmith', 'Chef', 'chef2024', 'Alice', 'Smith'),
('JDoe', 'Studente', 'password', 'John', 'Doe'),
('RVerdi', 'Studente', 'verde2024', 'Roberto', 'Verdi'),
('CDeMarco', 'Studente', 'Carl2024', 'Carla', 'DeMarco'),
('MS Rossi', 'Studente', 'Rossi2024', 'Marco', 'Rossi'),
('FBruno', 'Studente', 'Bruno0000', 'Francesca', 'Bruno'),
('LVerdi', 'Studente', 'verde20232024', 'Laura', 'Verdi')
ON CONFLICT (username) DO NOTHING;

