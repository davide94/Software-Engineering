package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.GameLogic;

/**
 *
 */
public class King {

    private final static int LINK_PRICE = 2;

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

    public int priceToMove(City city) {
        return this.currentCity.distanceFrom(city) * LINK_PRICE;
    }

    @Override
    public String toString() {
        return "King{" +
                "currentCity='" + currentCity.getName() + "\'" +
                '}';
    }

}