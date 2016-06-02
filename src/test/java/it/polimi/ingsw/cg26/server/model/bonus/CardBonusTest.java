package it.polimi.ingsw.cg26.server.model.bonus;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class CardBonusTest {

	PoliticDeck politicDeck;
	
	private Bonus bonus;
	
	@Before
	public void setUp(){
		this.bonus = new EmptyBonus();
		
		List<PoliticCard> cards = new LinkedList<>();
		
		politicDeck = new PoliticDeck(cards);
		
		politicDeck.add(new PoliticCard(new PoliticColor("arancione")));
		politicDeck.add(new PoliticCard(new PoliticColor("nero")));
		politicDeck.add(new PoliticCard(new PoliticColor("bianco")));
		politicDeck.add(new PoliticCard(new PoliticColor("azzurro")));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithNegativeMultiplicity() {
		new CardBonus(bonus, -1, politicDeck);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithZeroMultiplicity() {
		new CardBonus(bonus, 0, politicDeck);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithDeckNull() {
		new CardBonus(bonus, 5, null);
	}
	
	@Test
	public void testApplyTheBonusWithMultiplicity1ShouldGiveTheFirstCardOfTheDeckToThePlayer(){
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new EmptyBonus());
		List<PoliticCard> playerCards = new LinkedList<>();
		Player player = new Player(1, "Marco", cell, 2, playerCards, new LinkedList<Assistant>());
		CardBonus cardBonus = new CardBonus(bonus, 1, politicDeck);
		cardBonus.apply(player);
		
		assertEquals(politicDeck.draw().getColor(), new PoliticColor("nero"));
		
		PoliticCard card = player.takeCard(new PoliticCardDTO(new PoliticColorDTO("arancione"), 1, "Marco"));
		assertEquals(new PoliticColor("arancione") ,card.getColor());
	}
	
	@Test
	public void testGetState(){
		CardBonus cardBonus = new CardBonus(bonus, 3, politicDeck);
		BonusDTO bonusDTO = cardBonus.getState();
		
		assertEquals("\nCardBonus{multiplicity=3}", bonusDTO.toString());
	}
	
	@Test
	public void testToString(){
		CardBonus cardBonus = new CardBonus(bonus, 2, politicDeck);
		
		assertEquals("\nCardBonus{multiplicity=2}", cardBonus.toString());
	}

}
