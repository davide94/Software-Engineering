package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

import java.util.List;

/**
 *
 */
public class BuildKingCommand extends Command {

    private static final long serialVersionUID = 8976504149589335869L;

    private final CityDTO city;

    private final List<PoliticCardDTO> cards;

    public BuildKingCommand(CityDTO city, List<PoliticCardDTO> cards) {
    	if(city == null || cards == null)
    		throw new NullPointerException();
        this.city = city;
        this.cards = cards;
    }

    public CityDTO getCity() {
        return city;
    }

    public List<PoliticCardDTO> getCards() {
        return cards;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
