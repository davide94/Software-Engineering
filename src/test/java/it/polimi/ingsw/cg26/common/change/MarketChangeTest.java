package it.polimi.ingsw.cg26.common.change;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.AssistantDTO;
import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.KingDTO;
import it.polimi.ingsw.cg26.common.dto.KingDeckDTO;
import it.polimi.ingsw.cg26.common.dto.MarketDTO;
import it.polimi.ingsw.cg26.common.dto.NobilityCellDTO;
import it.polimi.ingsw.cg26.common.dto.NobilityTrackDTO;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticDeckDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.dto.RewardTileDTO;
import it.polimi.ingsw.cg26.common.dto.SellableDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.CoinBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.Change;
import it.polimi.ingsw.cg26.common.update.change.MarketChange;

public class MarketChangeTest {

	private Model model;
	
	private MarketDTO newMarket;
	
	@Before
	public void setUp() {
		PoliticCardDTO card = new PoliticCardDTO(new PoliticColorDTO("rosso"), 5, "Luca");
		List<SellableDTO> onSale = new ArrayList<>();
		onSale.add(card);
		onSale.add(new AssistantDTO(3, "Marco"));
		onSale.add(new BusinessPermissionTileDTO(new ArrayList<>(), new CoinBonusDTO(new EmptyBonusDTO(), 5), 4, "Davide"));
		
		List<RegionDTO> regions = new ArrayList<>();
		List<PlayerDTO> players = new ArrayList<>();
		List<CouncillorDTO> pool = new ArrayList<>();
		BalconyDTO kingB = new BalconyDTO(new ArrayList<>());
		NobilityTrackDTO track = new NobilityTrackDTO(new ArrayList<NobilityCellDTO>());
		KingDTO king = new KingDTO("Milano");
		MarketDTO market = new MarketDTO(onSale);
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
		
		onSale.remove(card);
		onSale.add(new PoliticCardDTO(new PoliticColorDTO("nero"), 2, "Marco"));
		newMarket = new MarketDTO(onSale);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithMarketNull() {
		new MarketChange(new BasicChange(), null);
	}
	
	@Test
	public void testApply() throws Exception {
		Change change = new MarketChange(new BasicChange(), newMarket);
		change.apply(model);
		
		assertTrue(model.getMarket().getOnSale().contains(new PoliticCardDTO(new PoliticColorDTO("nero"), 2, "Marco")));
		assertTrue(model.getMarket().getOnSale().contains(new AssistantDTO(3, "Marco")));
		assertTrue(model.getMarket().getOnSale().contains(new BusinessPermissionTileDTO(new ArrayList<>(), new CoinBonusDTO(new EmptyBonusDTO(), 5), 4, "Davide")));
		assertFalse(model.getMarket().getOnSale().contains(new PoliticCardDTO(new PoliticColorDTO("rosso"), 5, "Luca")));
	}

}
