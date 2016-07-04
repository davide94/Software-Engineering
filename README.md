# README #


Per far partire il gioco lato Server bisogna runnare la classe "Server" all'interno del package "it.polimi.ingsw.cg26.server".


Lato giocatore runnare la classe "Client" all'interno del package "it.polimi.ingsw.cg26.client", si aprirà quindi un dialog panel dove bisogna scegliere il tipo di connessione, inserire un nome e decidere se giocare tramite GUI oppure CLI.


L'interfaccia grafica presenta un pannello in alto a destra che contiene le azioni del gioco (si apre passandoci sopra con il cursore, quando è il turno del giocatore). In basso a destra è presente il simbolo della chat che è la funzionalità avanzata scelta. Sulla sinistra, il simbolo nero che si apre passandoci sopra con il cursore mostra un pannello in cui è contenuto la stato dei giocatori. Infine in basso a sinistra è contenuto il pannello dove si svolgerà la fase di market.

In base al numero di giocatore verrà creata una mappa con una certa situazione di gioco. Il primo Client della partità è il giocatore che muove per primo e può scegliere in alto a destra quale azione veloce e principale compiere, visualizzando anche il countdown relativo al suo turno. 
Finito il tempo oppure una volta compiute le azioni il gioco è in mano al secondo giocatore e così via fino all'ultimo. 
Finito il turno inizia la fase di Market in cui , sempre nello stesso ordine,  i giocatori decidono quali oggetti vendere ed ogni giocatore, una volta conclusa tale operazione di vendita dovrà premere il tast "fold sell" per passare il turno.
Finita tale fase inizia quella di acquisto dove seguendo un giro random, i giocatori decidono a turno cosa comprare e quando ognuno ha concluso tale operazione dovrà premere il tasto "fold buy".
Ricomincia così il turno classico e tale procedura si ripete fino alla fine del gioco.

L'applicazione è stata deployata su una macchina virtuale hostata da Microsoft Azure, all'indirizzo 13.88.20.177.
Per problemi di rete (a noi non noti) la comunicazione via RMI con il server risulta problematica, nonostante funzioni regolarmente in locale.
Un ulteriore problema di connessione sorge qualora vi connettiate dalla rete "polimi-protected" del Politecnico che non permette di connettersi, su tutte le porte, al di fuori di questa rete non abbiamo riscontrato problemi dovuti alla rete con la connessione via socket.