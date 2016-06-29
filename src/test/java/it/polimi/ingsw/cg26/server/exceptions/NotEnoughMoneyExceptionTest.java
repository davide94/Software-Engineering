package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class NotEnoughMoneyExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new NotEnoughMoneyException();
        Exception e1 = new NotEnoughMoneyException("ciao");
        Exception e2 = new NotEnoughMoneyException(e);
        Exception e3 = new NotEnoughMoneyException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}