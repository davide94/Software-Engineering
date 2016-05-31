package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public class PoliticCard extends Sellable {

    /**
     * The color of the card
     */
    private final PoliticColor color;

    /**
     * Creates a Politic Card
     * @param color is the color of the card
     * @throws NullPointerException if color is null
     */
    public PoliticCard(PoliticColor color) {
        if (color == null)
            throw new NullPointerException();
        this.color = color;
    }

    /**
     * Generates the dto of the card
     * @return the dto of the card
     */
    @Override
    public PoliticCardDTO getState() {
        Player owner = this.getOwner();
        String name = "none";
        if (owner != null)
            name = owner.getName();
        return new PoliticCardDTO(color.getState(), this.getPrice(), name);
    }

    /**
     * Returns the color of the card
     * @return the color of the card
     */
    public PoliticColor getColor() {
        return this.color;
    }


    @Override
    public void backToOwner() {
    	this.getOwner().addPoliticCard(this);
    }
    
    @Override
	public void giveToNewOwner(Player player){
    	player.addPoliticCard(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }
}