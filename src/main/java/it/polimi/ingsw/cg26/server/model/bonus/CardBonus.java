package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.state.BonusState;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 * 
 */
public class CardBonus extends Bonus {

    private PoliticDeck politicDeck;

    /**
     * Create a CardBonus
     * @param multiplicity of the bonus
     */
    public CardBonus(int multiplicity, PoliticDeck politicDeck) {
    	super(multiplicity);
        if (politicDeck == null)
            throw new NullPointerException();
        this.politicDeck = politicDeck;
    }

    /**
     * the player draws a number of cards equal to the multiplicity
     * @param player the player to apply the bonus
     */
    @Override
    public void apply(Player player) {
        for(int i=0; i<this.getMultiplicity(); i++) {
            player.addPoliticCard(this.politicDeck.draw());
        }
    }

    @Override
    public BonusState getState() {
        return new BonusState("Cards", getMultiplicity());
    }

    @Override
    public String toString() {
        return "CardBonus{" +
                "multiplicity=" + super.getMultiplicity() +
                "}";
    }

}