package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.exceptions.BadInputFileException;
import it.polimi.ingsw.cg26.server.model.board.*;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.cards.KingDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.market.Market;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class BoardCreator {

    private BoardCreator() {
        // Nothing to do here
    }

    protected static GameBoard createBoard(Node root) throws BadInputFileException {

        PoliticDeck politicDeck = PoliticDeckCreator.createDeck(root);

        List<Councillor> councillors = CouncillorsCreator.createCouncillors(root);

        Balcony kingsBalcony = BalconyCreator.createBalcony(councillors);

        List<List<City>> cities = CitiesCreator.createCities(root, politicDeck);

        List<Region> regions = RegionsCreator.createRegions(root, cities, politicDeck, councillors);

        NobilityTrack nobilityTrack = NobilityTrackCreator.createNobilityTrack(root, politicDeck);

        King king = KingCreator.createKing(root, cities);

        Market market = new Market();

        KingDeck kingDeck = KingDeckCreator.createKingDeck(root, politicDeck);

        Map<CityColor, Bonus> colorBonuses = CityColorsBonusesCreator.createCityColorsBonuses(root, politicDeck);

        return GameBoard.createGameBoard(politicDeck, councillors, kingsBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
    }

}
