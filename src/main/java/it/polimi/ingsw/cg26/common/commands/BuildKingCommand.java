package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

import java.util.List;

/**
 *
 */
public class BuildKingCommand implements Command {

    private static final long serialVersionUID = 8976504149589335869L;

    /**
     * The city in which the player wants to build
     */
    private final CityDTO city;

    /**
     * The tile the player wants to use
     */
    private final List<PoliticCardDTO> cards;

    /**
     * Construct a Command to build an emporium with the help of the king
     * @param city the city to use in the command
     * @param cards is a list of PoliticCardDTO to use in the command
     * @throws NullPointerException if one or more arguments are null
     */
    public BuildKingCommand(CityDTO city, List<PoliticCardDTO> cards) {
    	if(city == null || cards == null)
    		throw new NullPointerException();
        this.city = city;
        this.cards = cards;
    }

    /**
     * Get CityDTO
     * @return the city of the command
     */
    public CityDTO getCity() {
        return city;
    }

    /**
     * Get TileDTO
     * @return the tile of the command
     */
    public List<PoliticCardDTO> getCards() {
        return cards;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
