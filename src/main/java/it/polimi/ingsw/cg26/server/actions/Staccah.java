package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.server.exceptions.*;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;

/**
 *
 */
public class Staccah extends Action {
    /**
     * Build a simple action
     *
     * @param token
     */
    public Staccah(long token) {
        super(token);
    }

    @Override
    public void apply(GameBoard gameBoard) throws NoRemainingAssistantsException, NoRemainingActionsException, InvalidCardsException, CouncillorNotFoundException, NotEnoughMoneyException, CityNotFoundException, ExistingEmporiumException, SellableNotFoundException, NoRemainingCardsException, InvalidTileException, InvalidCityException {

    }

    @Override
    public void notifyChange(GameBoard gameBoard) {

    }
}
