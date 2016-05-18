package it.polimi.ingsw.cg26.server.model.board;

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

    @Override
    public String toString() {
        return "King{" +
                "currentCity='" + currentCity.getName() + "\'" +
                '}';
    }

}