package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class MatchEnded extends State {

	
	/**
	 * The list of players
	 */
    private List<Player> players;
    
    /**
     * Default constructor
     * @param players is the list of players
     * @param gameBoard is the game board
     */
    public MatchEnded(List<Player> players, GameBoard gameBoard) {
        super(gameBoard);
        this.players = players;
        calculateWinner();
        LinkedList<PlayerDTO> playersStetes = new LinkedList<>();
        players.forEach(p -> playersStetes.add(p.getState()));
        gameBoard.notifyObservers(new it.polimi.ingsw.cg26.common.update.event.MatchEnded(playersStetes));
    }

    @Override
    public Player getCurrentPlayer() {
        return null;
    }

    public List<Player> getPlayers() {
        return players;
    }

    private Comparator<Player> nobilityComparator = (x, y) ->
            x.getNobilityCell().getIndex() > y.getNobilityCell().getIndex() ? -1 :
                    (x.getNobilityCell().getIndex() == y.getNobilityCell().getIndex() ? 0 : 1);

    private Comparator<Player> bptComparator = (x, y) ->
            x.getBPTNumber() > y.getBPTNumber() ? -1 :
                    (x.getBPTNumber() == y.getBPTNumber() ? 0 : 1);

    private Comparator<Player> victoryComparator = (x, y) ->
            x.getVictoryPoints() > y.getVictoryPoints() ? -1 :
                    (x.getVictoryPoints() == y.getVictoryPoints() ? 0 : 1);

    private Comparator<Player> assistantsPlusCardsComparator = (x, y) ->
            (x.getAssistantsNumber() + x.getPoliticCardsNumber()) > (y.getAssistantsNumber() + y.getPoliticCardsNumber()) ? -1 :
                    ((x.getAssistantsNumber() + x.getPoliticCardsNumber()) == (y.getAssistantsNumber() + y.getPoliticCardsNumber()) ? 0 : 1);

    private void calculateWinner() {

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
        if (players.get(0).getBPTNumber() > players.get(1).getBPTNumber())
            players.get(0).addVictoryPoints(3);

        Collections.sort(players, victoryComparator);
        int a = (int)players.stream().filter(p -> p.getVictoryPoints() == players.get(0).getVictoryPoints()).count();
        if (a > 1)
            Collections.sort(players.subList(0, a), assistantsPlusCardsComparator);

    }
}
