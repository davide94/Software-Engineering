package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class PlayerNotFoundExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new PlayerNotFoundException();
        Exception e1 = new PlayerNotFoundException("ciao");
        Exception e2 = new PlayerNotFoundException(e);
        Exception e3 = new PlayerNotFoundException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}