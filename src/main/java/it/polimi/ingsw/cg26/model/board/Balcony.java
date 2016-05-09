package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.cards.Councillor;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;



/**
 * 
 */
public class Balcony {
	
	
	private Queue<Councillor> councillors;
	
	

    public Balcony() {
    	this.councillors = new LinkedList<Councillor>();
    	 
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
    	councillors.add(c);
    	Councillor droppedCouncillor= councillors.poll();
    	return droppedCouncillor;
    	}
    }
    
    public boolean checkPoliticCardsCouncillors(List<PoliticCard> politicCards){
    	if(politicCards == null){
    		throw new NullPointerException();
    	} else {
    		for(Councillor iterCouncillor : councillors){
    			for(PoliticCard iterPoliticCard : politicCards){
    				if(iterCouncillor.getColor().equals(iterPoliticCard.getColor())){
    					politicCards.remove(iterPoliticCard);
    				}
    				if(politicCards.size() == 0){
    					return true;
    				}
    			}
    		}
    		return false;
    	}
    }

}