package it.polimi.ingsw.cg26.model.player;

import it.polimi.ingsw.cg26.exceptions.*;
import it.polimi.ingsw.cg26.model.GameLogic;
import it.polimi.ingsw.cg26.model.board.NobilityCell;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * The Player class models a player
 */
public class Player {

    /**
     * Reference to the game logic class
     */
    private GameLogic gameLogic;

    /**
     * Reference to the victory points manager
     */
    private VictoryPoints victoryPoints = new VictoryPoints();

    /**
     * Reference to the coins manager
     */
    private Coins coins = new Coins();

    /**
     * Reference to the main actions manager
     */
    private Actions mainActions = new MainActions();

    /**
     * Reference to the quick actions manager
     */
    private Actions quickActions = new QuickActions();

    /**
     * Reference to the current cell in the nobility track
     */
    private NobilityCell currentNobilityCell;

    /**
     * The collection of assistants owned by the player
     */
    private LinkedList<Assistant> assistants = new LinkedList<>();

    /**
     * The collection of politic cards owned by the player
     */
    private LinkedList<PoliticCard> cards = new LinkedList<>();

    /**
     * The collection of business permission tiles owned by the player, already used or not
     */
    private LinkedList<BusinessPermissionTile> tiles = new LinkedList<>();

    /**
     * Constructs a Player
     * @param gameLogic reference to the game logic class
     * @param nobilityCell reference to the cell in the nobility track that the player has to start from
     * @param coins number of coins owned by the player
     * @param assistants collection of assistants owned by the player
     * @throws NullPointerException if any parameter is null
     * @throws IllegalArgumentException if coins is negative
     */
    public Player(GameLogic gameLogic, NobilityCell nobilityCell, int coins, Collection<Assistant> assistants) {
        if (gameLogic == null || nobilityCell == null || assistants == null)
            throw new NullPointerException();
        if (coins < 0)
            throw new IllegalArgumentException();

        this.gameLogic = gameLogic;
        this.currentNobilityCell = nobilityCell;
        this.coins.addCoins(coins);
        this.assistants.addAll(assistants);
    }

    /**
     * Performs an action: the number of remaining main actions is decremented by one
     * @throws NoRemainingActionsException if the player do not have any remaining main actions
     */
    public void performMainAction() {
        this.mainActions.perform();
    }

    /**
     * Performs an action: the number of remaining quick actions is decremented by one
     * @throws NoRemainingActionsException if the player do not have any remaining quick actions
     */
    public void performQuickAction() {
        this.quickActions.perform();
    }

    /**
     * Sets the number of remaining main actions in this turn
     * @param remainingActions is the number to be set as number of remaining main actions
     * @throws IllegalArgumentException if the parameter is negative
     */
    public void setRemainingMainActions(int remainingActions) {
        this.mainActions.setRemaining(remainingActions);
    }

    /**
     * Sets the number of remaining quick actions in this turn
     * @param remainingActions is the number to be set as number of remaining quick actions
     * @throws IllegalArgumentException if the parameter is negative
     */
    public void setRemainingQuickActions(int remainingActions) {
        this.quickActions.setRemaining(remainingActions);
    }

    /**
     * Increments the number of main actions in this turn
     * @param increment is the number of main actions to be added for this turn
     * @throws IllegalArgumentException if the increment is negative
     */
    public void addRemainingMainActions(int increment) {
        this.mainActions.addActions(increment);
    }

    /**
     * Increments the number of quick actions in this turn
     * @param increment is the number of quick actions to be added for this turn
     * @throws IllegalArgumentException if the increment is negative
     */
    public void addRemainingQuickActions(int increment) {
        this.quickActions.addActions(increment);
    }

    /**
     * Gains a position in the nobility track
     */
    public void incrementNobility() {
        if (this.currentNobilityCell.hasNext()) {
            this.currentNobilityCell = this.currentNobilityCell.next();
            // TODO prendere i bonus nella nuova cella, che per ora non posso fare perché NobilityCell non è ancora implementato
        }
        // TODO if not next?
    }

    /**
     * Returns the nobility track's cell where the player currently is
     * @return the nobility track's cell where the player currently is
     */
    public NobilityCell getNobilityCell() {
        return this.currentNobilityCell;
    }

    /**
     * Returns the number of assistants owned by the player
     * @return the number of assistants owned by the player
     */
    public int numberOfAssistants(){
    	return this.assistants.size();
    }

    /**
     * Takes and removes an assistant from the assistants owned by the player
     * @return an assistant removed from the assistants owned by the player
     * @throws NoRemainingAssistantsException if there are no assistants remaining
     */
    public Assistant takeAssistant() {
        if (this.assistants.size() == 0)
            throw new NoRemainingAssistantsException();
        return this.assistants.poll();
    }

    /**
     * Adds an assistant to the assistants owned by the player
     * @param assistant is the assistant to be added to the assistants owned by the player
     * @throws NullPointerException if the parameter is null
     */
    public void addAssistant(Assistant assistant) {
        if (assistant == null)
            throw new NullPointerException();
        this.assistants.add(assistant);
    }

    /**
     * Increments the number of coins owned by the player
     * @param increment is the number of coins to be added
     * @throws IllegalArgumentException if the parameter is negative
     */
    public void addCoins(int increment) {
        this.coins.addCoins(increment);
    }

    /**
     * Decrements the number of coins owned by the player
     * @param decrement is the number of coins to be removed
     * @throws IllegalArgumentException if the parameter is negative
     * @throws NotEnoughMoneyException if the decrement is greater than the number of coins owned by the player
     */
    public void removeCoins(int decrement) {
        this.coins.removeCoins(decrement);
    }

    /**
     * Increments the number of victory points
     * @param increment is the number of victory points to be added
     * @throws IllegalArgumentException if the parameter is negative
     */
    public void addVictoryPoints(int increment) {
        this.victoryPoints.addPoints(increment);
    }

    /**
     * Adds a politic card to the cards owned by the player
     * @param card is the politic card to be added to the cards owned by the player
     * @throws NullPointerException if the parameter is null
     */
    public void addPoliticCard(PoliticCard card) {
        if (card == null)
            throw new NullPointerException();
        this.cards.add(card);
    }

    /**
     * Adds a business permission tile to the tiles owned by the player
     * @param tile is the business permission tile to be added to the tiles owned by the player
     * @throws NullPointerException if the parameter is null
     */
    public void addPermissionTile(BusinessPermissionTile tile) {
        if (tile == null)
            throw new NullPointerException();
        this.tiles.add(tile);
    }
    
    /**
     * Returns a collection of politic cards that match with the required
     * @return a collection of politic cards that match with the required
     * @throws InvalidCardsException if the player does not owns all the cards required
     */
    public LinkedList<PoliticCard> getCards(Collection<String> requiredCards) {
        LinkedList<PoliticCard> cards = new LinkedList<>();
        for (String requiredCard: requiredCards) {
            for (PoliticCard card: this.cards) {
                if (card.getColor().colorString().equalsIgnoreCase(requiredCard)) {
                    cards.add(card);
                    requiredCards.remove(requiredCard);
                    break;
                }
            }
        }
        if (!requiredCards.isEmpty()) {
            throw new InvalidCardsException();
        }
        return cards;
    }

    /**
     * Removes a collection of politic cards from the cards owned by the player
     * @throws InvalidCardsException if the player does not have any of the required cards
     * @throws NullPointerException if the parameter is null or if contains one or more null elements
     */
    public synchronized void useCards(Collection<PoliticCard> cards) {
        if (!this.cards.containsAll(cards))
            throw new InvalidCardsException();
        this.cards.removeAll(cards);
    }


}