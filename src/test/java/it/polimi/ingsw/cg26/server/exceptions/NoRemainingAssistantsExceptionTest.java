package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class NoRemainingAssistantsExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new NoRemainingAssistantsException();
        Exception e1 = new NoRemainingAssistantsException("ciao");
        Exception e2 = new NoRemainingAssistantsException(e);
        Exception e3 = new NoRemainingAssistantsException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}