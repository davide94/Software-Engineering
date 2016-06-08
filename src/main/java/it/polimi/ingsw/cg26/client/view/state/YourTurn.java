package it.polimi.ingsw.cg26.client.view.state;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class YourTurn implements State {

    @Override
    public Map<String, String> whatCanIDo() {
        Map<String, String> map = new HashMap<>();
        map.put("Quit", "quit");
        map.put("Print state", "printFullState");
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
}
