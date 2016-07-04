package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;

import java.util.List;

/**
 *
 */
public class MatchEnded implements Event {

	private static final long serialVersionUID = -6954465562704054321L;

    private List<PlayerDTO> finalRanking;

    public MatchEnded(List<PlayerDTO> finalRanking) {
        this.finalRanking = finalRanking;
    }

    @Override
    public void apply(ClientModel model) {
        model.setPlayers(finalRanking);
        model.getState().matchEnded();
    }
}
