--View necessaria per controllo posti rimanenti (in pratica)
CREATE OR REPLACE VIEW view_n_studenti_iscritti AS
SELECT 
    p.id_pratica,
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
LEFT JOIN view_n_studenti_iscritti vpp ON p.id_pratica = vpp.id_pratica
GROUP BY p.id_pratica, i.nome, i.unit_misura;



-- View per visualizzare le notifiche inviate da uno chef entro 30 giorni dalla data_creazione
--(se solo_iscritti è true, mostra le notifiche solo agli studenti iscritti al corso)
--(altrimenti mostra le notifiche agli studenti iscritti ad almeno un corso dello chef)
CREATE OR REPLACE VIEW view_notifiche_studente AS
SELECT DISTINCT
    n.id_notifica,
    u.username AS studente_username
FROM notifica n
JOIN utente u ON u.tipo_utente = 'Studente'
WHERE 
    n.data_creazione >= CURRENT_TIMESTAMP - INTERVAL '30 days'
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


-- una view che mostra il numero di ricette svolte per ogni pratica, organizzando i risultati in questo modo:
-- username_chef / id_pratica / numero_ricette_svolte
-- questo è visualizzato dall'inizio del mese fino alla fine del mese corrente

CREATE OR REPLACE VIEW view_numero_ricette_per_sessione AS
SELECT 
    c.chef_id AS username_chef,
    p.id_pratica,
    COUNT(ps.ricetta_id) AS numero_ricette_svolte
FROM pratica p
JOIN corso c ON p.corso_id = c.id_corso
LEFT JOIN pratica_svolta ps ON p.id_pratica = ps.pratica_id
WHERE EXTRACT(YEAR FROM p.giorno_sessione) = EXTRACT(YEAR FROM CURRENT_DATE)
  AND EXTRACT(MONTH FROM p.giorno_sessione) = EXTRACT(MONTH FROM CURRENT_DATE)
GROUP BY c.chef_id, p.id_pratica
ORDER BY p.giorno_sessione DESC, c.chef_id;


-- una view che mostra il numero di corsi e sessioni, organizzando i risultati in questo modo:
-- username_chef / numero_corsi_totali / numero_sessioni_online / numero_sessioni_pratiche
-- questo è visualizzato dall'inizio del mese finoalla fine del mese corrente
CREATE OR REPLACE VIEW view_get_report AS
SELECT
    c.chef_id AS chef_username,
    COUNT(DISTINCT c.id_corso)  AS numero_corsi_totali,
    COUNT(DISTINCT o.id_online)  AS numero_sessioni_online,
    COUNT(DISTINCT p.id_pratica) AS numero_sessioni_pratiche
FROM corso c
LEFT JOIN pratica p ON p.corso_id = c.id_corso 
  AND EXTRACT(YEAR FROM p.giorno_sessione) = EXTRACT(YEAR FROM CURRENT_DATE)
  AND EXTRACT(MONTH FROM p.giorno_sessione) = EXTRACT(MONTH FROM CURRENT_DATE)
LEFT JOIN online o ON o.corso_id = c.id_corso 
  AND EXTRACT(YEAR FROM o.giorno_sessione) = EXTRACT(YEAR FROM CURRENT_DATE)
  AND EXTRACT(MONTH FROM o.giorno_sessione) = EXTRACT(MONTH FROM CURRENT_DATE)
WHERE c.data_in <= (DATE_TRUNC('month', CURRENT_DATE) + INTERVAL '1 month' - INTERVAL '1 day')
  AND c.data_fin >= DATE_TRUNC('month', CURRENT_DATE)
GROUP BY c.chef_id;