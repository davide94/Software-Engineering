package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class ExistingEmporiumExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new ExistingEmporiumException();
        Exception e1 = new ExistingEmporiumException("ciao");
        Exception e2 = new ExistingEmporiumException(e);
        Exception e3 = new ExistingEmporiumException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}