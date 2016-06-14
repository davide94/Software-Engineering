package it.polimi.ingsw.cg26.common.commands;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.server.model.board.Balcony;
import it.polimi.ingsw.cg26.server.model.board.Councillor;
import it.polimi.ingsw.cg26.server.model.board.Region;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTileDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;

public class ElectAsMainActionCommandTest {

	private Region region;
	
	private Councillor councillor;
	
	@Before
	public void setUp() {
		region = Region.createRegion("Lombardia", new ArrayList<>(), new BusinessPermissionTileDeck(new ArrayList<>()), Balcony.createBalcony(4), new EmptyBonus());
		councillor = Councillor.createCouncillor(new PoliticColor("nero"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithRegionNull() {
		new ElectAsMainActionCommand(null, councillor.getState());
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithCouncillorNull() {
		new ElectAsMainActionCommand(region.getState(), null);
	}
	
	@Test
	public void testGetters() {
		ElectAsMainActionCommand command = new ElectAsMainActionCommand(region.getState(), councillor.getState());
		RegionDTO region = Region.createRegion("Lombardia", new ArrayList<>(), new BusinessPermissionTileDeck(new ArrayList<>()), Balcony.createBalcony(4), new EmptyBonus()).getState();
		CouncillorDTO councillor = Councillor.createCouncillor(new PoliticColor("nero")).getState();
		
		assertEquals(region, command.getRegion());
		assertEquals(councillor, command.getCouncillor());
	}

}
