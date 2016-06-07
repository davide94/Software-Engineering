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
import it.polimi.ingsw.cg26.server.exceptions.NotValidPlayerException;

public class PlayersChangeTest {

private GameBoardDTO gameBoardDTO;
	
	private Change change;
	
	private PlayerDTO changePlayer;
	
	@Before
	public void setUp(){
		change = new BasicChange();
		
		List<RegionDTO> regions = new ArrayList<>();
		List<PlayerDTO> players = new ArrayList<>();
		players.add(new PlayerDTO("Marco", 1, 2, 5, 1, 1, 2, 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
		players.add(new PlayerDTO("Davide", 2, 5, 8, 0, 0, 2, 2, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
		players.add(new PlayerDTO("Luca", 3, 13, 6, 0, 0, 4, 3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
		List<CouncillorDTO> pool = new ArrayList<>();
		BalconyDTO kingB = new BalconyDTO(new ArrayList<>());
		NobilityTrackDTO track = new NobilityTrackDTO(new ArrayList<NobilityCellDTO>());
		KingDTO king = new KingDTO("Milano");
		MarketDTO market = new MarketDTO(new ArrayList<SellableDTO>());
		KingDeckDTO kDeck = new KingDeckDTO(new ArrayList<RewardTileDTO>());
		PlayerDTO currentPlayer = new PlayerDTO("Marco", 1, 2, 5, 1, 1, 2, 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		
		gameBoardDTO = new GameBoardDTO(players, currentPlayer, new PoliticDeckDTO(), pool, kingB, regions, track, king, market, kDeck);
		
		changePlayer = new PlayerDTO("Marco", 1, 6, 8, 0, 1, 3, 2, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithChangeNullShouldThrowException(){
		new PlayersChange(null, changePlayer);
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildChangeWithPlayerNullShoulThrowException(){
		new PlayersChange(change, null);
	}
	
	@Test (expected = NotValidPlayerException.class)
	public void testApplyChangeWithNotExistingPlayerShouldThrowException(){
		PlayerDTO nePlayer = new PlayerDTO("Ajeje", 5, 6, 9, 0, 0, 3, 2, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		Change change = new PlayersChange(this.change, nePlayer);
		
		change.apply(gameBoardDTO);
	}

	@Test
	public void testApply(){
		Change change = new PlayersChange(this.change, changePlayer);
		change.apply(gameBoardDTO);
		PlayerDTO changedPlayer = null;
		for(PlayerDTO p : gameBoardDTO.getPlayers()){
			if(p.getName().equals(changePlayer.getName())){
				changedPlayer = p;
				break;
			}
		}
		
		assertEquals(changePlayer, changedPlayer);
	}
}