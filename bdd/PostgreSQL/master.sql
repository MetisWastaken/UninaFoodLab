-- CREAZIONE TABELLE

\i 'CreaTabellaUtente.sql'
\i 'CreaTabellaCorso.sql'
\i 'CreaTabellaIngrediente.sql'
\i 'CreaTabellaRicetta.sql'
\i 'CreaTabellaPratica.sql'
\i 'CreaTabellaOnline.sql'
\i 'CreaTabellaNotifica.sql'

-- CREAZIONE PONTI (Relazioni)

\i 'CreaPonteIscritto_C.sql'
\i 'CreaPonteIscritto_P.sql'
\i 'CreaPonteTeoria.sql'
\i 'CreaPontePratica_Svolta.sql'
\i 'CreaPonteNecessita.sql'

-- CREAZIONE FUNZIONI

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
\i 'funzioni/FunTabellaNotifica.sql'
\i 'funzioni/View.sql'

-- CREAZIONE PROCEDURE
\i 'funzioni/ProcedureCRUD/CRUDTabellaUtente.sql'
\i 'funzioni/ProcedureCRUD/CRUDTabellaRicetta.sql'

-- INSERIMENTI DI ESEMPIO

\i 'insert/1InserisciUtente.sql'
\i 'insert/2InserisciCorso.sql'
\i 'insert/3InserisciRicetta.sql'
\i 'insert/4InserisciOnline.sql'
\i 'insert/5InserisciPratica.sql'

