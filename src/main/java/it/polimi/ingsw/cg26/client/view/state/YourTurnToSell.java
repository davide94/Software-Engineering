package it.polimi.ingsw.cg26.client.view.state;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class YourTurnToSell implements State {

    @Override
    public Map<String, String> whatCanIDo() {
        Map<String, String> map = new HashMap<>();
        map.put("Quit", "quit");
        return map;
    }
}
