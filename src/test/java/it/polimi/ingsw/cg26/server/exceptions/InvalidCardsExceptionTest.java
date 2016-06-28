package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class InvalidCardsExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new InvalidCardsException();
        Exception e1 = new InvalidCardsException("ciao");
        Exception e2 = new InvalidCardsException(e);
        Exception e3 = new InvalidCardsException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}