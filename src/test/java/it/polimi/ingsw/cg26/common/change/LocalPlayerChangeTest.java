package it.polimi.ingsw.cg26.common.change;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
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

public class LocalPlayerChangeTest {

	private GameBoardDTO gameBoardDTO;
	
	private Change change;
	
	private PlayerDTO changePlayer;
	
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
		PlayerDTO currentPlayer = new PlayerDTO("Marco", 1, 2, 5, 1, 1, 2, 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		
		gameBoardDTO = new GameBoardDTO(players, currentPlayer, new PoliticDeckDTO(), pool, kingB, regions, track, king, market, kDeck);
	
		changePlayer = new PlayerDTO("Luca", 2, 12, 5, 0, 0, 8, 3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithChangeNullShouldThrowException(){
		new LocalPlayerChange(null, changePlayer);
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithPlayerNullShoulThrowException(){
		new LocalPlayerChange(change, null);
	}
	
	@Test
	public void applyChange(){
		Change change = new LocalPlayerChange(this.change, changePlayer);
		change.apply(gameBoardDTO);
		
		assertEquals(changePlayer, gameBoardDTO.getLocalPlayer());
	}

}
