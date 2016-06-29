package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class NoRemainingCardsExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new NoRemainingCardsException();
        Exception e1 = new NoRemainingCardsException("ciao");
        Exception e2 = new NoRemainingCardsException(e);
        Exception e3 = new NoRemainingCardsException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}