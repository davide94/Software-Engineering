package it.polimi.ingsw.cg26.creator;

import it.polimi.ingsw.cg26.exceptions.BadInputFileException;
import it.polimi.ingsw.cg26.model.board.Balcony;
import it.polimi.ingsw.cg26.model.board.Councillor;

import java.util.List;

/**
 *
 */
public class BalconyCreator {

    private static final int BALCONY_SIZE = 4;

    private BalconyCreator() {
        // Nothing to do here
    }

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
