package it.polimi.ingsw.cg26.server.model.bonus;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;

public class BonusTest {
	
	private Bonus bonus;
	
	private PoliticDeck politicDeck;
	
	@Before
	public void setUp(){
		this.bonus = new EmptyBonus();
				
		List<PoliticCard> cards = new LinkedList<>();
		cards.add(new PoliticCard(new PoliticColor("arancione")));
		politicDeck = new PoliticDeck(cards);
	}

	@Test
	public void testGetBonusNames1() throws Exception {
		Bonus bonus = new AssistantBonus(new TakePlayerBPTBonus(new VictoryBonus(this.bonus, 3), 1), 4);
		List<String> bonusNames = bonus.getBonusNames();
		
		assertTrue(bonusNames.contains("Victory"));
		assertTrue(bonusNames.contains("TakePlayerBPT"));
		assertTrue(bonusNames.contains("Assistant"));
		assertEquals(3, bonusNames.size());
	}
	
	@Test
	public void testGetBonusNames2() throws Exception {
		Bonus bonus = new NobilityBonus(new CardBonus(new TakeYourCityBonus(new MainActionBonus(this.bonus, 1), 2), 5, politicDeck), 2);
		List<String> bonusNames = bonus.getBonusNames();
		
		assertTrue(bonusNames.contains("Nobility"));
		assertTrue(bonusNames.contains("Card"));
		assertTrue(bonusNames.contains("TakeYourCity"));
		assertTrue(bonusNames.contains("MainAction"));
		assertEquals(4, bonusNames.size());
	}
	
	@Test
	public void testGetBonusNames3() throws Exception {
		Bonus bonus = new TakeBPTBonus(new CoinBonus(this.bonus, 3), 1);
		List<String> bonusNames = bonus.getBonusNames();
		
		assertTrue(bonusNames.contains("TakeBPT"));
		assertTrue(bonusNames.contains("Coin"));
		assertEquals(2, bonusNames.size());
	}

}
