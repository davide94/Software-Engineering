package it.polimi.ingsw.cg26.server.model.player;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.update.request.Request;
import it.polimi.ingsw.cg26.server.exceptions.*;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * The Player class models a player
 */
public class Player {

	/**
	 * Name of the Player
	 */
	private final String name;

	/**
	 * Token
	 */
	private final long token;

	/**
	 *
	 */
	private boolean online;

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
	 * Reference to the choose command manager
	 */
	private final RemainingChooseAction remainingChooseActions;

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
	 * The collection of business permission tiles owned by the player, not used yet
	 */
	private final List<BusinessPermissionTile> tiles;

	/**
	 * The collection of business permission tiles owned by the player, already used
	 */
	private final List<BusinessPermissionTile> discardedTiles;
	
	/**
	 * A request not already satisfied by the player
	 */
	private final List<Request> pendingRequests;

	/**
	 * Constructs a Player
	 * @param nobilityCell reference to the cell in the nobility track that the player has to start from
	 * @param coins number of coins owned by the player
	 * @param assistants collection of assistants owned by the player
	 * @throws NullPointerException if any parameter is null
	 * @throws IllegalArgumentException if coins is negative
	 */
	public Player(long token, String name, NobilityCell nobilityCell, int coins, Collection<PoliticCard> cards,
			Collection<Assistant> assistants) {
		if (name == null || nobilityCell == null || cards == null || assistants == null)
			throw new NullPointerException();
		if (coins < 0)
			throw new IllegalArgumentException();

		this.token = token;
		this.name = name;
        online = true;
		this.victoryPoints = new VictoryPoints();
		this.coins = new Coins();
		this.currentNobilityCell = nobilityCell;
		this.coins.addCoins(coins);
		this.cards = new LinkedList<>();
		for (PoliticCard card : cards)
			addPoliticCard(card);
		this.assistants = new LinkedList<>();
		for (Assistant assistant : assistants)
			addAssistant(assistant);
		this.remainingMainActions = new RemainingMainActions();
		this.remainingQuickActions = new RemainingQuickActions();
		this.remainingChooseActions = new RemainingChooseAction();
		this.tiles = new LinkedList<>();
		this.discardedTiles = new LinkedList<>();
		this.pendingRequests = new ArrayList<>();
	}

	/**
	 * Generates the State of the player
	 * @return a PlayerDTO object that represents the current player's State
	 */
	public PlayerDTO getState() {
		LinkedList<PoliticCardDTO> cardsState = new LinkedList<>();
		LinkedList<BusinessPermissionTileDTO> tilesState = new LinkedList<>();
		LinkedList<BusinessPermissionTileDTO> discardedTilesState = new LinkedList<>();
		for (BusinessPermissionTile tile: tiles)
			tilesState.add(tile.getState());
		for (BusinessPermissionTile tile: discardedTiles)
			discardedTilesState.add(tile.getState());
		return new PlayerDTO(name, 0, online, victoryPoints.getValue(), coins.getValue(), remainingMainActions.getValue(), remainingQuickActions.getValue(), currentNobilityCell.getIndex(), assistants.size(), cardsState, tilesState, discardedTilesState);
	}

	/**
	 * Generates the State of the player
	 * @return a PlayerDTO object that represents the current player's State
	 */
	public PlayerDTO getFullState() {
		LinkedList<PoliticCardDTO> cardsState = new LinkedList<>();
		LinkedList<BusinessPermissionTileDTO> tilesState = new LinkedList<>();
		LinkedList<BusinessPermissionTileDTO> discardedTilesState = new LinkedList<>();
		for (PoliticCard card: cards)
			cardsState.add(card.getState());
		for (BusinessPermissionTile tile: tiles)
			tilesState.add(tile.getState());
		for (BusinessPermissionTile tile: discardedTiles)
			discardedTilesState.add(tile.getState());
		return new PlayerDTO(name, token, online, victoryPoints.getValue(), coins.getValue(), remainingMainActions.getValue(), remainingQuickActions.getValue(), currentNobilityCell.getIndex(), assistants.size(), cardsState, tilesState, discardedTilesState);
	}

	/**
	 * Returns the name of the player
	 * @return the name of the player
     */
	public String getName() {
		return name;
	}

	/**
	 * Returns the token of the player
	 * @return the token of the player
     */
	public long getToken() {
		return token;
	}

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean b) {
        online = b;
    }

	/**
	 * Checks if the player can perform a main action
	 * @return true if the player can perform a main action, false if not
     */
	public boolean canPerformMainAction() {
		return remainingMainActions.canPerform();
	}

	/**
	 * Checks if the player can perform a quick action
	 * @return true if the player can perform a quick action, false if not
	 */
	public boolean canPerformQuickAction() {
		return remainingQuickActions.canPerform();
	}
	
	/**
	 * Checks if the player can perform a choose action
	 * @return true if the player can perform a quick action, false if not
	 */
	public boolean canPerformChooseAction() {
		return remainingChooseActions.canPerform();
	}

	/**
	 * Performs an action: the number of remaining main commands is decremented by one
	 * @throws NoRemainingActionsException if the player do not have any remaining main commands
	 */
	public void performMainAction() throws NoRemainingActionsException {
		remainingMainActions.perform();
	}

	/**
	 * Performs an action: the number of remaining quick commands is decremented by one
	 * @throws NoRemainingActionsException if the player do not have any remaining quick commands
	 */
	public void performQuickAction() throws NoRemainingActionsException {
		remainingQuickActions.perform();
	}
	
	/**
	 * Performs an action: the number of remaining choose commands is decremented by one
	 * @throws NoRemainingActionsException if the player do not have any remaining choose commands
	 */
	public void performChooseAction() throws NoRemainingActionsException {
		remainingChooseActions.perform();
	}

	/**
	 * Sets the number of remaining main commands in this turn
	 * @param remainingActions is the number to be set as number of remaining main commands
	 * @throws IllegalArgumentException if the parameter is negative
	 */
	public void setRemainingMainActions(int remainingActions) {
		remainingMainActions.setRemaining(remainingActions);
	}

	/**
	 * Sets the number of remaining quick commands in this turn
	 * @param remainingActions is the number to be set as number of remaining quick commands
	 * @throws IllegalArgumentException if the parameter is negative
	 */
	public void setRemainingQuickActions(int remainingActions) {
		remainingQuickActions.setRemaining(remainingActions);
	}

	/**
	 * Increments the number of main commands in this turn
	 * @param increment is the number of main commands to be added for this turn
	 * @throws IllegalArgumentException if the increment is negative
	 */
	public void addRemainingMainActions(int increment) {
		remainingMainActions.addActions(increment);
	}

	/**
	 * Increments the number of quick commands in this turn
	 * @param increment is the number of quick commands to be added for this turn
	 * @throws IllegalArgumentException if the increment is negative
	 */
	public void addRemainingQuickActions(int increment) {
		remainingQuickActions.addActions(increment);
	}
	
	/**
	 * Increments the number of choose commands in this turn
	 * @param increment is the number of choose commands to be added for this turn
	 * @throws IllegalArgumentException if the increment is negative
	 */
	public void addRemainingChooseAction(int increment){
		remainingChooseActions.addActions(increment);
	}

	/**
	 * Gains a position in the nobility track
	 */
	public void incrementNobility() throws NoRemainingCardsException {
		if (currentNobilityCell.hasNext()) {
			currentNobilityCell = currentNobilityCell.next();
			currentNobilityCell.apply(this);
		}
	}

	/**
	 * Returns the nobility track's cell where the player currently is
	 * @return the nobility track's cell where the player currently is
	 */
	public NobilityCell getNobilityCell() {
		return currentNobilityCell;
	}

	/**
	 * Returns the number of assistants owned by the player
	 * @return the number of assistants owned by the player
	 */
	public int getAssistantsNumber() {
		return assistants.size();
	}

	/**
	 * Takes and removes assistans from the assistants owned by the player
	 * @param number is the number of assistants to be taken
	 * @throws NoRemainingAssistantsException if there are no assistants remaining
	 */
	public void takeAssistants(int number) throws NoRemainingAssistantsException {
		if (assistants.size() < number)
			throw new NoRemainingAssistantsException("You does not own enough assistants.");
		for (int i = 0; i < number; i++)
			assistants.poll();
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
		assistants.add(assistant);
	}

	/**
	 * Increments the number of coins owned by the player
	 * @param increment is the number of coins to be added
	 * @throws IllegalArgumentException if the parameter is negative
	 */
	public void addCoins(int increment) {
		coins.addCoins(increment);
	}

	/**
	 * Decrements the number of coins owned by the player
	 * @param decrement is the number of coins to be removed
	 * @throws IllegalArgumentException if the parameter is negative
	 * @throws NotEnoughMoneyException if the decrement is greater than the number of coins owned by the player
	 */
	public void removeCoins(int decrement) throws NotEnoughMoneyException {
		coins.removeCoins(decrement);
	}

	/**
	 * Increments the number of victory points
	 * @param increment is the number of victory points to be added
	 * @throws IllegalArgumentException if the parameter is negative
	 */
	public void addVictoryPoints(int increment) {
		victoryPoints.addPoints(increment);
	}

	/**
	 *
	 * @return
     */
	public int getVictoryPoints() {
		return victoryPoints.getValue();
	}

    /**
     *
     */
    public int getBPTNumber() {
        return tiles.size() + discardedTiles.size();
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

    /**
     *
     */
    public int getPoliticCardsNumber() {
        return cards.size();
    }

    /**
	 * Returns a BusinessPermitTile that can be used to build an emporium in a required city
	 * @param bPTState is the city where the tile must be able to build in
	 * @return the tile
	 * @throws InvalidCardsException if the player does not own the card
     */
	public BusinessPermissionTile hasPermissionTile(BusinessPermissionTileDTO bPTState) throws InvalidCardsException {
		for (BusinessPermissionTile tile : this.tiles)
			if (tile.getState().equals(bPTState))
				return tile;
		throw new InvalidCardsException("You does not own the required cards.");
	}

	public BusinessPermissionTile hasPermissionTileAlsoFaceDown(BusinessPermissionTileDTO bPTState) throws InvalidCardsException {
		try {
			return hasPermissionTile(bPTState);
		} catch (InvalidCardsException e) {
			for (BusinessPermissionTile tile : this.discardedTiles)
				if (tile.getState().equals(bPTState))
					return tile;
			throw e;
		}
	}
	
	/**
	 * Returns a BusinessPermissionTile equal to the BPT dto given, removes the tile from the player
	 * @param tileState the tileState given by the user
	 * @return the tile removed from the player
	 */
	public BusinessPermissionTile removeRealBPT(BusinessPermissionTileDTO tileState) throws InvalidTileException {
		BusinessPermissionTile tile = null;
		for(BusinessPermissionTile t : tiles){
			if(t.getState().equals(tileState)){
				tile=t;
				break;
			}
		}
		if(tile == null)
			throw new InvalidTileException("You does not own the required BPT.");
		this.tiles.remove(tile);
		tile.setOwner(null);
		return tile;
	}

	/**
	 * Marks a tile as used
	 * @param tile is the tile to mark as used
	 * @throws InvalidTileException if the player does not owns the tile
     */
	public void useBPT(BusinessPermissionTile tile) throws InvalidTileException {
		if(this.tiles.remove(tile))
			this.discardedTiles.add(tile);
		else
			throw new InvalidTileException("You does not own the required BPT.");
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
	 * Checks if the player owns the required Politic Cards
	 * @param requiredCards is a collection of cards to search
	 * @return true if finds every card, false if not
     */
	public boolean hasCards(Collection<PoliticCardDTO> requiredCards) {
		LinkedList<PoliticCard> cards = new LinkedList<>(this.cards);
		for (PoliticCardDTO requiredCard: requiredCards) {
			PoliticCard c = null;
			for (PoliticCard card : cards) {
				if (card.getColor().getState().equals(requiredCard.getColor())) {
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
	 * @param requiredCards is a collection of PoliticCardDTO that represents the required cards
	 * @return a collection of politic cards that match with the required
	 * @throws InvalidCardsException if the player does not owns all the cards required
	 */
	public Collection<PoliticCard> takeCards(Collection<PoliticCardDTO> requiredCards) throws InvalidCardsException {
		LinkedList<PoliticCard> removed = new LinkedList<>();
		for (PoliticCardDTO requiredCard: requiredCards)
			removed.add(takeCard(requiredCard));
		return removed;
	}

	/**
	 * Returns a politic card that match with the required
	 * @param politicCardDTO is a PoliticCardDTO that represents the required cards
	 * @return a politic cards that match with the required
	 * @throws InvalidCardsException if the player does not owns the required card
	 */
	public PoliticCard takeCard(PoliticCardDTO politicCardDTO) throws InvalidCardsException {
		PoliticCard removedCard = null;
		for (PoliticCard card : this.cards) {
			if (card.getColor().getState().equals(politicCardDTO.getColor())) {
				removedCard = card;
				break;
			}
		}
		if (removedCard == null)
			throw new InvalidCardsException("You does not own the required cards.");
		removedCard.setOwner(null);
		this.cards.remove(removedCard);
		return removedCard;
	}

	/**
	 * Returns the number of coins owned by the player
	 * @return the number of coins owned by the player
	 */
	public int getCoinsNumber() {
		return coins.getValue();
	}

	/**
	 * @return the pendingRequest
	 */
	public List<Request> getPendingRequest() {
		return new ArrayList<>(pendingRequests);
	}

	/**
	 * @param pendingRequest the pendingRequest to add
	 */
	public boolean addPendingRequest(Request pendingRequest) {
        if (pendingRequest == null)
            throw new NullPointerException();
		return pendingRequests.add(pendingRequest);
	}
	
	public void removePendingRequest(){
		pendingRequests.remove(0);
	}
}