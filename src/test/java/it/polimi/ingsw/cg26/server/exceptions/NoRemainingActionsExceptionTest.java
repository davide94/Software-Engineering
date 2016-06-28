package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class NoRemainingActionsExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new NoRemainingActionsException();
        Exception e1 = new NoRemainingActionsException("ciao");
        Exception e2 = new NoRemainingActionsException(e);
        Exception e3 = new NoRemainingActionsException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}