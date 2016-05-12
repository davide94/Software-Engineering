package it.polimi.ingsw.cg26.model.player;

import it.polimi.ingsw.cg26.exceptions.*;
import it.polimi.ingsw.cg26.model.GameLogic;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.board.NobilityCell;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;

import java.util.Collection;
import java.util.LinkedList;

/**
 * The Player class models a player
 */
public class Player {

    private static final int INITIAL_CARDS_NUMBER = 6;

    /**
     * Reference to the game logic class
     */
    //private final GameBoard gameBoard;

    /**
     * Reference to the victory points manager
     */
    private final VictoryPoints victoryPoints = new VictoryPoints();

    /**
     * Reference to the coins manager
     */
    private final Coins coins = new Coins();

    /**
     * Reference to the main actions manager
     */
    private final RemainingActions remainingMainActions;

    /**
     * Reference to the quick actions manager
     */
    private final RemainingActions remainingQuickActions;

    /**
     * Reference to the current cell in the nobility track
     */
    private NobilityCell currentNobilityCell;

    /**
     * The collection of assistants owned by the player
     */
    private final LinkedList<Assistant> assistants = new LinkedList<>();

    /**
     * The collection of politic cards owned by the player
     */
    private final LinkedList<PoliticCard> cards = new LinkedList<>();

    /**
     * The collection of business permission tiles owned by the player, already used or not
     */
    private final LinkedList<BusinessPermissionTile> tiles = new LinkedList<>();

    /**
     * Constructs a Player
     * @param nobilityCell reference to the cell in the nobility track that the player has to start from
     * @param coins number of coins owned by the player
     * @param assistants collection of assistants owned by the player
     * @throws NullPointerException if any parameter is null
     * @throws IllegalArgumentException if coins is negative
     */
    public Player(NobilityCell nobilityCell, int coins, LinkedList<PoliticCard> cards, Collection<Assistant> assistants) {
        if (nobilityCell == null || cards == null || assistants == null)
            throw new NullPointerException();
        if (coins < 0)
            throw new IllegalArgumentException();

        this.currentNobilityCell = nobilityCell;
        this.coins.addCoins(coins);
        this.cards.addAll(cards);
        this.assistants.addAll(assistants);
        this.remainingMainActions = new RemainingMainActions();
        this.remainingQuickActions = new RemainingQuickActions();

        System.out.print("Player:  ");
        for (PoliticCard card: this.cards)
            System.out.print(card.getColor().colorString() + "  ");
        System.out.println("\n--------");
    }

    public boolean canPerformMainAction() {
        return this.remainingMainActions.canPerform();
    }

    public boolean canPerformQuickAction() {
        return this.remainingQuickActions.canPerform();
    }

    /**
     * Performs an action: the number of remaining main actions is decremented by one
     * @throws NoRemainingActionsException if the player do not have any remaining main actions
     */
    public void performMainAction() {
        this.remainingMainActions.perform();
    }

    /**
     * Performs an action: the number of remaining quick actions is decremented by one
     * @throws NoRemainingActionsException if the player do not have any remaining quick actions
     */
    public void performQuickAction() {
        this.remainingQuickActions.perform();
    }

    /**
     * Sets the number of remaining main actions in this turn
     * @param remainingActions is the number to be set as number of remaining main actions
     * @throws IllegalArgumentException if the parameter is negative
     */
    public void setRemainingMainActions(int remainingActions) {
        this.remainingMainActions.setRemaining(remainingActions);
    }

    /**
     * Sets the number of remaining quick actions in this turn
     * @param remainingActions is the number to be set as number of remaining quick actions
     * @throws IllegalArgumentException if the parameter is negative
     */
    public void setRemainingQuickActions(int remainingActions) {
        this.remainingQuickActions.setRemaining(remainingActions);
    }

    /**
     * Increments the number of main actions in this turn
     * @param increment is the number of main actions to be added for this turn
     * @throws IllegalArgumentException if the increment is negative
     */
    public void addRemainingMainActions(int increment) {
        this.remainingMainActions.addActions(increment);
    }

    /**
     * Increments the number of quick actions in this turn
     * @param increment is the number of quick actions to be added for this turn
     * @throws IllegalArgumentException if the increment is negative
     */
    public void addRemainingQuickActions(int increment) {
        this.remainingQuickActions.addActions(increment);
    }

    /**
     * Gains a position in the nobility track
     */
    public void incrementNobility() {
        if (this.currentNobilityCell.hasNext()) {
            this.currentNobilityCell = this.currentNobilityCell.next();
            this.currentNobilityCell.apply(this);
        }
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
     *
     */
    public boolean hasCards(Collection<PoliticColor> cardsColors) {
        LinkedList<PoliticCard> cards = new LinkedList<>(this.cards);
        for (PoliticColor color: cardsColors) {
            PoliticCard c = null;
            for (PoliticCard card: cards) {
                if (card.getColor().equals(color)) {
                    c = card;
                    break;
                }
            }
            if (c == null)
                return false;
            cards.remove(c);
        }
        return true;
    }


    /**
     * Returns a collection of politic cards that match with the required
     * @param cardsColors is a collection of PoliticColor that represents the required cards
     * @return a collection of politic cards that match with the required
     * @throws InvalidCardsException if the player does not owns all the cards required
     */
    public LinkedList<PoliticCard> useCards(Collection<PoliticColor> cardsColors) {
        LinkedList<PoliticCard> cards = new LinkedList<>(this.cards);
        LinkedList<PoliticCard> ret = new LinkedList<>();
        for (PoliticColor color: cardsColors) {
            PoliticCard c = null;
            for (PoliticCard card: cards) {
                if (card.getColor().equals(color)) {
                    c = card;
                    break;
                }
            }
            if (c == null)
                throw new InvalidCardsException();
            cards.remove(c);
            ret.add(c);
        }
        return ret;
    }

    /**
     * Removes a collection of politic cards from the cards owned by the player
     * @throws InvalidCardsException if the player does not have any of the required cards
     * @throws NullPointerException if the parameter is null or if contains one or more null elements
     */
    /*public void useCards(Collection<PoliticColor> cardsColors) {
        LinkedList<PoliticCard> cards = new LinkedList<>(this.cards);
        for (PoliticColor color: cardsColors) {
            for (PoliticCard card: cards) {
                if (card.getColor().colorString().equalsIgnoreCase(color.colorString()))
                    this.cards.remove(card);
            }
        }
    }*/

    /**
     * Returns the number of coins owned by the player
     * @return the number of coins owned by the player
     */
    public int getCoinsNumber() {
        return this.coins.getValue();
    }


}