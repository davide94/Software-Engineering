package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.exceptions.BadInputFileException;
import it.polimi.ingsw.cg26.server.model.board.Balcony;
import it.polimi.ingsw.cg26.server.model.board.Councillor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class BalconyCreatorTest {

    @Test
    public void testCreateBalcony() throws Exception {
        LinkedList<Councillor> councillors = new LinkedList<>();
        councillors.add(Councillor.createCouncillor(new PoliticColor("c1")));
        councillors.add(Councillor.createCouncillor(new PoliticColor("c2")));
        councillors.add(Councillor.createCouncillor(new PoliticColor("c3")));
        councillors.add(Councillor.createCouncillor(new PoliticColor("c4")));

        LinkedList<Councillor> councillors2 = new LinkedList<>(councillors);

        Balcony b1 = BalconyCreator.createBalcony(councillors);

        Balcony b2 = Balcony.createBalcony(4);
        for (Councillor c: councillors) {
            b2.elect(c);
        }

    }

    @Test (expected = BadInputFileException.class)
    public void testShouldFail() throws Exception {
        BalconyCreator.createBalcony(new LinkedList<>());
    }
}