package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 */
public class CreatorTest {

    private Node root;
    private GameBoard gameBoard;

    @Before
    public void setUp() throws Exception {
        DOMParserInterface parserInterface = new XMLAdapter();
        Document document = parserInterface.parse("maps/test.xml", "maps/schema.xsd");
        root = document.getDocumentElement();

        gameBoard = Creator.createGame("maps/test.xml");
    }

    @Test
    public void testCreateGame() throws Exception {

    }
}