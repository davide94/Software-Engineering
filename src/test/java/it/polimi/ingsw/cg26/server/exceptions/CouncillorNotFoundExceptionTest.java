package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class CouncillorNotFoundExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new CouncillorNotFoundException();
        Exception e1 = new CouncillorNotFoundException("ciao");
        Exception e2 = new CouncillorNotFoundException(e);
        Exception e3 = new CouncillorNotFoundException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}