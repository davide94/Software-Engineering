package it.polimi.ingsw.cg26.common.dto;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class BalconyDTOTest {

    BalconyDTO balcony;

    @Before
    public void setUp() throws Exception {
        CouncillorDTO c1 = new CouncillorDTO(new PoliticColorDTO("c1"));
        CouncillorDTO c2 = new CouncillorDTO(new PoliticColorDTO("c2"));
        CouncillorDTO c3 = new CouncillorDTO(new PoliticColorDTO("c3"));
        CouncillorDTO c4 = new CouncillorDTO(new PoliticColorDTO("c4"));
        LinkedList<CouncillorDTO> councillors = new LinkedList<>();
        councillors.add(c1);
        councillors.add(c2);
        councillors.add(c3);
        councillors.add(c4);
        balcony = new BalconyDTO(councillors);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new BalconyDTO(null);
    }

    @Test
    public void testGetCouncillors() throws Exception {
        CouncillorDTO c1 = new CouncillorDTO(new PoliticColorDTO("c1"));
        CouncillorDTO c2 = new CouncillorDTO(new PoliticColorDTO("c2"));
        CouncillorDTO c3 = new CouncillorDTO(new PoliticColorDTO("c3"));
        CouncillorDTO c4 = new CouncillorDTO(new PoliticColorDTO("c4"));
        LinkedList<CouncillorDTO> councillors = new LinkedList<>();
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