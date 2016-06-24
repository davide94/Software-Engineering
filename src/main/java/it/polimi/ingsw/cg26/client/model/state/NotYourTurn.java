package it.polimi.ingsw.cg26.client.model.state;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *If you are in this state another player is the current player and you can't 
 *do the moves that you usually do in your turn
 */
public class NotYourTurn implements State {

    @Override
    public Map<String, String> commands() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Quit", "quit");
        map.put("Print State", "printFullState");
        return map;
    }
}
