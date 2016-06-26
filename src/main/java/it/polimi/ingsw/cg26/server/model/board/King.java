package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.dto.KingDTO;


public class King {

	/**
	 * the cost to move the king between two cities that are adjacent
	 */
    private static final int LINK_PRICE = 2;
    
    /**
     * the city in which the king is located 
     */
    private City currentCity;
    
    /**
     * Default constructor
     */
    private King(City currentCity) {
        if (currentCity == null)
            throw new NullPointerException();
        this.currentCity = currentCity;
    }

    /**
     * Create the king
     * @param currentCity is the city in which should be created the king
     * @return a new king
     */
    public static King createKing(City currentCity) {
        return new King(currentCity);
    }
    
    /**
     * Create a king DTO
     * @return the DTO of the king
     */
    public KingDTO getState() {
        return new KingDTO(currentCity.getName());
    }

    /**
     * Move the king from a city to another
     * @param city is the city in which the king should be moved
     * @throws NullPointerException if the city in which the king should be moved is null
     */
    public void move(City city) {
        if (city == null)
            throw new NullPointerException();
        this.currentCity = city;
    }

    /**
     * Get the price to move the king to another city
     * @param city is the city in which the king should be moved
     * @return the cost to move the king from the current city to the city of the argument
     */
    public int priceToMove(City city) {
        return this.currentCity.distanceFrom(city) * LINK_PRICE;
    }

    /**
     * Get the city in which there is the king
     * @return the city in which there is the king
     */
    public City getCurrentCity() {
		return currentCity;
	}

    /**
     * Set the city where there is the king
     * @param currentCity is the city where the king is
     * @throws NullPointerException if the city is null
     */
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