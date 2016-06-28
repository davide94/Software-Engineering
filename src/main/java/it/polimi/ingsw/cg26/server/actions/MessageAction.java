package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.common.update.event.MessageUpdate;
import it.polimi.ingsw.cg26.server.exceptions.*;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;

/**
 *
 */
public class MessageAction extends Action {

    private String sender;

    private String body;

    public MessageAction(String sender, String body, long token) {
        super(token);
        this.sender = sender;
        this.body = body;
    }

    @Override
    public void apply(GameBoard gameBoard) throws NoRemainingAssistantsException, NoRemainingActionsException, InvalidCardsException, CouncillorNotFoundException, NotEnoughMoneyException, CityNotFoundException, ExistingEmporiumException, SellableNotFoundException, NoRemainingCardsException, InvalidTileException, InvalidCityException {
        gameBoard.notifyObservers(new MessageUpdate(sender, body));
    }

    @Override
    public void notifyChange(GameBoard gameBoard) {

    }
}
