package it.polimi.ingsw.cg26.common.change;

import java.util.List;

import it.polimi.ingsw.cg26.common.state.BoardState;
import it.polimi.ingsw.cg26.common.state.PlayerState;
import it.polimi.ingsw.cg26.server.exceptions.NotValidPlayerException;

public class PlayerChange extends ChangeDecorator {

	private PlayerState playerState;
	
	public PlayerChange(Change decoratedChange, PlayerState playerState) {
		super(decoratedChange);
		this.playerState = playerState;
	}

	@Override
	public void apply(BoardState gameBoardState){
		super.apply(gameBoardState);
		List<PlayerState> players = gameBoardState.getPlayers();
		int i=-1;
		for(PlayerState p : players){
			if(p.getName().equals(playerState.getName())){
				i = players.indexOf(p);
				break;
			}
		}
		if(i<0)
			throw new NotValidPlayerException();
		players.set(i, this.playerState);
	}
	
}
