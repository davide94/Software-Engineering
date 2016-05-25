package it.polimi.ingsw.cg26.common.state;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class NobilityTrackStateTest {

    private NobilityTrackState track;

    private LinkedList<NobilityCellState> cells;

    @Before
    public void setUp() throws Exception {
        cells = new LinkedList<>();
        cells.add(new NobilityCellState(0, new LinkedList<BonusState>()));
        track = new NobilityTrackState(cells);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new NobilityTrackState(null);
    }

    @Test
    public void testGetCellsState() throws Exception {
        assertEquals(track.getCellsState(), cells);
    }

    @Test
    public void testToString() throws Exception {
        track.toString();
    }
}