package it.polimi.ingsw.cg26.client.model.state.pendingRequest;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;

import java.util.*;

/**
 *
 */
public class PendingBPTRequest extends PendingRequest {

    public PendingBPTRequest(Model model, int multiplicity) {
        super(model, multiplicity);
    }

    @Override
    public Optional<List<BusinessPermissionTileDTO>> getPendingBPTBonusRequest() {
        List<BusinessPermissionTileDTO> tiles = new LinkedList<>();
        for (RegionDTO r: model.getRegions())
            tiles.addAll(r.getDeck().getOpenCards());
        return Optional.of(tiles);
    }

    @Override
    public Map<String, String> commands() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Quit", "quit");
        map.put("Print State", "printFullState");
        map.put("Choose pending Business Permit Tile", "chooseBPT");
        return map;
    }
}
