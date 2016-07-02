package it.polimi.ingsw.cg26.client.model.state;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *If you are in this state you are the current player and you can 
 *do the moves that you usually do in your turn
 */
public class YourTurn implements State {

    @Override
    public Map<String, String> commands() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Quit", "quit");
        map.put("Print State", "printFullState");
        map.put("Elect a Councillor", "electAsMainAction");
        map.put("Acquire a Business Permit Tile", "acquire");
        map.put("Build an emporium using a Permit Tile", "build");
        map.put("Build an emporium with the help of the King", "buildKing");
        map.put("Engage an Assistant", "engageAssistant");
        map.put("Change Building Permit Tiles", "changeBPT");
        map.put("Send an Assistant to elect a Councillor", "electAsQuickAction");
        map.put("Perform an additional Main Action", "additionalMainAction");
        map.put("Fold Quick Action", "foldQuickAction");
        return map;
    }

    @Override
    public boolean isYourTurn() {
        return true;
    }
}
