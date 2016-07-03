package it.polimi.ingsw.cg26.client.model.state.pendingRequest;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;

import java.util.*;

/**
 *
 */
public class PendingPlayerRequest extends PendingRequest {

    public PendingPlayerRequest(Model model, int multiplicity) {
        super(model, multiplicity);
    }

    @Override
    public Optional<List<BusinessPermissionTileDTO>> getPendingPlayerBonusRequest() {
        List<BusinessPermissionTileDTO> tiles = new LinkedList<>();
        model.getLocalPlayer().getTiles().forEach(tiles::add);
        model.getLocalPlayer().getDiscardedTiles().forEach(tiles::add);
        return Optional.of(tiles);
    }

    @Override
    public Map<String, String> commands() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Quit", "quit");
        map.put("Print State", "printFullState");
        map.put("Choose a bonus of a Building Permit Tile you own", "choosePlayer");
        return map;
    }
}
