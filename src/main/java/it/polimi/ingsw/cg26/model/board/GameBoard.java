package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.exceptions.*;
import it.polimi.ingsw.cg26.model.cards.*;
import it.polimi.ingsw.cg26.model.player.Player;
import java.util.Collection;

/**
 * 
 */
public class GameBoard {
	
	private final PoliticDeck politicDeck;
	
	private Collection<Region> regions;
	
	private Collection<Councillor> councillorsPool;


    public GameBoard(PoliticDeck deck, Collection<Councillor> councillors, Balcony balcony, Collection<Region> regions, NobilityTrack nobilityTrack, King king) {
    	this.politicDeck=deck;
    }

    /**
     * @param region 
     * @param color
     */
    public void elect(String region, String color) {
    	Councillor addCouncillor=null;
    	Councillor droppedCouncillor;
    	
    	for(Councillor councillor : councillorsPool){
    		if(councillor.getColor().colorString()==color){
    		addCouncillor = councillor;
    		break;
    		}
    	}
    	if(addCouncillor==null){
    		throw new NotExistingCouncillorException();
    	} else {
    		for (Region iterRegion : regions) {
    			if(iterRegion.getName()==region){
    				droppedCouncillor = iterRegion.elect(addCouncillor);
    				councillorsPool.add(droppedCouncillor);
    				councillorsPool.remove(addCouncillor);
    				break;
    			}
    		}
    		// TODO implement here aggiungere eccezione per regione non valida
    	}
    }

    /**
     * @param
     * @return
     */
    public Boolean acquireBPT(PoliticCard[] cards, String regions, int l) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Boolean build(Player p, String city) {
        // TODO implement here
        return null;
    }

    /**
     * @param region 
     * @return
     */
    public Boolean changeBPT(String region) {
        // TODO implement here
        return null;
    }

    /**
     * @param city
     */
    public void buildKing(String city) {
        // TODO implement here
    }

    /**
     * @return
     */
    public int distance(String a, String b) {
        // TODO implement here
        return 0;
    }

	/**
	 * @return the politicDeck
	 */
	public PoliticDeck getPoliticDeck() {
		return politicDeck;
	}

}