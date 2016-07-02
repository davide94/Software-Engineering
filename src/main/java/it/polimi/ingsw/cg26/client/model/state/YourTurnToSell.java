package it.polimi.ingsw.cg26.client.model.state;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *If a player is in this state the game is in the market phase and he can sell the items
 *because this is his turn
 */
public class YourTurnToSell implements State {

    @Override
    public Map<String, String> commands() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Quit", "quit");
        map.put("Print State", "printFullState");
        map.put("Print market", "printMarket");
        map.put("Sell something", "sell");
        map.put("Fold", "foldSell");
        return map;
    }

    @Override
    public boolean isYourTurnToSell() {
        return true;
    }
}
