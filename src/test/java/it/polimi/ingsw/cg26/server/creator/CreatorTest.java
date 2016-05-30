package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.time.Instant;

import static org.junit.Assert.*;

/**
 *
 */
public class CreatorTest {

    private Node root;
    private GameBoard gameBoard;

    @Before
    public void setUp() throws Exception {
        Instant before = Instant.now();

        DOMParserInterface parserInterface = new XMLAdapter();

        Document document = parserInterface.parse("src/test/resources/configTest.xml", "src/main/resources/schema.xsd");

        root = document.getFirstChild();

        gameBoard = Creator.createGame("src/test/resources/configTest.xml");
    }

    @Test
    public void testCreateGame() throws Exception {
        //assertEquals(gameBoard, BoardCreator.createBoard(root));
    }
}