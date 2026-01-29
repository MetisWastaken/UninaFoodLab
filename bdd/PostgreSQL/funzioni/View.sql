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
    SUM(n.quant_ing * COALESCE(NULLIF(vpp.posti_occupati, 0), 1)) AS quantita,
    i.unit_misura
FROM pratica p
JOIN pratica_svolta ps ON p.id_pratica = ps.pratica_id
JOIN ricetta r ON ps.ricetta_id = r.id_ricetta
JOIN necessita n ON r.id_ricetta = n.ricetta_id
JOIN ingrediente i ON n.ingrediente_id = i.nome
LEFT JOIN view_studenti_iscritti vpp ON p.id_pratica = vpp.id_pratica
GROUP BY p.id_pratica, i.nome, i.unit_misura;

-- View per visualizzare le notifiche inviate da uno chef entro 10 giorni dalla data_creazione
--(se solo_iscritti è true, mostra le notifiche solo agli studenti iscritti al corso)
--(altrimenti mostra le notifiche agli studenti iscritti ad almeno un corso dello chef)
CREATE OR REPLACE VIEW view_notifiche_studente AS
SELECT DISTINCT
    n.id_notifica,
    u.username AS studente_username
FROM notifica n
JOIN utente u ON u.tipo_utente = 'Studente'
WHERE 
    n.data_creazione >= CURRENT_TIMESTAMP - INTERVAL '10 days'
    AND (
        -- Caso 1: solo_iscritti = TRUE, mostra solo agli iscritti al corso specifico
        (
            n.solo_iscritti = TRUE
            AND n.corso_id IS NOT NULL
            AND EXISTS (
                SELECT 1 FROM iscritto_c ic 
                WHERE ic.corso_id = n.corso_id AND ic.stud_id = u.username
            )
        )
        OR
        (
            -- Caso 2: solo_iscritti = FALSE, mostra agli iscritti ad almeno un corso dello chef
            n.solo_iscritti = FALSE
            AND EXISTS (
                SELECT 1 FROM iscritto_c ic
                JOIN corso c ON ic.corso_id = c.id_corso
                WHERE ic.stud_id = u.username AND c.chef_id = n.username_chef
            )
        )
    );