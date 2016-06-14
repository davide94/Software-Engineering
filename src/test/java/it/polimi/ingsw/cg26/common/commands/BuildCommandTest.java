package it.polimi.ingsw.cg26.common.commands;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;

public class BuildCommandTest {

	private BusinessPermissionTile tile;
	
	private City city;
	
	@Before
	public void setUp() {
		List<City> cities = new ArrayList<>();
		cities.add(City.createCity("Milano", CityColor.createCityColor("grigio"), new EmptyBonus()));
		tile = new BusinessPermissionTile(cities, new EmptyBonus());
		city = City.createCity("Roma", CityColor.createCityColor("rosso"), new EmptyBonus());
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithCityNull() {
		new BuildCommand(null, tile.getState());
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithTileNull() {
		new BuildCommand(city.getState(), null);
	}
	
	@Test
	public void testGetters() {
		BuildCommand command = new BuildCommand(city.getState(), tile.getState());
		CityDTO city = City.createCity("Roma", CityColor.createCityColor("rosso"), new EmptyBonus()).getState(); 
		assertEquals(city, command.getCity());
	}

}
