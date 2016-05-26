package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class NobilityTrackDTO implements Serializable {

    private static final long serialVersionUID = -3226153742652964326L;

    private List<NobilityCellDTO> cells;

    /**
     * Constructs a Nobility Track DTO object
     * @param cells is a list of Nobility Cells DTO
     * @throws NullPointerException if cells is null
     */
    public NobilityTrackDTO(List<NobilityCellDTO> cells) {
        if (cells == null)
            throw new NullPointerException();
        this.cells = cells;
    }

    /**
     * Returns a list of Nobility Cells DTO
     * @return a list of Nobility Cells dtDTOo
     */
    public List<NobilityCellDTO> getCells() {
        return cells;
    }

    @Override
    public String toString() {
        return "NobilityTrackDTO{" +
                "cells=" + cells +
                '}';
    }
}
