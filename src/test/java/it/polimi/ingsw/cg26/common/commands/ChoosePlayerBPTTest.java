package it.polimi.ingsw.cg26.common.commands;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;

public class ChoosePlayerBPTTest {

	private List<BusinessPermissionTileDTO> tiles;
	
	@Before
	public void setUp() {
		tiles = new ArrayList<>();
		List<City> cities1 = new ArrayList<>();
		cities1.add(City.createCity("Milano", CityColor.createCityColor("grigio"), new EmptyBonus()));
		BusinessPermissionTile tile1 = new BusinessPermissionTile(cities1, new EmptyBonus());
		List<City> cities2 = new ArrayList<>();
		cities2.add(City.createCity("Roma", CityColor.createCityColor("blu"), new EmptyBonus()));
		BusinessPermissionTile tile2 = new BusinessPermissionTile(cities2, new EmptyBonus());
		tiles.add(tile1.getState());
		tiles.add(tile2.getState());
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithChosenBPTNull() {
		new ChoosePlayerBPTCommand(null);
	}
	
	@Test
	public void testGetters() {
		ChoosePlayerBPTCommand command = new ChoosePlayerBPTCommand(tiles);
		List<BusinessPermissionTileDTO> tiles = new ArrayList<>();
		List<City> cities1 = new ArrayList<>();
		cities1.add(City.createCity("Milano", CityColor.createCityColor("grigio"), new EmptyBonus()));
		BusinessPermissionTile tile1 = new BusinessPermissionTile(cities1, new EmptyBonus());
		List<City> cities2 = new ArrayList<>();
		cities2.add(City.createCity("Roma", CityColor.createCityColor("blu"), new EmptyBonus()));
		BusinessPermissionTile tile2 = new BusinessPermissionTile(cities2, new EmptyBonus());
		tiles.add(tile1.getState());
		tiles.add(tile2.getState());
		
		assertEquals(tiles, command.getChosenBPT());
	}

}
