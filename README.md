# README #


Per far partire il gioco lato Server bisogna runnare la classe "Server" all'interno del package "it.polimi.ingsw.cg26.server".

Lato giocatore runnare la classe "Client" all'interno del package "it.polimi.ingsw.cg26.client", si aprirà quindi un dialog panel dove bisogna scegliere il tipo di connessione, inserire un nome e decidere se giocare tramite GUI oppure CLI.

L'interfaccia grafica presenta un pannello in alto a destra che contiene le azioni del gioco (si apre passandoci sopra con il cursore, quando è il turno del giocatore).
In basso a destra è presente il simbolo della chat che è la funzionalità avanzata scelta.
Sulla sinistra, il simbolo nero che si apre passandoci sopra con il cursore mostra un pannello in cui è contenuto la stato dei giocatori.
Infine in basso a sinistra è contenuto il pannello dove si svolgerà la fase di market.
L'interfaccia riproduce anche una colonna sonora che si può silenziare con il pulsante posto sotto il balcone più a destra.

L'inizio del gioco rispetta le regole complete, avviando la partita 20 secondi dopo la connessione del secondo giocatore e può ospitare tutti i giocatori che si connettono in questo lasso di tempo.
Per ovvi motivi se ci sono più di 4 giocatori l'interfaccia grafica potrebbe risultare non ottimizzata, nonostante il lato server sia in grado di gestire la partita con un numero arbitrario di giocatori senza risentirne.
In base al numero di giocatore verrà creata una mappa con una certa situazione di gioco. Il primo Client della partità è il giocatore che muove per primo e può scegliere in alto a destra quale azione veloce e principale compiere, visualizzando anche il countdown relativo al suo turno. 
Finito il tempo oppure una volta compiute le azioni il gioco è in mano al secondo giocatore e così via fino all'ultimo. 
Finito il turno inizia la fase di Market in cui , sempre nello stesso ordine,  i giocatori decidono quali oggetti vendere ed ogni giocatore, una volta conclusa tale operazione di vendita dovrà premere il tast "fold sell" per passare il turno.
Finita tale fase inizia quella di acquisto dove seguendo un giro random, i giocatori decidono a turno cosa comprare e quando ognuno ha concluso tale operazione dovrà premere il tasto "fold buy".
Ricomincia così il turno classico e tale procedura si ripete fino alla fine del gioco.

L'applicazione è stata deployata su una macchina virtuale hostata da Microsoft Azure, all'indirizzo 13.88.20.177.
Per problemi di rete (a noi non noti) la comunicazione via RMI con il server risulta problematica, nonostante funzioni regolarmente in locale.
Un ulteriore problema di connessione sorge qualora vi connettiate dalla rete "polimi-protected" del Politecnico che non permette di connettersi, su tutte le porte, al di fuori di questa rete non abbiamo riscontrato problemi dovuti alla rete con la connessione via socket.