package it.polimi.ingsw.cg26.server.model.bonus;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class CardBonusTest {

	PoliticDeck politicDeck;
	
	@Before
	public void setUp(){
		List<PoliticCard> cards = new LinkedList<>();
		
		politicDeck = new PoliticDeck(cards);
		
		politicDeck.add(new PoliticCard(new PoliticColor("arancione")));
		politicDeck.add(new PoliticCard(new PoliticColor("nero")));
		politicDeck.add(new PoliticCard(new PoliticColor("bianco")));
		politicDeck.add(new PoliticCard(new PoliticColor("azzurro")));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithNegativeMultiplicity() {
		new CardBonus(-1, politicDeck);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithZeroMultiplicity() {
		new CardBonus(0, politicDeck);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithDeckNull() {
		new CardBonus(5, null);
	}
	
	@Test
	public void testApplyTheBonusWithMultiplicity1ShouldGiveTheFirstCardOfTheDeckToThePlayer(){
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new ArrayList<>());
		List<PoliticCard> playerCards = new LinkedList<>();
		Player player = new Player(1, "Marco", cell, 2, playerCards, new LinkedList<Assistant>());
		CardBonus bonus = new CardBonus(1, politicDeck);
		bonus.apply(player);
		
		assertEquals(politicDeck.draw().getColor(), new PoliticColor("nero"));
		
		PoliticCard card = player.takeCard(new PoliticCardDTO(new PoliticColorDTO("arancione"), 1, "Marco"));
		assertEquals(new PoliticColor("arancione") ,card.getColor());
	}
	
	@Test
	public void testGetState(){
		CardBonus bonus = new CardBonus(3, politicDeck);
		BonusDTO bonusDTO = bonus.getState();
		
		assertEquals("Cards", bonusDTO.getKind());
		assertEquals(3, bonusDTO.getMultiplicity());
	}
	
	@Test
	public void testToString(){
		CardBonus bonus = new CardBonus(2, politicDeck);
		
		assertEquals("CardBonus{multiplicity=2}", bonus.toString());
	}

}
