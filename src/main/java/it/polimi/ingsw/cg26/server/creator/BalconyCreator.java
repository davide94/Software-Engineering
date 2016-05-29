package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.exceptions.BadInputFileException;
import it.polimi.ingsw.cg26.server.model.board.Balcony;
import it.polimi.ingsw.cg26.server.model.board.Councillor;

import java.util.List;

/**
 *
 */
public class BalconyCreator {

    private static final int BALCONY_SIZE = 4;

    protected static Balcony createBalcony(List<Councillor> councillors) {
        Balcony balcony = Balcony.createBalcony(BALCONY_SIZE);
        for (int i = 0; i < BALCONY_SIZE; i++) {
            if (councillors.isEmpty())
                throw new BadInputFileException();
            Councillor councillor = councillors.remove(0);
            balcony.elect(councillor);
        }
        return balcony;
    }
}
