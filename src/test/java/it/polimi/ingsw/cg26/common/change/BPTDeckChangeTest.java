package it.polimi.ingsw.cg26.common.change;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.update.change.BPTDeckChange;
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
import it.polimi.ingsw.cg26.common.dto.PoliticDeckDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.dto.RewardTileDTO;
import it.polimi.ingsw.cg26.common.dto.SellableDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.CoinBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;

public class BPTDeckChangeTest {

	private Model model;
	
	private RegionDTO chosenRegion;
	
	private Change change;
	
	private BusinessPermissionTileDeckDTO chosenDeck;
	
	@Before
	public void setUp(){
		change = new BasicChange();
		
		List<String> cities1 = new ArrayList<>();
		cities1.add("Milano");
		cities1.add("Torino");
		List<String> cities2 = new ArrayList<>();
		cities2.add("Milano");
		cities2.add("Venezia");
		
		List<BusinessPermissionTileDTO> tiles = new ArrayList<>();
		tiles.add(new BusinessPermissionTileDTO(cities1, new CoinBonusDTO(new EmptyBonusDTO(), 3), 0, "none"));
		tiles.add(new BusinessPermissionTileDTO(cities2, new CoinBonusDTO(new EmptyBonusDTO(), 5), 0, "none"));
		BusinessPermissionTileDeckDTO bPTDeck = new BusinessPermissionTileDeckDTO(tiles);
		
		BalconyDTO balconyDTO =  new BalconyDTO(new ArrayList<>());
		chosenRegion = new RegionDTO("Lombardia", new ArrayList<CityDTO>(), bPTDeck, balconyDTO, new EmptyBonusDTO());
		
		BalconyDTO balconyDTO2 =  new BalconyDTO(new ArrayList<>());
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
		PlayerDTO currentPlayer = new PlayerDTO("Marco", 1, 2, 5, 1, 1, 2, 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

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

		List<String> cities3 = new ArrayList<>();
		cities3.add("Como");
		cities3.add("Varese");
		List<BusinessPermissionTileDTO> chosenTiles = new ArrayList<>();
		tiles.add(new BusinessPermissionTileDTO(cities1, new CoinBonusDTO(new EmptyBonusDTO(), 3), 0, "none"));
		tiles.add(new BusinessPermissionTileDTO(cities3, new CoinBonusDTO(new EmptyBonusDTO(), 5), 0, "none"));
		chosenDeck = new BusinessPermissionTileDeckDTO(chosenTiles);
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithChangeNullShouldThrowException(){
		new BPTDeckChange(null, chosenDeck, chosenRegion);
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithDeckNullShouldThrowException(){
		new BPTDeckChange(change, null, chosenRegion);
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithRegionNullShouldThrowException(){
		new BPTDeckChange(change, chosenDeck, null);
	}

	@Test (expected = InvalidRegionException.class)
	public void testApplyChangeToANotExistingregionShouldThrowException(){
		RegionDTO region = new RegionDTO("Molise", new ArrayList<>(), new BusinessPermissionTileDeckDTO(new ArrayList<BusinessPermissionTileDTO>()), new BalconyDTO(new ArrayList<>()), new EmptyBonusDTO());
		Change change = new BPTDeckChange(this.change, chosenDeck, region);
	
		change.apply(model);
	}
	
	@Test
	public void testApplyChange(){
		Change change = new BPTDeckChange(this.change, chosenDeck, chosenRegion);
		
		change.apply(model);
		
		assertEquals(chosenDeck, chosenRegion.getDeck());
	}
}
