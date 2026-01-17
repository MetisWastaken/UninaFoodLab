-- Usa questo comando da shell per creare l'intero database UninaFoodLab:
-- & "C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres -d uninafoodlab -f "(posizione_del_file)\master.sql"

-- ============================================
-- MASTER SCRIPT - UninaFoodLab Database
-- ============================================
-- Ordine di esecuzione:
-- 1. Tabelle
-- 2. Ponti (relazioni)
-- 3. Funzioni
-- 4. Inserimenti di esempio

-- ============================================
-- 1. CREAZIONE TABELLE
-- ============================================
\i 'CreaTabellaUtente.sql'
\i 'CreaTabellaCorso.sql'
\i 'CreaTabellaIngrediente.sql'
\i 'CreaTabellaRicetta.sql'
\i 'CreaTabellaPratica.sql'
\i 'CreaTabellaOnline.sql'

-- ============================================
-- 2. CREAZIONE PONTI (Relazioni)
-- ============================================
\i 'CreaPonteIscritto_C.sql'
\i 'CreaPonteIscritto_P.sql'
\i 'CreaPonteTeoria.sql'
\i 'CreaPontePratica_Svolta.sql'
\i 'CreaPonteNecessita.sql'

-- ============================================
-- 3. CREAZIONE FUNZIONI
-- ============================================
\i 'funzioni/FunTabellaUtente.sql'
\i 'funzioni/FunTabellaCorso.sql'
\i 'funzioni/FunTabellaIngrediente.sql'
\i 'funzioni/FunTabellaRicetta.sql'
\i 'funzioni/FunTabellaPratica.sql'
\i 'funzioni/FunTabellaOnline.sql'
\i 'funzioni/FunPonteIscritto_C.sql'
\i 'funzioni/FunPonteIscritto_P.sql'
\i 'funzioni/FunPonteTeoria.sql'
\i 'funzioni/FunPontePratica_Svolta.sql'
\i 'funzioni/FunPonteNecessita.sql'
\i 'funzioni/View.sql'

-- ============================================
-- 3. INSERIMENTI DI ESEMPIO
-- ============================================
\i 'insert/1InserisciUtente.sql'
\i 'insert/2InserisciCorso.sql'
\i 'insert/3InserisciRicetta.sql'
\i 'insert/4InserisciOnline.sql'
\i 'insert/5InserisciPratica.sql'
-- ============================================
-- Setup completato!
-- ============================================
