package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.cards.Councillor;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.model.player.Player;

import java.util.Collection;

/**
 * 
 */
public class GameBoard {

    /**
     *
     */
    public GameBoard(PoliticDeck deck, Collection<Councillor> councillors, Balcony kingsBalcony, Region coast, Region hills, Region mountains, NobilityTrack nobilityTrack, King king) {
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

}