package it.polimi.ingsw.cg26.server.model.player;

import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * The Player class models a player
 */
public class Player {

    private final String name;

    private final String token;

    /**
     * Reference to the victory points manager
     */
    private final VictoryPoints victoryPoints;

    /**
     * Reference to the coins manager
     */
    private final Coins coins;

    /**
     * Reference to the main commands manager
     */
    private final RemainingActions remainingMainActions;

    /**
     * Reference to the quick commands manager
     */
    private final RemainingActions remainingQuickActions;

    /**
     * Reference to the current cell in the nobility track
     */
    private NobilityCell currentNobilityCell;

    /**
     * The collection of assistants owned by the player
     */
    private final LinkedList<Assistant> assistants;

    /**
     * The collection of politic cards owned by the player
     */
    private final List<PoliticCard> cards;

    /**
     * The collection of business permission tiles owned by the player, already used or not
     */
    private final List<BusinessPermissionTile> tiles;

    private final LinkedList<BusinessPermissionTile> discardedTiles;

    /**
     * Constructs a Player
     * @param nobilityCell reference to the cell in the nobility track that the player has to start from
     * @param coins number of coins owned by the player
     * @param assistants collection of assistants owned by the player
     * @throws NullPointerException if any parameter is null
     * @throws IllegalArgumentException if coins is negative
     */
    public Player(String token, String name, NobilityCell nobilityCell, int coins, Collection<PoliticCard> cards, Collection<Assistant> assistants) {
        if (token == null || name == null ||nobilityCell == null || cards == null || assistants == null)
            throw new NullPointerException();
        if (coins < 0)
            throw new IllegalArgumentException();

        this.token = token;
        this.name = name;
        this.victoryPoints = new VictoryPoints();
        this.coins = new Coins();
        this.currentNobilityCell = nobilityCell;
        this.coins.addCoins(coins);
        this.cards = new LinkedList<>();
        for(PoliticCard card : cards){
    		this.addPoliticCard(card);
    	}
        this.assistants = new LinkedList<>();
        for(Assistant assistant : assistants){
        	this.addAssistant(assistant);
        }
        this.remainingMainActions = new RemainingMainActions();
        this.remainingQuickActions = new RemainingQuickActions();
        this.tiles = new LinkedList<>();
        this.discardedTiles = new LinkedList<>();
    }

    public String getName() {
        return this.name;
    }

    public String getToken() {
        return this.token;
    }

    public boolean canPerformMainAction() {
        return this.remainingMainActions.canPerform();
    }

    public boolean canPerformQuickAction() {
        return this.remainingQuickActions.canPerform();
    }

    /**
     * Performs an action: the number of remaining main commands is decremented by one
     * @throws NoRemainingActionsException if the player do not have any remaining main commands
     */
    public void performMainAction() {
        this.remainingMainActions.perform();
    }

    /**
     * Performs an action: the number of remaining quick commands is decremented by one
     * @throws NoRemainingActionsException if the player do not have any remaining quick commands
     */
    public void performQuickAction() {
        this.remainingQuickActions.perform();
    }

    /**
     * Sets the number of remaining main commands in this turn
     * @param remainingActions is the number to be set as number of remaining main commands
     * @throws IllegalArgumentException if the parameter is negative
     */
    public void setRemainingMainActions(int remainingActions) {
        this.remainingMainActions.setRemaining(remainingActions);
    }

    /**
     * Sets the number of remaining quick commands in this turn
     * @param remainingActions is the number to be set as number of remaining quick commands
     * @throws IllegalArgumentException if the parameter is negative
     */
    public void setRemainingQuickActions(int remainingActions) {
        this.remainingQuickActions.setRemaining(remainingActions);
    }

    /**
     * Increments the number of main commands in this turn
     * @param increment is the number of main commands to be added for this turn
     * @throws IllegalArgumentException if the increment is negative
     */
    public void addRemainingMainActions(int increment) {
        this.remainingMainActions.addActions(increment);
    }

    /**
     * Increments the number of quick commands in this turn
     * @param increment is the number of quick commands to be added for this turn
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
    public int getAssistantsNumber(){
    	return this.assistants.size();
    }

    /**
     * Takes and removes assistans from the assistants owned by the player
     * @param number is the number of assistants to be taken
     * @throws NoRemainingAssistantsException if there are no assistants remaining
     */
    public void takeAssistants(int number) {
        if (this.assistants.size() < number)
            throw new NoRemainingAssistantsException();
        for (int i = 0; i < number; i++)
            this.assistants.poll();
    }

    /**
     * Adds an assistant to the assistants owned by the player
     * @param assistant is the assistant to be added to the assistants owned by the player
     * @throws NullPointerException if the parameter is null
     */
    public void addAssistant(Assistant assistant) {
        if (assistant == null)
            throw new NullPointerException();
        assistant.setOwner(this);
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
        card.setOwner(this);
        this.cards.add(card);
    }

    public BusinessPermissionTile hasPermissionTile(String city) {
        for (BusinessPermissionTile tile: this.tiles)
            if (!tile.canBuildIn(city))
                return tile;
        throw new InvalidCardsException();
    }

    public void useBPT(BusinessPermissionTile tile) {
        this.tiles.remove(tile);
        this.discardedTiles.add(tile);
    }

    /**
     * Adds a business permission tile to the tiles owned by the player
     * @param tile is the business permission tile to be added to the tiles owned by the player
     * @throws NullPointerException if the parameter is null
     */
    public void addPermissionTile(BusinessPermissionTile tile) {
        if (tile == null)
            throw new NullPointerException();
        tile.setOwner(this);
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
    public Collection<PoliticCard> takeCards(Collection<PoliticColor> cardsColors) {
        LinkedList<PoliticCard> cards = new LinkedList<>(this.cards);
        LinkedList<PoliticCard> removed = new LinkedList<>();
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
            //PoliticCard c=takeCard(color)
            cards.remove(c);
            removed.add(c);
        }
        this.cards.removeAll(removed);
        return removed;
    }

    public PoliticCard takeCard(PoliticCard politicCard){
    	PoliticCard removedCard=null;
    	for(PoliticCard card : this.cards){
    		if(card.equals(politicCard)){
    			removedCard = card;
    			break;
    		}
    	}
    	if(removedCard == null)
    		throw new InvalidCardsException();
    	this.cards.remove(removedCard);
    	return removedCard;
    }

    /**
     * Returns the number of coins owned by the player
     * @return the number of coins owned by the player
     */
    public int getCoinsNumber() {
        return this.coins.getValue();
    }

}