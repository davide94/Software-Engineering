package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.update.Update;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

/**
 *
 */
public class MessageUpdate implements Update {

    private static final long serialVersionUID = -6798701414526765734L;

    private String sender;

    private String body;

    public MessageUpdate(String sender, String body) {
        this.sender = sender;
        this.body = body;
    }

    @Override
    public void apply(ClientModel model) throws InvalidRegionException, InvalidCityException, PlayerNotFoundException {
        model.addMessage(sender, body);
    }
}
