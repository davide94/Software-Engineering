package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public class Build extends Action {

    private final String cityName;

    public Build(String cityName) {
        if (cityName == null)
            throw new NullPointerException();
        this.cityName = cityName;
    }

    @Override
    public void apply(GameBoard gameBoard, Player currentPlayer) {
        if (!currentPlayer.canPerformMainAction())
            throw new NoRemainingActionsException();
        BusinessPermissionTile tile = currentPlayer.hasPermissionTile(this.cityName);
        if (tile == null)
            throw new InvalidCardsException();
        City city = gameBoard.getCity(this.cityName);
        int empNumber = city.getEmporiumsNumber();
        if (currentPlayer.getAssistantsNumber() < empNumber)
            throw new NoRemainingAssistantsException();
        city.build(currentPlayer);
        currentPlayer.takeAssistants(empNumber);
        currentPlayer.useBPT(tile);
        currentPlayer.performMainAction();
    }

}
