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

public class ChooseCityCommandTest {

	private List<CityDTO> cities;
	
	@Before
	public void setUp() {
		cities = new ArrayList<>();
		cities.add(City.createCity("Roma", CityColor.createCityColor("rosso"), new EmptyBonus()).getState());
		cities.add(City.createCity("Milano", CityColor.createCityColor("grigio"), new EmptyBonus()).getState());
		cities.add(City.createCity("Torino", CityColor.createCityColor("nero"), new EmptyBonus()).getState());
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithCitiesNull() {
		new ChooseCityCommand(null);
	}
	
	@Test
	public void testGetters() {
		ChooseCityCommand command = new ChooseCityCommand(cities);
		List<CityDTO> cities = new ArrayList<>();
		cities.add(City.createCity("Roma", CityColor.createCityColor("rosso"), new EmptyBonus()).getState());
		cities.add(City.createCity("Milano", CityColor.createCityColor("grigio"), new EmptyBonus()).getState());
		cities.add(City.createCity("Torino", CityColor.createCityColor("nero"), new EmptyBonus()).getState());
		
		assertEquals(cities, command.getChosenCities());
	}

}
