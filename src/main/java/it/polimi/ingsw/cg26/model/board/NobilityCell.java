package it.polimi.ingsw.cg26.model.board;

import java.util.HashSet;
import java.util.Set;
import it.polimi.ingsw.cg26.model.player.Player;
import it.polimi.ingsw.cg26.model.bonus.*;

import java.util.List;
import java.util.ArrayList;



/**
 * 
 */
public class NobilityCell {
	
	
	 /**
     * The number of the Cell
     */
    private int CellNuber;

    
   /**
    * The players on the nobility cell
    */
    private Set<Player> players= new HashSet<Player>();
    
    /**
     * The bonus on the nobility cell
     */
    private List<Bonus> bonuses=new ArrayList<Bonus>();
    
    
    
    

    /**
     * Default constructor
     */
    public NobilityCell(int CellNumber,List<Bonus> bonuses) {
    	this.CellNuber= CellNumber;
    	this.bonuses=bonuses;
    }

   
    /**
     * @return
     */
    public NobilityCell next() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Boolean hasNext() {
        // TODO implement here
        return null;
    }

}