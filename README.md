# README

## Gruppo
Riccardo Ruspoli - 865874

### Descrizione progetto
Il progetto è stato realizzato con Java 11, con l'ausilio del framework Spring Boot + Spring MVC e JPA per l'accesso al DB.

Il progetto si compone di due entità, Account e Transaction, che sono salvate in modo persistente sul DB PostgreSQL tramite JPA.
Due JPA repository, AccountRepository e TransactionRepository, si occupano della comunicazione tra il DB e l'applicazione e la gestione del modello dei dati tramite le entità.

Tutta la logica di business è contenuta all'interno di due servizi, AccountService e TransactionService, che si occupano della comunicazione tra i controller e il DB, applicando tutta la logica richiesta sia per recuperare i dati, sia per salvarli nel DB.

Tre controller si occupano della comunicazione tra in frontend ed il backend: AccountController e TransactionController gestiscono gli endpoint per operazioni legate alle rispettive entità, chiamando il service opportuno, mentre NavigationController permette la navigazione tramite le due pagine HTML del progetto.
Gli errori vengono gestiti nei servizi tramite il throw di Exception standard o custom, e nei controller viene fatto il catch e restituito il codice di errore ed il messaggio corretto al chiamante.

Il frontend dell'applicazione è costituito da HTML + CSS con l'ausilio di Bootstrap, mentre la validazione e la comunicazione con il backend è realizzato con JavaScript e jQuery.