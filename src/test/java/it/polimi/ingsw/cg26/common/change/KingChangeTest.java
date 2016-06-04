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

public class KingChangeTest {

	private GameBoardDTO gameBoardDTO;
	
	private Change change;
	
	private KingDTO changeKing;
	
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
		
		changeKing = new KingDTO("Roma");
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithChangeNullShouldThrowException(){
		new KingChange(null, changeKing);
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithKingNullShoulThrowException(){
		new KingChange(change, null);
	}
	
	@Test
	public void testApplyChange(){
		Change change =  new KingChange(this.change, changeKing);
		change.apply(gameBoardDTO);
		
		assertEquals(changeKing, gameBoardDTO.getKing());
		assertEquals("Roma", gameBoardDTO.getKing().getCurrentCity());
	}

}
