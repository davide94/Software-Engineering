package it.polimi.ingsw.cg26.actions.main;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.model.board.City;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.player.Player;

/**
 *
 */
public class Build extends Action {

    private final String cityName;

    public Build(String token, String cityName) {
        super(token);
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
        currentPlayer.takeBPT(tile);
        currentPlayer.performMainAction();
    }

}
