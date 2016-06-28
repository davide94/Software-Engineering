package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class SellableNotFoundExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new SellableNotFoundException();
        Exception e1 = new SellableNotFoundException("ciao");
        Exception e2 = new SellableNotFoundException(e);
        Exception e3 = new SellableNotFoundException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}