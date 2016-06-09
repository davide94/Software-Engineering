package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.exceptions.BadInputFileException;
import it.polimi.ingsw.cg26.server.model.board.Balcony;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.Councillor;
import it.polimi.ingsw.cg26.server.model.board.Region;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTileDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;

import org.w3c.dom.Node;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class RegionsCreator {

    private RegionsCreator() {
        // Nothing to do here
    }

    protected static List<Region> createRegions(Node root, List<List<City>> cities, PoliticDeck politicDeck, List<Councillor> councillors) throws BadInputFileException {
        if (root == null || cities == null || politicDeck == null || councillors == null)
            throw new NullPointerException();

        List<Region> regions = new LinkedList<>();

        int i = 0;
        for (Node regionRoot: Creator.getNodes(Creator.getNode(root, "regions"), "region")) {
            regions.add(createRegion(regionRoot, cities.get(i), politicDeck, councillors));
            i++;
        }
        return regions;
    }

    private static Region createRegion(Node root, List<City> cities, PoliticDeck politicDeck, List<Councillor> councillors) throws BadInputFileException {
        String name = Creator.getAttribute(root, "name");
        BusinessPermissionTileDeck tilesDeck = BusinessPermitTileDeckCreator.createDeck(Creator.getNode(root, "permissionTiles"), cities, politicDeck);
        Balcony balcony = BalconyCreator.createBalcony(councillors);
        Bonus bonus = BonusesCreator.createBonus(Creator.getNode(root, "bonus"), politicDeck);
        return Region.createRegion(name, cities, tilesDeck, balcony, bonus);
    }

}
