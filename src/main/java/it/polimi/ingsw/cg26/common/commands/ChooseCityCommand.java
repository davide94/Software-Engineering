package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

import java.util.List;

public class ChooseCityCommand implements Command {

	private static final long serialVersionUID = 2543376097909235749L;
	
	private final List<CityDTO> chosenCities;
	
	public ChooseCityCommand(List<CityDTO> chosenCities) {
		if(chosenCities == null)
			throw new NullPointerException();
		this.chosenCities = chosenCities;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @return the chosenCity
	 */
	public List<CityDTO> getChosenCities() {
		return chosenCities;
	}

}
