package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.state.CityState;

/**
 *
 */
public class BuildCommand extends Command {

    private static final long serialVersionUID = -7169935530571666693L;

    private final CityState city;

    public BuildCommand(CityState city) {
        this.city = city;
    }

    public CityState getCity() {
        return city;
    }

}
