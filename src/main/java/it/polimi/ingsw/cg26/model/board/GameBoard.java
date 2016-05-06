package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.model.player.Player;

/**
 * 
 */
public class GameBoard {

	private PoliticDeck politicDeck;
	
    /**
     * @param
     */
    public void GameBoard() {
        // TODO implement here
    }

    /**
     * @param region 
     * @param color
     */
    public void elect(String region, String color) {
        // TODO implement here
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