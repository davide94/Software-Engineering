package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.GameLogic;

/**
 *
 */
public class King {


	private City currentCity;

    /**
     * Default constructor
     */
    public King(City currentCity) {
        if (currentCity == null)
            throw new NullPointerException();
        this.currentCity = currentCity;
    }

    /**
     * @param
     */
    public void Move(City city) {
        if (city == null)
            throw new NullPointerException();
        this.currentCity = city;
    }

}