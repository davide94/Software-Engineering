package it.polimi.ingsw.cg26.client.model.state;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *The public interface of the states
 */
public interface State {

    Map<String, String> commands();

    default boolean isYourTurn() {
        return false;
    }

    default boolean isYourTurnToBuy() {
        return false;
    }

    default boolean isYourTurnToSell() {
        return false;
    }

    default boolean isMatchEnded() {
        return false;
    }

    default Optional<List<BusinessPermissionTileDTO>> getPendingBPTBonusRequest() {
        return Optional.empty();
    }

    default Optional<List<CityDTO>> getPendingCityBonusRequest() {
        return Optional.empty();
    }

    default Optional<List<BusinessPermissionTileDTO>> getPendingPlayerBonusRequest() {
        return Optional.empty();
    }
}
