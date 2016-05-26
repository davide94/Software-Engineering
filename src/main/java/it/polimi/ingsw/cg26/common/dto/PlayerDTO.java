package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 */
public class PlayerDTO implements Serializable {

    private static final long serialVersionUID = -7431187814932580599L;

    private final String name;

    private final int victoryPoints;

    private final int coins;

    private final int remainingMainActions;

    private final int remainingQuickActions;

    private final int nobilityCell;

    private final int assistantsNumber;

    private final Collection<PoliticCardDTO> cards;

    private final Collection<BusinessPermissionTileDTO> tiles;

    private final Collection<BusinessPermissionTileDTO> discardedTiles;

    /**
     * Constructs a Player DTO object
     * @param name is the name of the player
     * @param token is the token associated to the player
     * @param victoryPoints is the number of Victory Points owned by the Player
     * @param coins is the number of Coins owned by the Player
     * @param remainingMainActions is the number of remaining Main Actions for the Player
     * @param remainingQuickActions is the number of remaining Main Actions for the Player
     * @param nobilityCell is the index of the Nobility Cell where the Player is
     * @param assistantsNumber is the number of Assistants owned by the Player
     * @param cards is a collection of Politic Cards DTO
     * @param tiles is a collection of Business Permit Tiles DTO
     * @param discardedTiles is a collection of Business Permit Tiles DTO
     * @throws NullPointerException if any parameter is null
     * @throws IllegalArgumentException if any String parameter is empty or an integer parameter is negative
     */
    public PlayerDTO(String name, int victoryPoints, int coins, int remainingMainActions, int remainingQuickActions, int nobilityCell, int assistantsNumber, Collection<PoliticCardDTO> cards, Collection<BusinessPermissionTileDTO> tiles, Collection<BusinessPermissionTileDTO> discardedTiles) {
        if (cards == null || tiles == null || discardedTiles == null)
            throw new NullPointerException();
        if (name.isEmpty() || victoryPoints < 0 || coins < 0 || remainingMainActions < 0 || remainingQuickActions < 0 || nobilityCell < 0 || assistantsNumber < 0)
            throw new IllegalArgumentException();
        this.name = name;
        this.victoryPoints = victoryPoints;
        this.coins = coins;
        this.remainingMainActions = remainingMainActions;
        this.remainingQuickActions = remainingQuickActions;
        this.nobilityCell = nobilityCell;
        this.assistantsNumber = assistantsNumber;
        this.cards = cards;
        this.tiles = tiles;
        this.discardedTiles = discardedTiles;
    }

    /**
     * Returns the name of the player
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of Victory Points owned by the Player
     * @return the number of Victory Points owned by the Player
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Returns the number of Coins owned by the Player
     * @return the number of Coins owned by the Player
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Returns the number of remaining Main Actions for the Player
     * @return the number of remaining Main Actions for the Player
     */
    public int getRemainingMainActions() {
        return remainingMainActions;
    }

    /**
     * Returns the number of remaining Quick Actions for the Player
     * @return the number of remaining Quick Actions for the Player
     */
    public int getRemainingQuickActions() {
        return remainingQuickActions;
    }

    /**
     * Returns the index of the Nobility Cell where the Player is
     * @return the index of the Nobility Cell where the Player is
     */
    public int getNobilityCell() {
        return nobilityCell;
    }

    /**
     * Returns the number of Assistants owned by the Player
     * @return the number of Assistants owned by the Player
     */
    public int getAssistantsNumber() {
        return assistantsNumber;
    }

    /**
     * Returns a collection of Politic Cards DTO owned by the Player
     * @return a collection of Politic Cards DTO owned by the Player
     */
    public Collection<PoliticCardDTO> getCards() {
        return cards;
    }

    /**
     * Returns a collection of Business Permit Tiles DTO owned by the Player and not used yet
     * @return a collection of Business Permit Tiles DTO owned by the Player and not used yet
     */
    public Collection<BusinessPermissionTileDTO> getTiles() {
        return tiles;
    }

    /**
     * Returns a collection of Business Permit Tiles DTO owned by the Player and already used
     * @return a collection of Business Permit Tiles DTO owned by the Player and already used
     */
    public Collection<BusinessPermissionTileDTO> getDiscardedTiles() {
        return discardedTiles;
    }
}
