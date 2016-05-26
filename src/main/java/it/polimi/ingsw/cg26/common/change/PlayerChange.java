package it.polimi.ingsw.cg26.common.change;

import java.util.List;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import it.polimi.ingsw.cg26.server.exceptions.NotValidPlayerException;

public class PlayerChange extends ChangeDecorator {

	private PlayerDTO playerDTO;
	
	public PlayerChange(Change decoratedChange, PlayerDTO playerDTO) {
		super(decoratedChange);
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
