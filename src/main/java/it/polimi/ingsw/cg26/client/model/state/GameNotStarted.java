package it.polimi.ingsw.cg26.client.model.state;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *In this state the game is not started yet
 */
public class GameNotStarted implements State {

    @Override
    public Map<String, String> commands() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Quit", "quit");
        map.put("Print State", "printFullState");
        map.put("Send a message", "sendMessage");
        return map;
    }
}
