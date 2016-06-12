package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.Change;
import it.polimi.ingsw.cg26.common.update.change.CityChange;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.*;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class Build extends Action {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
	 * The City where the player wants to build the emporium
	 */
    private final CityDTO city;
    
    /**
     * The Business Permit Tile the player wants to use to build the emporium
     */
    private final BusinessPermissionTileDTO bPTState;

    /**
     * Construct a build action
     * @param city is the city in which the player wants to build his emporium
     * @param bPTState the tile that the user wants to use to build
     * @throws NullPointerException if the argument is null
     */
    public Build(CityDTO city, BusinessPermissionTileDTO bPTState, long token) {
        super(token);
        if (city == null || bPTState == null)
            throw new NullPointerException();
        this.city = city;
        this.bPTState = bPTState;
    }

    /**
     * @throws NoRemainingActionsException if the player has no more remaining actions to do
     * @throws NoRemainingAssistantsException if the player cannot pay the required number of assistant to build
     * @throws InvalidCityException if the city is not in the BPT cities list
     */
    @Override
    public void apply(GameBoard gameBoard) throws NoRemainingActionsException, InvalidCardsException, CityNotFoundException, NoRemainingAssistantsException, ExistingEmporiumException, NoRemainingCardsException, InvalidCityException {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        if (!currentPlayer.canPerformMainAction())
            throw new NoRemainingActionsException();
        BusinessPermissionTile tile = currentPlayer.hasPermissionTile(bPTState);
        if(!tile.canBuildIn(city))
        	throw new InvalidCityException();
        City realCity = gameBoard.getCity(city);
        int empNumber = realCity.getEmporiumsNumber();
        if (currentPlayer.getAssistantsNumber() < empNumber)
            throw new NoRemainingAssistantsException();
        realCity.build(currentPlayer);
        
        CityColor color=realCity.getColor();
        gameBoard.checkBonuses(currentPlayer, color);
        currentPlayer.takeAssistants(empNumber);
        currentPlayer.useBPT(tile);
        currentPlayer.performMainAction();
        notifyChange(gameBoard);
        checkPendingRequest(gameBoard);
    }
    
    @Override
    public void notifyChange(GameBoard gameBoard) {
        Change change = null;
        try {
            change = new CityChange(new BasicChange(), gameBoard.getCity(city).getState());
        } catch (CityNotFoundException e) {
            log.error("Error creating CityChange", e);
        }
        notifyDecoratingPlayersChange(gameBoard, change);
    }
}
