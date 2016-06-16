package it.polimi.ingsw.cg26.common.commands.market;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.VictoryBonusDTO;

public class SellBPTCommandTest {

	private BusinessPermissionTileDTO tile;
	
	@Before
	public void setUp() {
		List<String> cities = new ArrayList<>();
		cities.add("Milano");
		cities.add("Roma");
		tile = new BusinessPermissionTileDTO(cities, new VictoryBonusDTO(new EmptyBonusDTO(), 3), 0, "player2");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithPriceZero() {
		new SellBPTCommand(0, tile);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithNegativePrice() {
		new SellBPTCommand(-1, tile);
	}

	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithBPTNull() {
		new SellBPTCommand(3, null);
	}
	
	@Test
	public void testGetTile() {
		SellBPTCommand command = new SellBPTCommand(6, this.tile);
		List<String> cities = new ArrayList<>();
		cities.add("Milano");
		cities.add("Roma");
		BusinessPermissionTileDTO tile = new BusinessPermissionTileDTO(cities, new VictoryBonusDTO(new EmptyBonusDTO(), 3), 0, "player2");
		
		assertEquals(tile, command.getTile());
	}
	
	@Test
	public void testGetPrice() {
		SellBPTCommand command = new SellBPTCommand(3, this.tile);
		
		assertEquals(3, command.getPrice());
	}
}
