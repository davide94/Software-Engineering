package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.FullStateChange;
import it.polimi.ingsw.cg26.common.update.change.PlayersChange;
import it.polimi.ingsw.cg26.common.update.event.PlayerLeft;
import it.polimi.ingsw.cg26.server.exceptions.*;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.view.View;

/**
 *
 */
public class Staccah extends Action {

    private final View view;

    /**
     * Build a simple action
     *
     * @param token
     */
    public Staccah(long token, View view) {
        super(token);
        this.view = view;
    }

    @Override
    public void apply(GameBoard gameBoard) throws NoRemainingAssistantsException, NoRemainingActionsException, InvalidCardsException, CouncillorNotFoundException, NotEnoughMoneyException, CityNotFoundException, ExistingEmporiumException, SellableNotFoundException, NoRemainingCardsException, InvalidTileException, InvalidCityException {
        gameBoard.getScheduler().deactivatePlayer(getToken());
        gameBoard.deregisterObserver(view);
        notifyChange(gameBoard);
    }

    @Override
    public void notifyChange(GameBoard gameBoard) {
        PlayerDTO playerState = null;
        for (PlayerDTO p: gameBoard.getState().getPlayers())
            if (p.getToken() == getToken())
                playerState = p;
        if (playerState != null)
            gameBoard.notifyObservers(new PlayersChange(new BasicChange(), playerState));
        else
            gameBoard.notifyObservers(new FullStateChange(new BasicChange(), gameBoard.getState()));
        gameBoard.notifyObservers(new PlayerLeft());
    }
}
