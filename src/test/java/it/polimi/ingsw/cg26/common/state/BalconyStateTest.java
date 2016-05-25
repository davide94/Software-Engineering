package it.polimi.ingsw.cg26.common.state;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class BalconyStateTest {

    BalconyState balcony;

    @Before
    public void setUp() throws Exception {
        CouncillorState c1 = new CouncillorState(new PoliticColorState("c1"));
        CouncillorState c2 = new CouncillorState(new PoliticColorState("c2"));
        CouncillorState c3 = new CouncillorState(new PoliticColorState("c3"));
        CouncillorState c4 = new CouncillorState(new PoliticColorState("c4"));
        LinkedList<CouncillorState> councillors = new LinkedList<>();
        councillors.add(c1);
        councillors.add(c2);
        councillors.add(c3);
        councillors.add(c4);
        balcony = new BalconyState(councillors);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new BalconyState(null);
    }

    @Test
    public void testGetCouncillors() throws Exception {
        CouncillorState c1 = new CouncillorState(new PoliticColorState("c1"));
        CouncillorState c2 = new CouncillorState(new PoliticColorState("c2"));
        CouncillorState c3 = new CouncillorState(new PoliticColorState("c3"));
        CouncillorState c4 = new CouncillorState(new PoliticColorState("c4"));
        LinkedList<CouncillorState> councillors = new LinkedList<>();
        councillors.add(c1);
        councillors.add(c2);
        councillors.add(c3);
        councillors.add(c4);

        assertEquals(balcony.getCouncillors(), councillors);
    }

    @Test
    public void testToString() throws Exception {
        balcony.toString();
    }
}