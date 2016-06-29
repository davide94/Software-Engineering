package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class InvalidSellableExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new InvalidSellableException();
        Exception e1 = new InvalidSellableException("ciao");
        Exception e2 = new InvalidSellableException(e);
        Exception e3 = new InvalidSellableException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}