# README #


Per far partire il gioco lato Server bisogna runnare la classe "Server" all'interno del package "it.polimi.ingsw.cg26.server".


Lato giocatore runnare la classe "Client" all'interno del package "it.polimi.ingsw.cg26.client", si aprirà quindi un dialog panel dove bisogna scegliere il tipo di connessione, inserire un nome e decidere se giocare tramite GUI oppure CLI.


In base al numero di giocatore verrà creata una mappa con una certa situazione di gioco. Il primo Client della partità è il giocatore che muove per primo e può scegliere in alto a destra quale azione veloce e principale compiere, visualizzando anche il countdown relativo al suo turno. 
Finito il tempo oppure una volta compiute le azioni il gioco è in mano al secondo giocatore e così via fino all'ultimo. 
Finito il turno inizia la fase di Market in cui , sempre nello stesso ordine,  i giocatori decidono quali oggetti vendere ed ogni giocatore, una volta conclusa tale operazione di vendita dovrà premere il tast "fold sell" per passare il turno.
Finita tale fase inizia quella di acquisto dove seguendo un giro random, i giocatori decidono a turno cosa comprare e quando ognuno ha concluso tale operazione dovrà premere il tasto "fold buy".
Ricomincia così il turno classico e tale procedura si ripete fino alla fine del gioco. 