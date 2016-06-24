package it.polimi.ingsw.cg26.client.model.state;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *If a player is in this state the game is in the market phase but he can't buy or sell anything
 *because this isn't his turn
 */
public class NotYourTurnMarket implements State {

    @Override
    public Map<String, String> commands() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Quit", "quit");
        map.put("Print State", "printFullState");
        map.put("Print market", "printMarket");
        return map;
    }
}
