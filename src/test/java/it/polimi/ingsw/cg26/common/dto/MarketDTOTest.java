package it.polimi.ingsw.cg26.common.dto;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MarketDTOTest {

	@Test
	public void testConstructMarketDTO() {
		List<SellableDTO> sellables = new ArrayList<SellableDTO>();
		PoliticCardDTO card = new PoliticCardDTO(new PoliticColorDTO("c1"), 4, "ajeje");
		AssistantDTO assistant = new AssistantDTO(2, "aldo");
		sellables.add(assistant);
		sellables.add(card);
		MarketDTO market = new MarketDTO(sellables);
		
		assertEquals(2, market.getOnSale().size());
		assertTrue(market.getOnSale().contains(assistant));
		assertTrue(market.getOnSale().contains(card));
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructMarketDTOWithNullOnSaleShouldThrowException(){
		new MarketDTO(null);
	}

}
