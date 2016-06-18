package it.polimi.ingsw.cg26.common.change;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
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
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.Change;
import it.polimi.ingsw.cg26.common.update.change.CurrentPlayerChange;

public class CurrentPlayerChangeTest {

	private Model model;
	
	private PlayerDTO newCurrentPlayer;
	
	@Before
	public void setUp() {
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

		newCurrentPlayer = new PlayerDTO("Davide", 2, true, 4, 7, 1, 1, 0, 2, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithPlayerNull() {
		new CurrentPlayerChange(new BasicChange(), null);
	}
	
	@Test
	public void test() throws Exception {
		Change change = new CurrentPlayerChange(new BasicChange(), this.newCurrentPlayer);
		change.apply(model);
		PlayerDTO player = new PlayerDTO("Davide", 2, true, 4, 7, 1, 1, 0, 2, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		
		assertEquals(player.getName(), model.getCurrentPlayer().getName());
		assertEquals(player.getAssistantsNumber(), model.getCurrentPlayer().getAssistantsNumber());
		assertEquals(player.getCoins(), model.getCurrentPlayer().getCoins());
		assertEquals(player.getVictoryPoints(), model.getCurrentPlayer().getVictoryPoints());
		assertEquals(player.getNobilityCell(), model.getCurrentPlayer().getNobilityCell());
		assertEquals(player.getToken(), model.getCurrentPlayer().getToken());
	}
}
