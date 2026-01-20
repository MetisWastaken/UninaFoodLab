--View necessaria per controllo posti rimanenti (in pratica)
CREATE OR REPLACE VIEW view_studenti_iscritti AS
SELECT 
    p.id_pratica,
    p.corso_id,
    p.posti_totali,
    COALESCE(COUNT(ip.stud_id), 0) AS posti_occupati,
    (p.posti_totali - COALESCE(COUNT(ip.stud_id), 0)) AS posti_rimanenti
FROM pratica p
LEFT JOIN iscritto_p ip ON p.id_pratica = ip.pratica_id
GROUP BY p.id_pratica, p.corso_id, p.posti_totali;

--view per visualizzare il totale di ingredienti utilizzati per ogni pratica
--(somma degli ingredienti di tutte le ricette associate alla pratica)
--(moltiplicato per il numero di iscritti alla pratica)
CREATE OR REPLACE VIEW view_ingredienti_pratica AS
SELECT 
    p.id_pratica,
    i.nome AS ingrediente_nome,
    SUM(n.quant_ing * COALESCE(NULLIF(vpp.posti_occupati, 0), 1)) AS quantita
FROM pratica p
JOIN pratica_svolta ps ON p.id_pratica = ps.pratica_id
JOIN ricetta r ON ps.ricetta_id = r.id_ricetta
JOIN necessita n ON r.id_ricetta = n.ricetta_id
JOIN ingrediente i ON n.ingrediente_id = i.nome
LEFT JOIN view_studenti_iscritti vpp ON p.id_pratica = vpp.id_pratica
GROUP BY p.id_pratica, i.nome;
