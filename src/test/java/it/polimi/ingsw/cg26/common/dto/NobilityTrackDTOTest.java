package it.polimi.ingsw.cg26.common.dto;

import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class NobilityTrackDTOTest {

    private NobilityTrackDTO track;

    private LinkedList<NobilityCellDTO> cells;

    @Before
    public void setUp() throws Exception {
        cells = new LinkedList<>();
        cells.add(new NobilityCellDTO(0, new RewardTileDTO(new LinkedList<BonusDTO>())));
        track = new NobilityTrackDTO(cells);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new NobilityTrackDTO(null);
    }

    @Test
    public void testGetCellsState() throws Exception {
        assertEquals(track.getCells(), cells);
    }

    @Test
    public void testToString() throws Exception {
        track.toString();
    }
}