package it.polimi.ingsw.cg26.common.change;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.update.change.BalconyChange;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.Change;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDeckDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.KingDTO;
import it.polimi.ingsw.cg26.common.dto.KingDeckDTO;
import it.polimi.ingsw.cg26.common.dto.MarketDTO;
import it.polimi.ingsw.cg26.common.dto.NobilityCellDTO;
import it.polimi.ingsw.cg26.common.dto.NobilityTrackDTO;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticDeckDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.dto.RewardTileDTO;
import it.polimi.ingsw.cg26.common.dto.SellableDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;

public class BalconyChangeTest {

	private ClientModel model;
	
	private RegionDTO chosenRegion;
	
	private Change change;
	
	private BalconyDTO chosenBalcony;
	
	@Before
	public void setUp(){
		change = new BasicChange();
		
		List<CouncillorDTO> councillors = new ArrayList<>();
		councillors.add(new CouncillorDTO(new PoliticColorDTO("rosso")));
		councillors.add(new CouncillorDTO(new PoliticColorDTO("giallo")));
		councillors.add(new CouncillorDTO(new PoliticColorDTO("nero")));
		councillors.add(new CouncillorDTO(new PoliticColorDTO("bianco")));
		BalconyDTO balconyDTO =  new BalconyDTO(councillors);
		
		chosenRegion = new RegionDTO("Lombardia", new ArrayList<CityDTO>(), new BusinessPermissionTileDeckDTO(new ArrayList<BusinessPermissionTileDTO>()), balconyDTO, new EmptyBonusDTO());
		
		List<CouncillorDTO> councillors2 = new ArrayList<>();
		councillors2.add(new CouncillorDTO(new PoliticColorDTO("verde")));
		councillors2.add(new CouncillorDTO(new PoliticColorDTO("giallo")));
		councillors2.add(new CouncillorDTO(new PoliticColorDTO("nero")));
		councillors2.add(new CouncillorDTO(new PoliticColorDTO("bianco")));
		BalconyDTO balconyDTO2 =  new BalconyDTO(councillors2);
		
		RegionDTO region = new RegionDTO("Veneto", new ArrayList<CityDTO>(), new BusinessPermissionTileDeckDTO(new ArrayList<BusinessPermissionTileDTO>()), balconyDTO2, new EmptyBonusDTO());
	
		List<RegionDTO> regions = new ArrayList<>();
		regions.add(region);
		regions.add(chosenRegion);
		List<PlayerDTO> players = new ArrayList<>();
		List<CouncillorDTO> pool = new ArrayList<>();
		BalconyDTO kingB = new BalconyDTO(new ArrayList<>());
		NobilityTrackDTO track = new NobilityTrackDTO(new ArrayList<NobilityCellDTO>());
		KingDTO king = new KingDTO("Milano");
		MarketDTO market = new MarketDTO(new ArrayList<SellableDTO>());
		KingDeckDTO kDeck = new KingDeckDTO(new ArrayList<RewardTileDTO>());
		PlayerDTO currentPlayer = new PlayerDTO("Marco", 1, false, 2, 5, 1, 1, 2, 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

		model = new Model();
		model.setPlayers(players);
		model.setLocalPlayer(currentPlayer);
		model.setPoliticDeck(new PoliticDeckDTO());
		model.setCouncillorsPool(pool);
		model.setKingBalcony(kingB);
		model.setRegions(regions);
		model.setNobilityTrack(track);
		model.setKing(king);
		model.setMarket(market);
		model.setKingDeck(kDeck);

		List<CouncillorDTO> councillors3 = new ArrayList<>();
		councillors3.add(new CouncillorDTO(new PoliticColorDTO("verde")));
		councillors3.add(new CouncillorDTO(new PoliticColorDTO("blu")));
		councillors3.add(new CouncillorDTO(new PoliticColorDTO("bianco")));
		councillors3.add(new CouncillorDTO(new PoliticColorDTO("bianco")));
		chosenBalcony =  new BalconyDTO(councillors3);
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithChangeNullShouldThrowException(){
		new BalconyChange(null, new BalconyDTO(new ArrayList<>()), chosenRegion);
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithBalconyNullShouldThrowException(){
		new BalconyChange(change, null, chosenRegion);
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithRegionNullShouldThrowException(){
		new BalconyChange(change, new BalconyDTO(new ArrayList<>()), null);
	}
	
	@Test (expected = InvalidRegionException.class)
	public void testApplyChangeToANotExistingregionShouldThrowException() throws Exception {
		RegionDTO region = new RegionDTO("Molise", new ArrayList<>(), new BusinessPermissionTileDeckDTO(new ArrayList<BusinessPermissionTileDTO>()), new BalconyDTO(new ArrayList<>()), new EmptyBonusDTO());
		Change change = new BalconyChange(this.change, chosenBalcony, region);
	
		change.apply(model);
	}
	
	@Test
	public void testApplyChange() throws Exception {
		Change change = new BalconyChange(this.change, chosenBalcony, chosenRegion);
		
		assertTrue(chosenRegion.getBalcony().getCouncillors().contains(new CouncillorDTO(new PoliticColorDTO("rosso"))));
		assertFalse(chosenRegion.getBalcony().getCouncillors().contains(new CouncillorDTO(new PoliticColorDTO("verde"))));
		
		change.apply(model);
		
		assertEquals(chosenBalcony, chosenRegion.getBalcony());
	}

}
