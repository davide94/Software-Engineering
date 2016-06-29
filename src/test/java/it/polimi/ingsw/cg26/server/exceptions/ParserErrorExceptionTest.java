package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class ParserErrorExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new ParserErrorException();
        Exception e1 = new ParserErrorException("ciao");
        Exception e2 = new ParserErrorException(e);
        Exception e3 = new ParserErrorException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}