package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class PlayerState implements Serializable {

    private static final long serialVersionUID = -7431187814932580599L;

    private final String name;

    private final long token;

    private final int victoryPoints;

    private final int coins;

    private final int remainingMainActions;

    private final int remainingQuickActions;

    private final int nobilityCell;

    private final int assistantsNumber;

    private final List<PoliticCardState> cards;

    private final List<BusinessPermissionTileState> tiles;

    private final List<BusinessPermissionTileState> discardedTiles;

    public PlayerState(String name, long token, int victoryPoints, int coins, int remainingMainActions, int remainingQuickActions, int nobilityCell, int assistantsNumber, List<PoliticCardState> cards, List<BusinessPermissionTileState> tiles, List<BusinessPermissionTileState> discardedTiles) {
        this.name = name;
        this.token = token;
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

    public String getName() {
        return name;
    }

    public long getToken() {
        return token;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getCoins() {
        return coins;
    }

    public int getRemainingMainActions() {
        return remainingMainActions;
    }

    public int getRemainingQuickActions() {
        return remainingQuickActions;
    }

    public int getNobilityCell() {
        return nobilityCell;
    }

    public int getAssistantsNumber() {
        return assistantsNumber;
    }

    public List<PoliticCardState> getCards() {
        return cards;
    }

    public List<BusinessPermissionTileState> getTiles() {
        return tiles;
    }

    public List<BusinessPermissionTileState> getDiscardedTiles() {
        return discardedTiles;
    }
}
