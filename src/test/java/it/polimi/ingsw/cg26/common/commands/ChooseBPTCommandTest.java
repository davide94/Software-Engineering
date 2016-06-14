package it.polimi.ingsw.cg26.common.commands;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.server.model.board.Balcony;
import it.polimi.ingsw.cg26.server.model.board.Region;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTileDeck;

public class ChooseBPTCommandTest {

	private Region region;
	
	@Before
	public void setUp() {
		region = Region.createRegion("Lombardia", new ArrayList<>(), new BusinessPermissionTileDeck(new ArrayList<>()), Balcony.createBalcony(4), new EmptyBonus());
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithRegionNull() {
		new ChooseBPTCommand(null, 1);
	}
	
	@Test
	public void testGetters(){
		ChooseBPTCommand command = new ChooseBPTCommand(region.getState(), 0);
		RegionDTO region = Region.createRegion("Lombardia", new ArrayList<>(), new BusinessPermissionTileDeck(new ArrayList<>()), Balcony.createBalcony(4), new EmptyBonus()).getState();
		
		assertEquals(region, command.getChosenRegion());
		assertEquals(0, command.getChosenPosition());
	}
}
