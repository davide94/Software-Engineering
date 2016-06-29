package it.polimi.ingsw.cg26.server.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class BadInputFileExceptionTest {

    @Test
    public void test() throws Exception {
        Exception e =  new NullPointerException();

        new BadInputFileException();
        Exception e1 = new BadInputFileException("ciao");
        Exception e2 = new BadInputFileException(e);
        Exception e3 = new BadInputFileException("ciao", e);

        assertEquals(e1.getMessage(), "ciao");
        assertEquals(e2.getCause(), e);
        assertEquals(e3.getMessage(), "ciao");
        assertEquals(e3.getCause(), e);
    }
}