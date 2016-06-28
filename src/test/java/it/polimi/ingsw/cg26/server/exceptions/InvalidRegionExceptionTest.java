package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class InvalidRegionExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new InvalidRegionException();
        Exception e1 = new InvalidRegionException("ciao");
        Exception e2 = new InvalidRegionException(e);
        Exception e3 = new InvalidRegionException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}