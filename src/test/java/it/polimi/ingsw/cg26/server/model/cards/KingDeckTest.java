package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class KingDeckTest {

    private KingDeck deck;

    @Before
    public void setUp() throws Exception {
        Collection<RewardTile> tiles = new LinkedList<>();
        tiles.add(new RewardTile(new LinkedList<Bonus>()));
        deck = new KingDeck(tiles);
    }

    @Test
    public void testGetState() throws Exception {
        deck.getState();
    }
}