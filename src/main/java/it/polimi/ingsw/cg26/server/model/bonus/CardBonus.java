package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.CardBonusDTO;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.List;

/**
 * 
 */
public class CardBonus extends BonusDecorator {

	/**
	 * The politic Card Deck from which the player will draw
	 */
    private PoliticDeck politicDeck;

    /**
     * Create a CardBonus
     * @param multiplicity of the bonus
     * @throws IllegalArgumentException if the multiplicity is less than 1
     * @throws NullPointerException if the deck is null
     */
    public CardBonus(Bonus decoratedBonus, int multiplicity, PoliticDeck politicDeck) {
    	super(decoratedBonus, multiplicity);
        if (politicDeck == null)
            throw new NullPointerException();
        this.politicDeck = politicDeck;
    }

    /**
     * the player draws a number of cards equal to the multiplicity
     * @param player the player to apply the bonus
     */
    @Override
    public void apply(Player player) throws NoRemainingCardsException {
    	super.apply(player);
        for(int i=0; i<this.getMultiplicity(); i++) {
            player.addPoliticCard(this.politicDeck.draw());
        }
    }
    
    @Override
    public List<String> getBonusNames(){
    	List<String> bonuses = super.getBonusNames();
    	bonuses.add("Card");
    	return bonuses;
    }

    @Override
    public BonusDTO getState() {
        return new CardBonusDTO(super.getState(), getMultiplicity());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) 
        	return true;
        if (o == null || getClass() != o.getClass()) 
        	return false;
        if (!super.equals(o)) 
        	return false;

        CardBonus cardBonus = (CardBonus) o;

        return politicDeck != null ? politicDeck.equals(cardBonus.politicDeck) : cardBonus.politicDeck == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (politicDeck != null ? politicDeck.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString()+"\nCardBonus{" +
                "multiplicity=" + super.getMultiplicity() +
                "}";
    }
}