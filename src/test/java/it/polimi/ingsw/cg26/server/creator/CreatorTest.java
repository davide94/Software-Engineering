package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class CreatorTest {

    private GameBoard model;
    private GameBoard gameboard;


    @Before
    public void setUp() throws Exception {
        model = Creator.createGame("src/main/resources/config.xml");
    }

    @Test
    public void testCreateGame() throws Exception {

    }
}