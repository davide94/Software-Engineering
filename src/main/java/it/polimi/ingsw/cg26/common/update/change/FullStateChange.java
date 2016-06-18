package it.polimi.ingsw.cg26.common.update.change;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

/**
 *
 */
public class FullStateChange extends ChangeDecorator {

    private static final long serialVersionUID = -6556639578792576624L;

    /**
     * The new Full State to set
     */
    private GameBoardDTO state;

    /**
     * Constructs a change of the full State of the game
     * @param decoratedChange the change to decorate
     * @param state the new State to set
     * @throws NullPointerException if one or more arguments are null
     */
    public FullStateChange(Change decoratedChange, GameBoardDTO state) {
        super(decoratedChange);
        if(state == null)
        	throw new NullPointerException();
    	this.state = state;
    }

    /**
     * 
     * @return the State
     */
    public GameBoardDTO getState() {
        return state;
    }

    @Override
    public String toString() {
        return "FullStateChange{" +
                "dto=" + state +
                '}';
    }

	@Override
	public void apply(ClientModel model) throws InvalidCityException, InvalidRegionException, PlayerNotFoundException {
        super.apply(model);
        model.setPlayers(state.getPlayers());
        model.setCurrentPlayer(state.getCurrentPlayer());
        model.setPoliticDeck(state.getPoliticDeck());
        model.setCouncillorsPool(state.getCouncillorsPool());
        model.setKingBalcony(state.getKingBalcony());
        model.setRegions(state.getRegions());
        model.setNobilityTrack(state.getNobilityTrack());
        model.setKing(state.getKing());
        model.setMarket(state.getMarket());
        model.setKingDeck(state.getKingDeck());
    }
}
