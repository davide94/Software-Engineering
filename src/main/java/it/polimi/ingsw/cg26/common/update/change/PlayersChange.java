package it.polimi.ingsw.cg26.common.update.change;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

import java.util.List;

public class PlayersChange extends ChangeDecorator {

	private static final long serialVersionUID = -9080677273453131329L;

	/**
	 * The new Player State to set
	 */
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
	public void apply(ClientModel model) throws InvalidCityException, InvalidRegionException, PlayerNotFoundException {
		super.apply(model);
		List<PlayerDTO> players = model.getPlayers();
		int i=-1;
		for(PlayerDTO p : players){
			if(p.getName().equals(playerDTO.getName())){
				i = players.indexOf(p);
				break;
			}
		}
		if(i<0)
			throw new PlayerNotFoundException();
		players.set(i, this.playerDTO);
		model.setPlayers(players);
	}
}
