package it.polimi.ingsw.cg26.creator;

import it.polimi.ingsw.cg26.model.board.*;
import it.polimi.ingsw.cg26.model.cards.PoliticDeck;
import org.w3c.dom.Node;

import java.util.List;

/**
 *
 */
public class BoardCreator {

    private BoardCreator() {
        // Nothing to do here
    }

    protected static GameBoard createBoard(Node root) {

        PoliticDeck politicDeck = PoliticDeckCreator.createDeck(root);

        List<Councillor> councillors = CouncillorsCreator.createCouncillors(root);

        Balcony kingsBalcony = BalconyCreator.createBalcony(councillors);

        List<List<City>> cities = CitiesCreator.createCities(root, politicDeck);

        List<Region> regions = RegionsCreator.createRegions(root, cities, politicDeck, councillors);

        NobilityTrack nobilityTrack = NobilityTrackCreator.createNobilityTrack(root, politicDeck);

        King king = KingCreator.createKing(root, cities);

        // Create the Market

        return new GameBoard(politicDeck, councillors, kingsBalcony, regions, nobilityTrack, king);
    }

}
