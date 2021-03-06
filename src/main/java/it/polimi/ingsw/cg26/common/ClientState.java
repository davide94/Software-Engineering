package it.polimi.ingsw.cg26.common;

/**
 *
 */
public interface ClientState {

    void regularGameStarted();

    void matchEnded();

    void turnStarted();

    void turnEnded();

    void marketStarted();

    void sellTurnStarted();

    void sellTurnEnded();

    void buyTurnStarted();

    void buyTurnEnded();

    void actionSuccessful();

    void actionFailed(String cause);

    void pendingBPTRequest(int multiplicity);

    void pendingCityBonusRequest(int multiplicity);

    void pendingPlayerRequest(int multiplicity);
}
