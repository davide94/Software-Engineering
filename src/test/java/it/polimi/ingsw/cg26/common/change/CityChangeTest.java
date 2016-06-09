package it.polimi.ingsw.cg26.common.change;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.Change;
import it.polimi.ingsw.cg26.common.update.change.CityChange;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDeckDTO;
import it.polimi.ingsw.cg26.common.dto.CityColorDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.EmporiumDTO;
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
import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;

public class CityChangeTest {

	private Model model;
	
	private RegionDTO chosenRegion;
	
	private Change change;
	
	private CityDTO chosenCity;
	
	private CityDTO changedCity;
	
	@Before
	public void setUp(){
		change = new BasicChange();
		
		changedCity = new CityDTO("Milano", new CityColorDTO("grigio"), new EmptyBonusDTO(), new ArrayList<>(), new ArrayList<>());
		CityDTO city2 = new CityDTO("Torino", new CityColorDTO("nero"), new EmptyBonusDTO(), new ArrayList<>(), new ArrayList<>());
		List<CityDTO> cities = new ArrayList<>();
		cities.add(changedCity);
		cities.add(city2);
		
		BalconyDTO balconyDTO =  new BalconyDTO(new ArrayList<>());
		chosenRegion = new RegionDTO("Lombardia", cities, new BusinessPermissionTileDeckDTO(new ArrayList<BusinessPermissionTileDTO>()), balconyDTO, new EmptyBonusDTO());
		
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

		List<EmporiumDTO> emporiums = new ArrayList<>();
		emporiums.add(new EmporiumDTO("Marco"));
		emporiums.add(new EmporiumDTO("Davide"));
		chosenCity = new CityDTO("Milano", new CityColorDTO("grigio"), new EmptyBonusDTO(), emporiums, new ArrayList<>());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithChangeNullShouldThrowException(){
		new CityChange(null, chosenCity);
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithCityNullShouldThrowException(){
		new CityChange(change, null);
	}
	
	@Test (expected = InvalidCityException.class)
	public void testApplyChangeWithNotExistingCityShouldThrowException() throws Throwable {
		CityDTO neCity = new CityDTO("Gotham", new CityColorDTO("nero"), new EmptyBonusDTO(), new ArrayList<>(), new ArrayList<>());
		Change change =  new CityChange(this.change, neCity);
		
		change.apply(model);
	}
	
	@Test
	public void testApplyChange() throws Throwable {
		Change change =  new CityChange(this.change, chosenCity);
		change.apply(model);
		
		assertEquals(2, changedCity.getEmporiums().size());
	}

}
