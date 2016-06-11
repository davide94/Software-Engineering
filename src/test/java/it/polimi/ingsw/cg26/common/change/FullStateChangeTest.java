package it.polimi.ingsw.cg26.common.change;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.Change;
import it.polimi.ingsw.cg26.common.update.change.FullStateChange;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDeckDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
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

public class FullStateChangeTest {

	private Model model;
	
	private Change change;
	
	private BalconyDTO changeKingBalcony;
	
	private List<RegionDTO> changeRegions;
	
	private NobilityTrackDTO changeTrack;
	
	private KingDTO changeKing;
	
	private List<CouncillorDTO> changePool;
	
	private MarketDTO changeMarket;
	
	private PlayerDTO changeCurrentPlayer;
	
	private KingDeckDTO changeKingDeck;
	
	private List<PlayerDTO> changePlayers;
	
	private PoliticDeckDTO changeDeck;
	
	@Before
	public void setUp(){
		change = new BasicChange();
		
		List<RegionDTO> regions = new ArrayList<>();
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

		changePool = new ArrayList<>();
		changePool.add(new CouncillorDTO(new PoliticColorDTO("arancione")));
		changePool.add(new CouncillorDTO(new PoliticColorDTO("verde")));
		changePool.add(new CouncillorDTO(new PoliticColorDTO("rosso")));
		changePool.add(new CouncillorDTO(new PoliticColorDTO("blu")));
		changePool.add(new CouncillorDTO(new PoliticColorDTO("bianco")));
		
		RegionDTO region1 = new RegionDTO("Veneto", new ArrayList<CityDTO>(), new BusinessPermissionTileDeckDTO(new ArrayList<BusinessPermissionTileDTO>()), new BalconyDTO(new ArrayList<>()), new EmptyBonusDTO());
		RegionDTO region2 = new RegionDTO("Lombardia", new ArrayList<CityDTO>(), new BusinessPermissionTileDeckDTO(new ArrayList<BusinessPermissionTileDTO>()), new BalconyDTO(new ArrayList<>()), new EmptyBonusDTO());
		changeRegions = new ArrayList<>();
		changeRegions.add(region1);
		changeRegions.add(region2);
		
		changePlayers = new ArrayList<>();
		
		List<CouncillorDTO> kingCouncillors = new ArrayList<>();
		kingCouncillors.add(new CouncillorDTO(new PoliticColorDTO("verde")));
		kingCouncillors.add(new CouncillorDTO(new PoliticColorDTO("viola")));
		kingCouncillors.add(new CouncillorDTO(new PoliticColorDTO("bianco")));
		kingCouncillors.add(new CouncillorDTO(new PoliticColorDTO("nero")));
		changeKingBalcony = new BalconyDTO(kingCouncillors);
		
		changeDeck = new PoliticDeckDTO();
		
		changeTrack = new NobilityTrackDTO(new ArrayList<NobilityCellDTO>());
		
		changeKing = new KingDTO("Roma");
	
		changeMarket = new MarketDTO(new ArrayList<SellableDTO>());
		
		changeKingDeck = new KingDeckDTO(new ArrayList<RewardTileDTO>());
		
		changeCurrentPlayer = new PlayerDTO("Luca", 1, false, 2, 3, 1, 1, 5, 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithChangeNullShouldThrowException(){
		GameBoardDTO changeGameBoard = new GameBoardDTO(changePlayers, changeCurrentPlayer, changeDeck , changePool, changeKingBalcony, changeRegions, changeTrack, changeKing, changeMarket, changeKingDeck);
		new FullStateChange(null, changeGameBoard);
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithGameBoardNullShoulThrowException(){
		new FullStateChange(this.change, null);
	}
	
	@Test
	public void testApplyChange() throws Exception {
		GameBoardDTO changeGameBoard = new GameBoardDTO(changePlayers, changeCurrentPlayer, changeDeck , changePool, changeKingBalcony, changeRegions, changeTrack, changeKing, changeMarket, changeKingDeck);
		Change change =  new FullStateChange(this.change, changeGameBoard);
		change.apply(model);
		
		assertEquals(changePlayers, model.getPlayers());
		assertEquals(changeCurrentPlayer, model.getCurrentPlayer());
		assertEquals(changeDeck, model.getPoliticDeck());
		assertEquals(changePool, model.getCouncillorsPool());
		assertEquals(changeKingBalcony, model.getKingBalcony());
		assertEquals(changeRegions, model.getRegions());
		assertEquals(changeTrack, model.getNobilityTrack());
		assertEquals(changeKing, model.getKing());
		assertEquals(changeMarket, model.getMarket());
		assertEquals(changeKingDeck, model.getKingDeck());
	}
	
	@Test
	public void testToString(){
		GameBoardDTO changeGameBoard = new GameBoardDTO(changePlayers, changeCurrentPlayer, changeDeck , changePool, changeKingBalcony, changeRegions, changeTrack, changeKing, changeMarket, changeKingDeck);
		Change change =  new FullStateChange(this.change, changeGameBoard);
		
		assertEquals("FullStateChange{dto=" + changeGameBoard +'}', change.toString());
	}
	
	@Test
	public void testGetState(){
		GameBoardDTO changeGameBoard = new GameBoardDTO(changePlayers, changeCurrentPlayer, changeDeck , changePool, changeKingBalcony, changeRegions, changeTrack, changeKing, changeMarket, changeKingDeck);
		FullStateChange change =  new FullStateChange(this.change, changeGameBoard);
		
		assertEquals(changeGameBoard, change.getState());
	}

}
