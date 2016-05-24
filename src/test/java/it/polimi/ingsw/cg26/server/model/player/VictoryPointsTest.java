package it.polimi.ingsw.cg26.server.model.player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class VictoryPointsTest {

    private VictoryPoints victoryPoints;
    @Before
    public void setUp() throws Exception {
        victoryPoints = new VictoryPoints();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddPointsShouldThrowIllegalArgumentException() throws Exception {
        victoryPoints.addPoints(-1);
    }

    @Test
    public void testAddPointsAndGetValueShouldWork() throws Exception {
        victoryPoints.addPoints(5);
        assertEquals(victoryPoints.getValue(), 5);
    }
}