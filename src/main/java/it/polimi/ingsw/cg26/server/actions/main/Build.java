package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.common.state.BusinessPermissionTileState;
import it.polimi.ingsw.cg26.common.state.CityState;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public class Build extends Action {

    private final CityState city;
    
    private final BusinessPermissionTileState bPTState;

    /**
     * 
     * @param city the city in which the player wants to build his emporium
     * @param bPTState the tile that the user wants to use to build
     * @throws NullPointerException if the argument is null
     */
    public Build(CityState city, BusinessPermissionTileState bPTState) {
        if (city == null || bPTState == null)
            throw new NullPointerException();
        this.city = city;
        this.bPTState = bPTState;
    }

    /**
     * @throws NoRemainingActionsException if the player has no more remaining actions to do
     * @throws NoRemainingAssistantsException if the player cannot pay the required number of assistant to build
     */
    @Override
    public void apply(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        if (!currentPlayer.canPerformMainAction())
            throw new NoRemainingActionsException();
        BusinessPermissionTile tile = currentPlayer.hasPermissionTile(bPTState);
        City realCity = gameBoard.getCity(city);
        int empNumber = realCity.getEmporiumsNumber();
        if (currentPlayer.getAssistantsNumber() < empNumber)
            throw new NoRemainingAssistantsException();
        realCity.build(currentPlayer);
        
        
        CityColor color=realCity.getColor();
        if(gameBoard.checkColorBonuses(currentPlayer, color)){
        	
        	currentPlayer.addVictoryPoints(10); //come faccio a sapere quanti ne devo dare?
            //prendo tessera del re
        	
        }
        
        
        //si può fare lo stesso per la regione però
        //City dovrebbe avere il reference a Region
        
        /*
         if(realCity.getRegion().checkRegionBonuses(currentPlayer)){
         //applico il bonus
         //prendo tessera re
          
          }
          */
        
        
        
        
        
        
        currentPlayer.takeAssistants(empNumber);
        currentPlayer.useBPT(tile);
        currentPlayer.performMainAction();
    }

}
