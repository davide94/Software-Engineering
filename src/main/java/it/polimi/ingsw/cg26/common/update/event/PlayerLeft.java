package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.update.Update;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

/**
 *
 */
public class PlayerLeft implements Update {

	private static final long serialVersionUID = 3086378319015322799L;

	@Override
    public void apply(ClientModel model) throws InvalidRegionException, InvalidCityException, PlayerNotFoundException {
        System.out.println("a Player Left");
    }
}
