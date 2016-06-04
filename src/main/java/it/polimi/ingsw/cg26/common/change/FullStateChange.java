package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;

/**
 *
 */
public class FullStateChange extends ChangeDecorator {

    private static final long serialVersionUID = -6556639578792576624L;

    private GameBoardDTO state;

    /**
     * Constructs a change of the full state of the game
     * @param decoratedChange the change to decorate
     * @param state the new state to set
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
     * @return the state
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
	public void apply(GameBoardDTO gameGameBoardDTO){
        super.apply(gameGameBoardDTO);
        gameGameBoardDTO.setPlayers(state.getPlayers());
        gameGameBoardDTO.setCurrentPlayer(state.getCurrentPlayer());
        gameGameBoardDTO.setPoliticDeck(state.getPoliticDeck());
        gameGameBoardDTO.setCouncillorsPool(state.getCouncillorsPool());
        gameGameBoardDTO.setKingBalcony(state.getKingBalcony());
        gameGameBoardDTO.setRegions(state.getRegions());
        gameGameBoardDTO.setNobilityTrack(state.getNobilityTrack());
        gameGameBoardDTO.setKing(state.getKing());
        gameGameBoardDTO.setMarket(state.getMarket());
        gameGameBoardDTO.setKingDeck(state.getKingDeck());
    }
}
