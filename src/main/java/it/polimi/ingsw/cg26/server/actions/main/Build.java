package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.common.state.CityState;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.board.Region;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public class Build extends Action {

    private final CityState city;

    public Build(CityState city) {
        if (city == null)
            throw new NullPointerException();
        this.city = city;
    }

    @Override
    public void apply(GameBoard gameBoard, Player currentPlayer) {
        if (!currentPlayer.canPerformMainAction())
            throw new NoRemainingActionsException();
        BusinessPermissionTile tile = currentPlayer.hasPermissionTile(city);
        if (tile == null)
            throw new InvalidCardsException();
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
