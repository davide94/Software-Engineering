package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

import java.util.List;

public class ChooseCityCommand implements Command {

	private static final long serialVersionUID = 2543376097909235749L;
	
	/**
	 * The cities where take the bonuses chosen by the player
	 */
	private final List<CityDTO> chosenCities;
	
	/**
	 * Construct a choose city command
	 * @param chosenCities the cities where take the bonuses chosen by the player
	 * @throws NullPointerException if the cities is null
	 */
	public ChooseCityCommand(List<CityDTO> chosenCities) {
		if(chosenCities == null)
			throw new NullPointerException();
		this.chosenCities = chosenCities;
	}
	
	/**
	 * Get the list of cities chosen by the player
	 * @return the chosenCities
	 */
	public List<CityDTO> getChosenCities() {
		return chosenCities;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
