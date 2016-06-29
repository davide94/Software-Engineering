package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class InvalidTileExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new InvalidTileException();
        Exception e1 = new InvalidTileException("ciao");
        Exception e2 = new InvalidTileException(e);
        Exception e3 = new InvalidTileException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}