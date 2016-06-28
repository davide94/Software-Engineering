package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class CityNotFoundExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new CityNotFoundException();
        Exception e1 = new CityNotFoundException("ciao");
        Exception e2 = new CityNotFoundException(e);
        Exception e3 = new CityNotFoundException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}