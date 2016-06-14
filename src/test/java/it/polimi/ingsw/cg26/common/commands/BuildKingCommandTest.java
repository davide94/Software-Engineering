package it.polimi.ingsw.cg26.common.commands;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;

public class BuildKingCommandTest {

	private List<PoliticCardDTO> cards;
	
	private City city;
	
	@Before
	public void setUp() {
		cards = new ArrayList<>();
		cards.add(new PoliticCard(new PoliticColor("blu")).getState());
		cards.add(new PoliticCard(new PoliticColor("arancione")).getState());
		cards.add(new PoliticCard(new PoliticColor("rosso")).getState());
		city = City.createCity("Roma", CityColor.createCityColor("rosso"), new EmptyBonus());
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithCityNull() {
		new BuildKingCommand(null, cards);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithCardsNull() {
		new BuildKingCommand(city.getState(), null);
	}
	
	@Test
	public void testGetters() {
		BuildKingCommand command = new BuildKingCommand(city.getState(), cards);
		CityDTO city = City.createCity("Roma", CityColor.createCityColor("rosso"), new EmptyBonus()).getState();
		List<PoliticCardDTO> cards = new ArrayList<>();
		cards.add(new PoliticCard(new PoliticColor("blu")).getState());
		cards.add(new PoliticCard(new PoliticColor("arancione")).getState());
		cards.add(new PoliticCard(new PoliticColor("rosso")).getState());
		
		assertEquals(city, command.getCity());
		assertEquals(cards, command.getCards());
	}

}
