package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.board.Emporium;

import java.util.List;

/**
 *
 */
public class CityState {

    private String name;

    private CityColor color;

    private List<Emporium> emporiums;

    private List<BonusState> bonuses;

    private List<String> nearCities;

    public CityState(String name, CityColor color, List<BonusState> bonuses, List<Emporium> emporiums, List<String> nearCities) {
        if (name == null || color == null || bonuses == null || emporiums == null || nearCities == null)
            throw new NullPointerException();
        this.name = name;
        this.color = color;
        this.bonuses = bonuses;
        this.emporiums = emporiums;
        this.nearCities = nearCities;
    }
}
