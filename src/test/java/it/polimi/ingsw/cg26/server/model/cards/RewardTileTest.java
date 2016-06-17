package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.bonus.AssistantBonus;
import it.polimi.ingsw.cg26.server.model.bonus.*;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class RewardTileTest {

    private RewardTile tile;
    private RewardTile tile2;

    @Before
    public void setUp() throws Exception {
    	Bonus bonuses = new AssistantBonus(new EmptyBonus(), 4);
    	Bonus bonuses2 = new CoinBonus(new EmptyBonus(), 2);
        tile = new RewardTile(bonuses);
        tile2 = new RewardTile(bonuses2);
        
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new RewardTile(null);
    }
    
    @Test
    public void testConstructorShouldNotFail() throws Exception {
        RewardTile tile3 =new RewardTile(new CoinBonus(new EmptyBonus(), 2));
        assertNotNull(tile3);
    }

    @Test
    public void testGetState() throws Exception {
        tile.getState();
    }
    
    @Test
    public void testGetBonuses() throws Exception {
        assertEquals(tile.getBonuses(), new AssistantBonus(new EmptyBonus(), 4));
    }

    
    
    @Test
    public void testApply() throws Exception {
        NobilityCell nobilityCell = NobilityCell.createNobilityCell(10, null, new EmptyBonus());
        NobilityCell nobilityCell2 = NobilityCell.createNobilityCell(9, nobilityCell, new EmptyBonus());
        LinkedList<PoliticCard> cards = new LinkedList<>();
        cards.add(new PoliticCard(new PoliticColor("aaaa")));
        LinkedList<Assistant> assistants = new LinkedList<>();
        assistants.add(new Assistant());
        Player player = new Player(1234, "name", nobilityCell2, 10, cards, assistants);
        tile.apply(player);
        assertEquals(player.getAssistantsNumber(), 5);
    }
    
    
    
    
    @Test
    public void testToString() {
        assertEquals(tile.toString(), "RewardTile{" +
				"bonuses=" + new AssistantBonus(new EmptyBonus(), 4) +
				'}');
    }
    
    
    @Test
    public void testEquals() {
    	
    	Bonus bonuses1 = new AssistantBonus(new EmptyBonus(), 4);
    	RewardTile tile1 = new RewardTile(bonuses1);
    	
    	assertEquals(tile, tile);
    	assertEquals(tile, tile1);
    	assertFalse(tile.equals(null));
    	assertFalse(tile.equals(bonuses1));
    	assertFalse(tile2.equals(bonuses1));
    	
    	
    	
    }
    
    
    @Test
    public void testHashCode() {
    	
    	assertEquals(tile.hashCode(), tile.hashCode());
    	assertNotEquals(tile.hashCode(), tile2.hashCode());
    	
    }
    
    
    
}