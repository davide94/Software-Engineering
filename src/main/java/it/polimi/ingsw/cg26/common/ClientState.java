package it.polimi.ingsw.cg26.common;

/**
 *
 */
public interface ClientState {

    void gameStarted();

    void gameEnded();

    void turnStarted();

    void turnEnded();

    void marketStarted();

    void marketEnded();

    void sellTurnStarted();

    void sellTurnEnded();

    void buyTurnStarted();

    void buyTurnEnded();

    void actionSuccessful();

    void actionFailed();
}
