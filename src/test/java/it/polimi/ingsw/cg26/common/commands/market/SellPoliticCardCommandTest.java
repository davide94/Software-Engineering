package it.polimi.ingsw.cg26.common.commands.market;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;

public class SellPoliticCardCommandTest {

	private PoliticCardDTO politicCard;
	
	@Before
	public void setUp() {
		politicCard = new PoliticCardDTO(new PoliticColorDTO("color"), 3, "player1");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithPriceZero() {
		new SellPoliticCardCommand(0, politicCard);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithNegativePrice() {
		new SellPoliticCardCommand(-2, politicCard);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithCardNull() {
		new SellPoliticCardCommand(4, null);
	}
	
	@Test
	public void testGetCard() {
		SellPoliticCardCommand command = new SellPoliticCardCommand(4, politicCard);
		PoliticCardDTO card = new PoliticCardDTO(new PoliticColorDTO("color"), 3, "player1");
		
		assertEquals(card, command.getPoliticCard());
	}
	
	@Test
	public void testGetPrice() {
		SellPoliticCardCommand command = new SellPoliticCardCommand(10, politicCard);
		
		assertEquals(10, command.getPrice());
	}
}
