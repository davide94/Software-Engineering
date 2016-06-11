package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

/**
 *
 */
public class PingRequest implements Event {
    @Override
    public void apply(ClientModel model) throws InvalidRegionException, InvalidCityException, PlayerNotFoundException {

    }
}
