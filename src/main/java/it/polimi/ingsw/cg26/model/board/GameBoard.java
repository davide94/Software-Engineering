package it.polimi.ingsw.cg26.model.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.polimi.ingsw.cg26.model.cards.Councillor;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.model.player.Player;

/**
 * 
 */
public class GameBoard {

	private PoliticDeck politicDeck;
	
	private List<Region> regions;
	
    /**
     * @param
     */
    public GameBoard() {
    	this.regions = new ArrayList<Region>();
        // TODO implement here
    }

    /**
     * @param region in which the player wants to elect a councillor
     * @param councillor the councillor that the player wants to elect
     */
    public Councillor elect(String region, Councillor councillor) {
    	for (Iterator<Region> iterator = regions.iterator(); iterator.hasNext();) {
			Region iterRegion = iterator.next();
			if(iterRegion.getName()==region){
				return iterRegion.elect(councillor);
			}
		}
    	return null;
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