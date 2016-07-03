package it.polimi.ingsw.cg26.client.model.state.pendingRequest;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.EmporiumDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;

import java.util.*;

/**
 *
 */
public class PendingCityBonusRequest extends PendingRequest {

    public PendingCityBonusRequest(Model model, int multiplicity) {
        super(model, multiplicity);
    }

    @Override
    public Optional<List<CityDTO>> getPendingCityBonusRequest() {
        List<CityDTO> cities = new LinkedList<>();
        for (RegionDTO r: model.getRegions())
            for (CityDTO c: r.getCities())
                for (EmporiumDTO e: c.getEmporiums())
                    if (e.belongsTo(model.getLocalPlayer()) && !c.getBonuses().toString().contains("obility"))
                        cities.add(c);
        return Optional.of(cities);
    }

    @Override
    public Map<String, String> commands() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Quit", "quit");
        map.put("Print State", "printFullState");
        map.put("Choose a Bonus of a city you have already built in", "chooseCity");
        return map;
    }
}
