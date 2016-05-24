package it.polimi.ingsw.cg26.server.model.player;

import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class RemainingActionsTest {

    private RemainingActions remainingActions;

    @Before
    public void setUp() throws Exception {
        remainingActions = new RemainingMainActions();
    }

    @Test
    public void testCanPerform() throws Exception {
        assertTrue(remainingActions.canPerform());
    }

    @Test
    public void testGetValue() throws Exception {
        assertEquals(remainingActions.getValue(), 1);
    }

    @Test (expected = NoRemainingActionsException.class)
    public void testPerformShouldThrowNoRemainingActionsException() throws Exception {
        remainingActions.perform();
        remainingActions.perform();
    }

    @Test
    public void testPerformShouldWork() throws Exception {
        remainingActions.perform();
        assertEquals(remainingActions.getValue(), 0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetRemainingShouldThrowIllegalArgumentException() throws Exception {
        remainingActions.setRemaining(-1);
    }

    @Test
    public void testSetRemainingShouldWork() throws Exception {
        remainingActions.setRemaining(3);
        assertEquals(remainingActions.getValue(), 3);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddActionsShouldThrowIllegalArgumentException() throws Exception {
        remainingActions.addActions(-2);
    }

    @Test
    public void testAddActionsShouldWork() throws Exception {
        remainingActions.addActions(2);
        assertEquals(remainingActions.getValue(), 3);
    }
}