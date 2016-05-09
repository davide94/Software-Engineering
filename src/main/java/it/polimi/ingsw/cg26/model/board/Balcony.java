package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.cards.Councillor;
import java.util.Queue;
import java.util.LinkedList;



/**
 * 
 */
public class Balcony {
	
	
	private Queue<Councillor> balcony;
	
	

    public Balcony() {
    	this.balcony=new LinkedList<Councillor>();
    	 
    }
    

    /**
     * @param
     * @return
     */
    public Councillor elect(Councillor c) {
    	if(c==null){
    		throw new NullPointerException();
    	}
    	else{
    	balcony.add(c);
    	Councillor droppedCouncillor= balcony.poll();
    	return droppedCouncillor;
    	}
    }

}