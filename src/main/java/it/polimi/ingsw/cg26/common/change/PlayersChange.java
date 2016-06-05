package it.polimi.ingsw.cg26.common.change;

import java.util.List;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import it.polimi.ingsw.cg26.server.exceptions.NotValidPlayerException;

public class PlayersChange extends ChangeDecorator {

	private static final long serialVersionUID = -9080677273453131329L;

	private PlayerDTO playerDTO;
	
	/**
	 * Construct a change for a player of the game
	 * @param decoratedChange the change to decorate
	 * @param playerDTO the player to set
	 * @throws NullPointerException if one or more arguments are null
	 */
	public PlayersChange(Change decoratedChange, PlayerDTO playerDTO) {
		super(decoratedChange);
		if(playerDTO == null)
			throw new NullPointerException();
		this.playerDTO = playerDTO;
	}

	@Override
	public void apply(GameBoardDTO gameGameBoardDTO){
		super.apply(gameGameBoardDTO);
		List<PlayerDTO> players = gameGameBoardDTO.getPlayers();
		int i=-1;
		for(PlayerDTO p : players){
			if(p.getName().equals(playerDTO.getName())){
				i = players.indexOf(p);
				break;
			}
		}
		if(i<0)
			throw new NotValidPlayerException();
		players.set(i, this.playerDTO);
	}
	
}
