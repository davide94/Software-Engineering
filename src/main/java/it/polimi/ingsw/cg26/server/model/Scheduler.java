package it.polimi.ingsw.cg26.server.model;

import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Coins;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Predicate;

/**
 *
 */
public class Scheduler {

    private static final int INITIAL_CARDS_NUMBER = 6;

    /**
     * List of players
     */
    private final List<Player> players;

    /**
     * The position of the current player in the players list
     */
    private int current;

    private boolean market;

    private boolean lastTurn;

    private int winner;

    /**
     * The Game Board
     */
    private final GameBoard gameBoard;

    /**
     * Constructs a Scheduler
     * @param gameBoard is the gameBoard
     * @throws NullPointerException if gameBoard is null
     */
    public Scheduler(GameBoard gameBoard) {
        if (gameBoard == null)
            throw new NullPointerException();
        this.players = new LinkedList<>();
        this.current = 0;
        this.market = false;
        this.lastTurn = false;
        this.winner = 0;
        this.gameBoard = gameBoard;
    }

    /**
     * Returns a collection that represents the dto of all the players
     * @return the dto of all the players
     */
    public List<PlayerDTO> getPlayersState(){
        List<PlayerDTO> playersState = new ArrayList<>();
        playersState.add(getCurrentPlayer().getState());
        for(Player player : this.players){
            playersState.add(player.getState());
        }
        return playersState;
    }

    /**
     * Returns a collection that represents the dto of all the players
     * @return the dto of all the players
     */
    public List<PlayerDTO> getPlayersFullState(){
        List<PlayerDTO> playersState = new ArrayList<>();
        playersState.add(getCurrentPlayer().getFullState());
        for(Player player : this.players){
            playersState.add(player.getFullState());
        }
        return playersState;
    }

    /**
     * Returns the current Player
     * @return the current Player
     */
    public Player getCurrentPlayer() {
        return players.get(current);
    }

    /**
     * Adds a Player to the list of players
     *
     *
     */
    public long registerPlayer(String name) {
        Player player = newPlayer(name);
        if (players.isEmpty()) {
            player.setRemainingMainActions(1);
            player.setRemainingQuickActions(1);
            player.addPoliticCard(gameBoard.getPoliticDeck().draw());
        }
        players.add(player);
        return player.getToken();
    }

    private Player newPlayer(String name) {
        if (name.equals(""))
            name = "Player_" + players.size();
        LinkedList<Assistant> assistants = new LinkedList<>();
        for (int i = 0; i <= players.size(); i++)
            assistants.add(new Assistant());
        LinkedList<PoliticCard> cards = new LinkedList<>();
        for (int i = 0; i < INITIAL_CARDS_NUMBER; i++)
            cards.add(gameBoard.getPoliticDeck().draw());
        long token = new BigInteger(64, new SecureRandom()).longValue();
        return new Player(token, name, gameBoard.getNobilityTrack().getFirstCell(), players.size() + 10, cards, assistants);
    }

    /**
     * Checks if the player can perform more actions and if not, the turn passes tu the next player.
     * Also checks if someone won.
     */
    public void actionPerformed() {
        if (getCurrentPlayer().canPerformMainAction() || getCurrentPlayer().canPerformQuickAction())
            return;

        if (!lastTurn) {
            int count = gameBoard.getRegions().stream().mapToInt(r -> (int)r.getCities().stream().filter(
                    c -> (c.hasEmporium(getCurrentPlayer()))).count()).reduce(0, (a, b) -> a + b);
            if (count >= 10) {
                getCurrentPlayer().addVictoryPoints(3);
                lastTurn = true;
                winner = current;
            }
        }

        current = current++ % players.size();

        if (!market && current == 0)
            market = true;

        if (lastTurn && current == winner) {
            endMatch();
            return;
        }

        if (!market) {
            getCurrentPlayer().setRemainingMainActions(1);
            getCurrentPlayer().setRemainingQuickActions(1);
            getCurrentPlayer().addPoliticCard(gameBoard.getPoliticDeck().draw());
        }
    }
    
    public void foldSell(){
    	
    }
    
    public void foldBuy(){
    	
    }

    private Comparator<Player> nobilityComparator = (x, y) ->
            x.getNobilityCell().getIndex() < y.getNobilityCell().getIndex() ? -1 :
                    x.getNobilityCell().getIndex() == y.getNobilityCell().getIndex() ? 0 : 1;

    private Comparator<Player> bptComparator = (x, y) ->
            x.getBPTNumber() < y.getBPTNumber() ? -1 :
                    x.getBPTNumber() == y.getBPTNumber() ? 0 : 1;

    private Comparator<Player> victoryComparator = (x, y) ->
            x.getBPTNumber() < y.getBPTNumber() ? -1 :
                    x.getBPTNumber() == y.getBPTNumber() ? 0 : 1;

    private Comparator<Player> assistantsPlusCardsComparator = (x, y) ->
            x.getAssistantsNumber() + x.getPoliticCardsNumber() < y.getAssistantsNumber() + y.getPoliticCardsNumber() ? -1 :
                    x.getAssistantsNumber() + x.getPoliticCardsNumber() == y.getAssistantsNumber() + y.getPoliticCardsNumber() ? 0 : 1;

    private void endMatch() {

        // first nobility -> 5 victory
        // second nobility -> 2 victory (only if the first is unique)
        // first bpt -> 3 (only if unique)

        Collections.sort(players, nobilityComparator);
        if (players.get(0).getNobilityCell().getIndex() != players.get(1).getNobilityCell().getIndex()) {
            players.get(0).addVictoryPoints(5);
            players.stream().skip(1)
                    .filter(p -> p.getNobilityCell().getIndex() == players.get(1).getNobilityCell().getIndex())
                    .forEach(p -> p.addVictoryPoints(2));
        } else {
            players.stream()
                    .filter(p -> p.getNobilityCell().getIndex() == players.get(0).getNobilityCell().getIndex())
                    .forEach(p -> p.addVictoryPoints(5));
        }

        Collections.sort(players, bptComparator);
        if (players.get(0).getBPTNumber() > players.get(2).getBPTNumber())
            players.get(0).addVictoryPoints(3);

        Collections.sort(players, victoryComparator);
        int a = (int)players.stream().filter(p -> p.getVictoryPoints() == players.get(0).getVictoryPoints()).count();
        if (a != 0)
            Collections.sort(players.subList(0, a), assistantsPlusCardsComparator);

        //if (assistantsPlusCardsComparator.compare(players.get(0), players.get(1)) == 0)
            // no one wins :(
        // players.gat(0) is the winner
    }
}
