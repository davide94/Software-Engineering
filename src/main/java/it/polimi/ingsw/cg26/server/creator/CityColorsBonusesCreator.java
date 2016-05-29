package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class CityColorsBonusesCreator {

    private CityColorsBonusesCreator() {
        // Nothing to do here
    }

    protected static Map<CityColor, RewardTile> createCityColorsBonuses(Node root, PoliticDeck politicDeck) {
        Node colorsRoot = Creator.getNode(root, "cityColorsBonuses");
        Map<CityColor, RewardTile> map =  new HashMap<>();
        for (Node colorNode: Creator.getNodes(colorsRoot, "color")) {
            String colorString = Creator.getAttribute(colorNode, "name");
            CityColor color = CityColor.createCityColor(colorString);
            RewardTile tile = BonusesCreator.createBonus(Creator.getNode(colorNode, "bonus"), politicDeck);
            map.put(color, tile);
        }
        return map;
    }
}
