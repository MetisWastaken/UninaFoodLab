--View necessaria per controllo posti rimanenti (in pratica)
CREATE OR REPLACE VIEW view_pratica_posti AS
SELECT 
    p.id_pratica,
    p.corso_id,
    p.posti_totali,
    COALESCE(COUNT(ip.stud_id), 0) AS posti_occupati,
    (p.posti_totali - COALESCE(COUNT(ip.stud_id), 0)) AS posti_rimanenti
FROM pratica p
LEFT JOIN iscritto_p ip ON p.id_pratica = ip.pratica_id
GROUP BY p.id_pratica, p.corso_id, p.posti_totali;
