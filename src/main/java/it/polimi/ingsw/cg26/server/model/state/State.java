package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.List;

/**
 *
 */
public interface State {

    Player getCurrentPlayer();

    default State startMatch(List<Player> players) {
        return this;
    }

    default boolean canPerformRegularAction(long token) {
        return false;
    }

    default boolean canSell(long token) {
        return false;
    }

    default boolean canBuy(long token) {
        return false;
    }

    default State regularActionPerformed() {
        return this;
    }

    default State sellFolded() {
        return this;
    }

    default State buyFolded() {
        return this;
    }

    default State tenEmporiumsBuilt() {
        return this;
    }
}
