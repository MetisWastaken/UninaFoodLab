## Work in Progress

### Modifiche necessarie:
1. ~~Necessita -> unità di misuro==NULL, quant. ing. non deve avere virgola~~ ___(non implementabile in DB)___(Credo sia possibile,ma non può solo cambiare il tipo, cerco di farla)
2. ~~Ingredienti in pratica ha bisogno di un view calcolabile in cui storare il cibo necessario per ogni sessione~~
3. ~~Controllo inizio e fine (date) non siano uno minore dell'altro~~
4. ~~I nomi dei corsi possono essere riutilizzati solo quando il corso avente già il nome X in questione è stato completato già~~
5. ~~se uno studente si vuole disiscrivere dal corso deve o finire le sessioni pratiche o prima uscire da queste ultime~~
6. ~~Se studente non è iscritto al corso iscritto_p bloccherà l'inserimento X~~
7. Controllo necessario tra data iscrizione utente e data fine corso e pratica X
8. ~~View necessaria per controllo posti rimanenti (in pratica)~~
9. Data inizio corso non può essere precedente a cuurent time
10. La pratica e online deve avere inserita all'interno di inizio e fine del corso in questione (due funzioni)
11. In sessione online va presentata la ricetta prima di essere svolta in pratica, con annesso check della data di presentazione come minore di quella di sessione pratica
12. funzione che impediscono a uno studente di iscriversi a sessioni precedenti alla sua registrazione.
13. funzione che impedisce a uno studente di iscriversi a sessioni pratiche precedenti alla data corrente.
14. Uno stundente non può iscriversi ad un corso dopo la sua fine
15. 




