package it.polimi.ingsw.cg26.creator;

import it.polimi.ingsw.cg26.exceptions.BadInputFileException;
import it.polimi.ingsw.cg26.model.board.City;
import it.polimi.ingsw.cg26.model.board.King;
import org.w3c.dom.Node;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class KingCreator {

    private KingCreator() {
        // Nothing to do here
    }

    protected static King createKing(Node root, List<List<City>> cities) {
        King king = null;
        List<City> allCities = new LinkedList<>();
        for (List<City> region: cities)
            allCities.addAll(region);
        for (Node region: Creator.getNodes(Creator.getNode(root, "regions"), "region"))
            for (Node city: Creator.getNodes(Creator.getNode(region, "cities"), "city"))
                if (Creator.hasChild("king", city)) {
                    String name = Creator.getAttribute(city, "name");
                    king = new King(CitiesCreator.getCity(name, allCities));
                }
        if (king == null)
            throw new BadInputFileException();
        return king;
    }
}
