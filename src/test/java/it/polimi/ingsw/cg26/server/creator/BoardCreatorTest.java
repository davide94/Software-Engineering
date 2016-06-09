package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.*;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.cards.KingDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.market.Market;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *
 */
public class BoardCreatorTest {

    private GameBoard gameBoardTest;
    private GameBoard gameBoard;

    @Before
    public void setUp() throws Exception {

        DOMParserInterface parserInterface = new XMLAdapter();

        Document document = parserInterface.parse("src/test/resources/configTest.xml", "src/main/resources/schema.xsd");

        Node root = document.getFirstChild();

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

        gameBoard = GameBoard.createGameBoard(politicDeck, councillors, kingsBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
        gameBoardTest = BoardCreator.createBoard(root);
    }

    @Test (expected = NullPointerException.class)
    public void testBoardCreatorShouldThrowNullPointerException() throws Exception {
        BoardCreator.createBoard(null);
    }

}