package it.polimi.ingsw.cg26.creator;

import it.polimi.ingsw.cg26.model.board.City;
import it.polimi.ingsw.cg26.model.bonus.Bonus;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTileDeck;
import it.polimi.ingsw.cg26.model.cards.PoliticDeck;
import org.w3c.dom.Node;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class BusinessPermitTileDeckCreator {

    private BusinessPermitTileDeckCreator() {
        // Nothing to do here
    }

    protected static BusinessPermissionTileDeck createDeck(Node root, List<City> cities, PoliticDeck politicDeck) {
        List<BusinessPermissionTile> tiles = createTiles(root, cities, politicDeck);
        return new BusinessPermissionTileDeck(tiles);
    }

    private static List<BusinessPermissionTile> createTiles(Node root, List<City> cities, PoliticDeck politicDeck) {
        List<BusinessPermissionTile> tiles = new LinkedList<>();
        for (Node node: Creator.getNodes(root, "permissionTile")) {
            List<City> cardCities = CitiesCreator.getCities(Creator.getNode(node, "cities"), cities);
            List<Bonus> cardBonuses = BonusesCreator.createBonus(Creator.getNode(node, "bonus"), politicDeck);
            tiles.add(new BusinessPermissionTile(cardCities, cardBonuses));
        }
        return tiles;
    }
}
