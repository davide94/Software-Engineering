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
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticDeckDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.dto.RewardTileDTO;
import it.polimi.ingsw.cg26.common.dto.SellableDTO;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.Change;
import it.polimi.ingsw.cg26.common.update.change.KingBalconyChange;

public class KingBalconyChangeTest {

	private Model model;
	
	private BalconyDTO changedBalcony;
	
	@Before
	public void setUp() {
		List<CouncillorDTO> councillors = new ArrayList<>();
		councillors.add(new CouncillorDTO(new PoliticColorDTO("verde")));
		councillors.add(new CouncillorDTO(new PoliticColorDTO("rosso")));
		councillors.add(new CouncillorDTO(new PoliticColorDTO("viola")));
		councillors.add(new CouncillorDTO(new PoliticColorDTO("azzurro")));
		
		List<RegionDTO> regions = new ArrayList<>();
		List<PlayerDTO> players = new ArrayList<>();
		List<CouncillorDTO> pool = new ArrayList<>();
		BalconyDTO kingB = new BalconyDTO(councillors);
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
		
		List<CouncillorDTO> changedCouncillors = new ArrayList<>();
		changedCouncillors.add(new CouncillorDTO(new PoliticColorDTO("rosa")));
		changedCouncillors.add(new CouncillorDTO(new PoliticColorDTO("nero")));
		changedCouncillors.add(new CouncillorDTO(new PoliticColorDTO("viola")));
		changedCouncillors.add(new CouncillorDTO(new PoliticColorDTO("azzurro")));
		changedBalcony = new BalconyDTO(changedCouncillors);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithBalconyNull() {
		new KingBalconyChange(new BasicChange(), null);
	}
	
	@Test
	public void testApply() throws Exception {
		Change change = new KingBalconyChange(new BasicChange(), changedBalcony);
		change.apply(model);
		
		assertEquals(4, model.getKingBalcony().getCouncillors().size());
		assertTrue(model.getKingBalcony().getCouncillors().contains(new CouncillorDTO(new PoliticColorDTO("rosa"))));
		assertTrue(model.getKingBalcony().getCouncillors().contains(new CouncillorDTO(new PoliticColorDTO("viola"))));
		assertTrue(model.getKingBalcony().getCouncillors().contains(new CouncillorDTO(new PoliticColorDTO("nero"))));
		assertTrue(model.getKingBalcony().getCouncillors().contains(new CouncillorDTO(new PoliticColorDTO("azzurro"))));
		assertFalse(model.getKingBalcony().getCouncillors().contains(new CouncillorDTO(new PoliticColorDTO("verde"))));
		assertFalse(model.getKingBalcony().getCouncillors().contains(new CouncillorDTO(new PoliticColorDTO("rosso"))));
	}
}
