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

public class ChangeBPTCommandTest {

	Region region;
	
	@Before
	public void setUp() {
		region = Region.createRegion("Veneto", new ArrayList<>(), new BusinessPermissionTileDeck(new ArrayList<>()), Balcony.createBalcony(4), new EmptyBonus());
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithRegionNull() {
		new ChangeBPTCommand(null);
	}
	
	@Test
	public void testGetters() {
		ChangeBPTCommand command = new ChangeBPTCommand(region.getState());
		RegionDTO region = Region.createRegion("Veneto", new ArrayList<>(), new BusinessPermissionTileDeck(new ArrayList<>()), Balcony.createBalcony(4), new EmptyBonus()).getState();
		
		assertEquals(region, command.getRegion());
	}

}
