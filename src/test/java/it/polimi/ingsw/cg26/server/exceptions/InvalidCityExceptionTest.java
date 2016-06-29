package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class InvalidCityExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new InvalidCityException();
        Exception e1 = new InvalidCityException("ciao");
        Exception e2 = new InvalidCityException(e);
        Exception e3 = new InvalidCityException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}