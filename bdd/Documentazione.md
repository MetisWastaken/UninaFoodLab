# Dizionario delle Classi

## Utente

**Descrizione:** Registro di tutti gli utenti del sistema. Un utente può essere Chef o Studente, e sulla base di ciò ottenere differenti permessi.

| Attributo | Cardinalità | Descrizione |
|-----------|-------------|-------------|
| Username | [1] | Identificativo univoco scelto da un utente per l'accesso |
| Tipo_utente | [1] | Ruolo assegnato all'utente che può essere "Studente" o "Chef" |
| Password | [1] | Codice di accesso |
| Nome | [0,1] | Nome di battesimo dell'utente registrato |
| Cognome | [0,1] | Cognome dell'utente registrato |

## Corso

**Descrizione:** Catalogo di tutti i corsi di cucina disponibili nel database

| Attributo | Cardinalità | Descrizione |
|-----------|-------------|-------------|
| Corso_id | [1] | Identificativo numerico univoco del corso (autoincrementato) |
| Nome | [1] | Titolo descrittivo del corso |
| Categoria | [0,1] | Tipologia culinaria del corso |
| Data_in | [1] | Data inizio lezioni |
| Data_fin | [1] | Data di conclusione delle lezioni |
| Frequenza_settimanale | [0,1] | Indicazione sulla frequenza delle lezioni |
| Chef_id | [1] | Riferimento allo chef responsabile del corso |

## Pratica

**Descrizione:** Registro di tutte le sessioni pratiche associate ai corsi del sistema. Una sessione pratica è un'attività di formazione pratica legata a un corso specifico, con una data, un'aula assegnata e un numero massimo di partecipanti.

| Attributo | Cardinalità | Descrizione |
|-----------|-------------|-------------|
| Id_pratica | [1] | Identificativo univoco della sessione pratica generato automaticamente |
| Giorno_sessione | [1] | Data in cui si svolge la sessione pratica |
| Aula | [0,1] | Numero o nome dell'aula in cui si tiene la sessione pratica |
| Posti_totali | [1] | Numero massimo di posti disponibili per la sessione pratica |
| Corso_id | [1] | Identificativo univoco del corso a cui è associata la sessione pratica |

## Online

**Descrizione:** Registro di tutte le sessioni online associate ai corsi del sistema. Una sessione online è un'attività di formazione a distanza legata a un corso specifico, con una data di svolgimento e un codice meeting per l'accesso.

| Attributo | Cardinalità | Descrizione |
|-----------|-------------|-------------|
| Id_online | [1] | Identificativo univoco della sessione online generato automaticamente |
| Giorno_sessione | [1] | Data in cui si svolge la sessione online |
| Codice_meeting | [0,1] | Codice di accesso o link univoco della sessione online |
| Corso_id | [1] | Identificativo univoco del corso a cui è associata la sessione online |

## Ricetta

**Descrizione:** Registro di tutte le ricette presenti nel sistema. Una ricetta contiene informazioni generali su un piatto culinario, con nome descrittivo e dettagli sugli ingredienti e i procedimenti necessari per la preparazione.

| Attributo | Cardinalità | Descrizione |
|-----------|-------------|-------------|
| Id_ricetta | [1] | Identificativo univoco della ricetta autoincrementato |
| Nome | [1] | Nome della ricetta |
| Descrizione | [0,1] | Descrizione dettagliata della ricetta |

## Ingrediente

**Descrizione:** Elenco degli ingredienti utilizzabili nelle ricette

| Attributo | Cardinalità | Descrizione |
|-----------|-------------|-------------|
| Nome | [1] | Identificativo univoco dell'ingrediente |
| Unit_misura | [0,1] | Enumerativo per identificare il tipo di unità usata per i vari ingredienti |

---

# Dizionario delle Associazioni

| Titolo | Descrizione | Classi Associate |
|--------|-------------|------------------|
| Formato_da_online | Figlio della aggregazione con genitore "corso", presenta la possibilità di quest'ultimo di essere formato da sessioni tenute su piattaforme in remoto | Corso [0,1] - Online [1,*] |
| Formato_da_pratica | Figlio della aggregazione con genitore "corso", presenta la possibilità di quest'ultimo di essere formato da sessioni tenute nelle aule di cucina in presenza | Corso [0,1] - Pratica [1,*] |
| Iscritto_C | Rappresenta l'iscrizione di uno studente a un corso. Un corso può avere diversi iscritti, ma uno studente può iscriversi a molteplici corsi | Corso [1,*] - Utente [0,*] |
| Iscritto_P | Rappresenta l'iscrizione di uno studente a una sessione pratica. Una sessione pratica può avere diversi iscritti, mentre uno studente può iscriversi a molteplici sessioni pratiche | Pratica [0,*] - Utente [0,*] |
| Teoria | Durante le sessioni online si considera la necessità di presentare almeno una ricetta che verrà successivamente preparata durante la sessione pratica | Online [0,*] - Ricetta [0,*] |
| Pratica_Svolta | Rappresenta le ricette svolte durante una sessione pratica (e presentate precedentemente in una sessione online). Una sessione pratica è basata su una o più ricette, mentre una ricetta può essere svolta in molteplici sessioni pratiche | Pratica [1,*] - Ricetta [0,*] |
| Necessita | Ogni ricetta ha bisogno di diversi ingredienti per essere preparata | Ricetta [1,*] - Ingrediente [1,*] |

---

# Dizionario dei Vincoli

## Utente

| Nome Vincolo | Descrizione | Tipologia |
|--------------|-------------|-----------|
| password_is_strong() | Controlla che la password sia almeno di 6 caratteri | Dominio |

## Corso

| Nome Vincolo | Descrizione | Tipologia |
|--------------|-------------|-----------|
| enforce_chef_id_is_chef() | Verifica che chef_id punti a un utente di tipo 'Chef' | Interrelazionale |
| enforce_corso_data_in_is_minor_than_data_fin() | Controlla che data_in (inizio corso) sia minore o uguale di data_fin (fine corso) | Intrarelazionale |
| enforce_unique_nome_corso_attivo() | Verifica che il nome del corso non sia già in uso da un corso attivo | Unicità |
| enforce_corso_data_in_not_past() | Evita che la data inizio corso sia precedente alla data corrente | Dominio |

## Pratica

| Nome Vincolo | Descrizione | Tipologia |
|--------------|-------------|-----------|
| enforce_pratica_checks() | Verifica che corso_id punti a un corso esistente nella tabella corso | Vincolo FK |
| prevent_pratica_date_conflict() | Evita che due sessioni pratiche dello stesso corso abbiano lo stesso giorno_sessione | Unicità |
| prevent_pratica_online_date_conflict() | Evita che una sessione pratica e una sessione online dello stesso corso abbiano lo stesso giorno_sessione | Interrelazionale |
| prevent_negative_posti_totali() | Evita l'inserimento di un numero di posti_totali negativo | Dominio |
| prevent_posti_totali_with_null_aula() | Previene l'inserimento di un numero di posti_totali diverso da 0 se aula è NULL | Vincolo di NULL |
| enforce_sessione_date_within_corso() | Vieta l'inserimento di una sessione al di fuori delle date di inizio e fine corso | Interrelazionale |

## Ricetta

| Nome Vincolo | Descrizione | Tipologia |
|--------------|-------------|-----------|
| ENFORCED_ricetta_name_is_unique() | Garantisce che il nome della ricetta sia univoco nel database | Unicità |

## Ingrediente

| Nome Vincolo | Descrizione | Tipologia |
|--------------|-------------|-----------|
| check_ingrediente_nome_univoco() | Controlla che il nome dell'ingrediente sia univoco nel database | Unicità |
| check_ingrediente_nome_valido() | Assicura che il nome dell'ingrediente sia valido (contiene solo lettere, no numeri, simboli, spazi o vuoto) | Dominio |

## Online

| Nome Vincolo | Descrizione | Tipologia |
|--------------|-------------|-----------|
| enforce_online_checks() | Verifica che corso_id punti a un corso esistente nella tabella corso | Foreign Key |
| prevent_online_date_conflict() | Evita che due sessioni online dello stesso corso abbiano lo stesso giorno_sessione | Unicità |
| prevent_online_pratica_date_conflict() | Evita che una sessione online e una sessione pratica dello stesso corso abbiano lo stesso giorno_sessione | Unicità |

## Iscritto_P

| Nome Vincolo | Descrizione | Tipologia |
|--------------|-------------|-----------|
| enforce_iscrittop_checks() | Verifica che stud_id sia un utente di tipo 'Studente' e che pratica_id punti a una pratica esistente | Vincolo FK |
| enforce_max_iscritti_pratica() | Vieta l'aggiunta di un nuovo iscritto se la pratica ha raggiunto il numero massimo di partecipanti | Interrelazionale |
| enforce_student_enrolled_in_corso() | Verifica che lo studente sia iscritto al corso prima di iscriversi a una pratica di quel corso | Integrità Referenziale |
| no_enroll_finished_pratica() | Impedisce l'iscrizione a pratiche terminate (uno studente può iscriversi fino un giorno prima della fine) | Temporale |
| no_enroll_pratica_before_corso_start() | Verifica che online_id punti a una sessione online esistente e ricetta_id punti a una ricetta esistente | Vincolo FK |
| prevent_duplicate_iscrittop() | Impedisce l'iscrizione doppia di uno studente a una stessa pratica | Unicità |

## Iscritto_C

| Nome Vincolo | Descrizione | Tipologia |
|--------------|-------------|-----------|
| enforce_iscrittoc_checks() | Verifica che stud_id sia un utente di tipo 'Studente' e che corso_id punti a un corso esistente | Integrità |
| enforce_iscrittoc_unenroll_checks() | Controlla se uno studente può disiscriversi da un corso (non deve essere iscritto a pratiche non ancora terminate) | Temporale |
| enforce_enroll_corso_date_checks() | Controlla se è possibile iscriversi al corso - il bando di iscrizione apre una settimana prima dell'inizio e chiude quando il corso inizia | Temporale |
| prevent_duplicate_iscrittoc() | Impedisce l'iscrizione doppia di uno studente a uno stesso corso | Unicità |

## Necessita (Ponte)

| Nome Vincolo | Descrizione | Tipologia |
|--------------|-------------|-----------|
| enforce_necessita_checks() | Verifica che ricetta_id punti a una ricetta esistente e ingrediente_id punti a un ingrediente esistente | Vincolo FK |
| enforce_positive_quant_ing() | Controlla che la quantità dell'ingrediente sia sempre positiva | Dominio |
| enforce_non_integer_for_null_unit_misura() | Impedisce l'inserimento di valori non interi per ingredienti senza unità di misura (pezzi) | Tupla |
| prevent_duplicate_Necessita() | Controlla se una ricetta ha al suo interno già salvato un ingrediente con quel nome | Chiave |

## Pratica_Svolta (Ponte)

| Nome Vincolo | Descrizione | Tipologia |
|--------------|-------------|-----------|
| enforce_pratica_svolta_checks() | Verifica che pratica_id punti a una sessione pratica esistente e ricetta_id punti a una ricetta esistente | Vincolo FK |
| check_ricetta_if_presentata() | Vieta l'inserimento di una ricetta non svolta in una sessione online precedente alla pratica | Temporale |
| prevent_duplicate_ricetta_in_pratica() | Impedisce l'iscrizione doppia di una stessa ricetta in una pratica | Chiave |

## Teoria (Ponte)

| Nome Vincolo | Descrizione | Tipologia |
|--------------|-------------|-----------|
| enforce_teoria_checks() | Verifica che online_id punti a una sessione online esistente e ricetta_id punti a una ricetta esistente | Vincolo FK |
| prevent_duplicate_ricetta_in_online() | Impedisce l'iscrizione doppia di una stessa ricetta in una sessione online | Unicità |

---

# Dizionario delle Funzioni

| Nome Funzione | Parametri IN | Descrizione |
|---------------|--------------|-------------|
| is_corso_finished() -> BOOLEAN | corso_id INT, checkDate DATE DEFAULT CURRENT_DATE | Restituisce true se un corso è finito (data_fin passato), false altrimenti |
| is_corso_started() -> BOOLEAN | corso_id INT, checkDate DATE DEFAULT CURRENT_DATE | Restituisce true se un corso è iniziato (data_in passato), false altrimenti |
| is_pratica_finished() -> BOOLEAN | praticaId INT, checkDate DATE DEFAULT CURRENT_DATE | Restituisce true se una pratica è finita (giorno_sessione è passato), false altrimenti |

---

# Dizionario delle View

| Nome View | Descrizione | Dipendenze | Utilizzo |
|-----------|-------------|-----------|----------|
| view_studenti_iscritti | Visualizza il numero di posti totali, occupati e rimanenti per ogni sessione pratica | tabella pratica, tabella iscritto_p | Controllo posti rimanenti nelle pratiche (enforce_max_iscritti_pratica) |
| view_ingredienti_pratica | Visualizza il totale di ingredienti utilizzati per ogni pratica (somma degli ingredienti di tutte le ricette associate, moltiplicato per il numero di iscritti) | tabella pratica, tabella pratica_svolta, tabella ricetta, tabella necessita, tabella ingrediente, view_studenti_iscritti | Calcolo quantità ingredienti necessari per le sessioni pratiche |
