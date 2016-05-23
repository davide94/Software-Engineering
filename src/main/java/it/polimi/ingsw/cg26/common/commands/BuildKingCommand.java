package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.state.CityState;
import it.polimi.ingsw.cg26.common.state.PoliticCardState;

import java.util.List;

/**
 *
 */
public class BuildKingCommand extends Command {

    private static final long serialVersionUID = 8976504149589335869L;

    private final CityState city;

    private final List<PoliticCardState> cards;

    public BuildKingCommand(CityState city, List<PoliticCardState> cards) {
        this.city = city;
        this.cards = cards;
    }
}
