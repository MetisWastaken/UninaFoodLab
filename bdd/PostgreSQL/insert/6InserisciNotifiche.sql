
-- Notifica globale (destinata a TUTTI gli studenti)
INSERT INTO notifica (titolo, messaggio, solo_iscritti, username_chef, corso_id) 
VALUES (
    'Aggiornamento importante da Chef Gennaro',
    'Cari studenti, durante il consiglio, abbiamo apportato alcuni aggiornamenti importanti riguardanti i prossimi corsi, Vi invito a rimanere attenti alle comunicazioni future per ulteriori dettagli.',
    FALSE, 
    'GEsposito',
    NULL    
);

-- Notifica solo iscritti
INSERT INTO notifica (titolo, messaggio, solo_iscritti, username_chef, corso_id) 
VALUES (
    'Avviso per gli Iscritti al corso di "Pasta Fresca"',
    'Carissimi aspiranti cuochi, vi ricordo di portare da casa il vostro grembiule personale e retina per capelli, non saranno concessi scambi di materiale.',
    TRUE, 
    'GEsposito',
    (SELECT id_corso FROM corso WHERE nome = 'Pasta Fresca')
);

-- Notifica globale (destinata a TUTTI gli studenti)
INSERT INTO notifica (titolo, messaggio, solo_iscritti, username_chef, corso_id) 
VALUES (
    'Messaggio di benvenuto da Chef Anna',
    'Un caloroso benvenuto a tutti i nuovi studenti iscritti ai nostri corsi, vi ricordo di consultare regolarmente la piattaforma per rimanere aggiornati su tutte le novità e comunicazioni importanti.',
    FALSE, 
    'ASmith',
    NULL    
);

-- Solo iscritti
INSERT INTO notifica (titolo, messaggio, solo_iscritti, username_chef, corso_id) 
VALUES (
    'Comunicazione di servizio corso Cucina Vegetariana',
    'Cari alunni, dopo gli ultimi accadimenti, sono tenuto a precisare che è severamente vietato utilizzare le foglie della pianta del corridoio interno come elemento decorativo dei piatti, soprattutto durante gli esami pratici con esterni.',
    TRUE,  
    'ASmith',
    (SELECT id_corso FROM corso WHERE nome = 'Cucina Vegetariana')
);

ON CONFLICT DO NOTHING;