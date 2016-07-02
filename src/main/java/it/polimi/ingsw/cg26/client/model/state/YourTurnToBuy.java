package it.polimi.ingsw.cg26.client.model.state;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *If a player is in this state the game is in the market phase and he can buy the items
 *because this is his turn
 */
public class YourTurnToBuy implements State {

    @Override
    public Map<String, String> commands() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Quit", "quit");
        map.put("Print State", "printFullState");
        map.put("Print market", "printMarket");
        map.put("Buy something", "buy");
        map.put("Fold", "foldBuy");
        return map;
    }

    @Override
    public boolean yourTurntoBuy() {
        return true;
    }
}
