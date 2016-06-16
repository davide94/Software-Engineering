package it.polimi.ingsw.cg26.common.commands.market;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.AssistantDTO;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.CoinBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;

public class BuyCommandTest {

	private PoliticCardDTO card;
	
	private AssistantDTO assistant;
	
	private BusinessPermissionTileDTO bpt;
	
	@Before
	public void setUp() {
		card = new PoliticCardDTO(new PoliticColorDTO("color1"), 2, "player1");
		assistant = new AssistantDTO(5, "player2");
		List<String> cities = new ArrayList<>();
		cities.add("Milano");
		cities.add("Roma");
		bpt = new BusinessPermissionTileDTO(cities, new CoinBonusDTO(new EmptyBonusDTO(), 2), 7, "player1");
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithSellableNull() {
		new BuyCommand(null);
	}
	
	@Test
	public void testGetterWithPoliticCard() {
		BuyCommand command = new BuyCommand(this.card);
		PoliticCardDTO card = new PoliticCardDTO(new PoliticColorDTO("color1"), 2, "player1");
		
		assertEquals(card, command.getSellable());
	}
	
	@Test
	public void testGetterWithAssistant() {
		BuyCommand command = new BuyCommand(this.assistant);
		AssistantDTO assistant = new AssistantDTO(5, "player2");
		
		assertEquals(assistant, command.getSellable());
	}
	
	@Test
	public void testGetterWithBPT() {
		BuyCommand command = new BuyCommand(this.bpt);
		List<String> cities = new ArrayList<>();
		cities.add("Milano");
		cities.add("Roma");
		BusinessPermissionTileDTO bpt = new BusinessPermissionTileDTO(cities, new CoinBonusDTO(new EmptyBonusDTO(), 2), 7, "player1");
		
		assertEquals(bpt, command.getSellable());
	}

}
