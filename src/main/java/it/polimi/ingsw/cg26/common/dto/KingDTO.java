package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;

/**
 *
 */
public class KingDTO implements Serializable {

    private static final long serialVersionUID = 4923580451344651682L;

    private String currentCity;

    /**
     * Constructs a King DTO object
     * @param currentCity is a string that identifies the city where the king is
     * @throws NullPointerException if currentCity is null
     * @throws IllegalArgumentException if currentCity is empty
     */
    public KingDTO(String currentCity) {
        if (currentCity.isEmpty())
            throw new IllegalArgumentException();
        this.currentCity = currentCity;
    }

    /**
     * Returns a string that identifies the City where the King is
     * @return a string that identifies the City where the King is
     */
    public String getCurrentCity() {
        return currentCity;
    }

    /**
     * Sets the current City
     * @param currentCity is a string that identifies a city
     * @throws NullPointerException if currentCity is null
     * @throws IllegalArgumentException if currentCity is empty
     */
    public void setCurrentCity(String currentCity) {
        if (currentCity.isEmpty())
            throw new IllegalArgumentException();
		this.currentCity = currentCity;
	}

    @Override
    public String toString() {
        return "KingDTO{" +
                "currentCity='" + currentCity + '\'' +
                '}';
    }
}
