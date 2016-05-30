package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.dto.KingDTO;

/**
 *
 */
public class King {

    private static final int LINK_PRICE = 2;

    private City currentCity;

    /**
     * Default constructor
     */
    private King(City currentCity) {
        if (currentCity == null)
            throw new NullPointerException();
        this.currentCity = currentCity;
    }

    public static King createKing(City currentCity) {
        return new King(currentCity);
    }

    public KingDTO getState() {
        return new KingDTO(currentCity.getName());
    }

    /**
     * @param
     */
    public void move(City city) {
        if (city == null)
            throw new NullPointerException();
        this.currentCity = city;
    }

    public int priceToMove(City city) {
        return this.currentCity.distanceFrom(city) * LINK_PRICE;
    }
    
    

    public City getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(City currentCity) {
		if (currentCity == null)
            throw new NullPointerException();
		this.currentCity = currentCity;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        King king = (King) o;

        return currentCity != null ? currentCity.equals(king.currentCity) : king.currentCity == null;

    }

    @Override
    public int hashCode() {
        return currentCity != null ? currentCity.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "King{" +
                "currentCity='" + currentCity.getName() + "\'" +
                '}';
    }

}