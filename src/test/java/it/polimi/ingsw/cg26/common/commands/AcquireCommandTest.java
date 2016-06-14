package it.polimi.ingsw.cg26.common.commands;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.server.model.board.Balcony;
import it.polimi.ingsw.cg26.server.model.board.Region;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTileDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;

public class AcquireCommandTest {

	private Region region;
	
	private List<PoliticCardDTO> cards;
	
	@Before
	public void setUp() {
		region = Region.createRegion("Lombardia", new ArrayList<>(), new BusinessPermissionTileDeck(new ArrayList<>()), Balcony.createBalcony(4), new EmptyBonus());
		/*List<City> cities = new ArrayList<>();
		cities.add(City.createCity("Milano", CityColor.createCityColor("grigio"), new EmptyBonus()));
		tile = new BusinessPermissionTile(cities, new EmptyBonus());*/
		cards = new ArrayList<>();
		cards.add(new PoliticCard(new PoliticColor("verde")).getState());
		cards.add(new PoliticCard(new PoliticColor("giallo")).getState());
		cards.add(new PoliticCard(new PoliticColor("rosso")).getState());
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithRegionNull() {
		new AcquireCommand(null, new ArrayList<>(), 0);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithCardsNull() {
		new AcquireCommand(region.getState(), null, 0);
	}
	
	@Test
	public void testGetters() {
		AcquireCommand command = new AcquireCommand(region.getState(), cards, 0);
		RegionDTO region = Region.createRegion("Lombardia", new ArrayList<>(), new BusinessPermissionTileDeck(new ArrayList<>()), Balcony.createBalcony(4), new EmptyBonus()).getState();
		List<PoliticCardDTO> cards = new ArrayList<>();
		cards.add(new PoliticCard(new PoliticColor("verde")).getState());
		cards.add(new PoliticCard(new PoliticColor("giallo")).getState());
		cards.add(new PoliticCard(new PoliticColor("rosso")).getState());
		
		assertEquals(region, command.getRegion());
		assertEquals(cards, command.getCards());
		assertEquals(0, command.getPosition());
	}

}
