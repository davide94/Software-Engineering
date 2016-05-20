package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class CityState implements Serializable {

    private static final long serialVersionUID = -6088147095293002180L;

    private String name;

    private CityColorState color;

    private List<EmporiumState> emporiums;

    private List<BonusState> bonuses;

    private List<String> nearCities;

    public CityState(String name, CityColorState color, List<BonusState> bonuses, List<EmporiumState> emporiums, List<String> nearCities) {
        if (name == null || color == null || bonuses == null || emporiums == null || nearCities == null)
            throw new NullPointerException();
        this.name = name;
        this.color = color;
        this.bonuses = bonuses;
        this.emporiums = emporiums;
        this.nearCities = nearCities;
    }

    @Override
    public String toString() {
        return "CityState{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", emporiums=" + emporiums +
                ", bonuses=" + bonuses +
                ", nearCities=" + nearCities +
                '}';
    }
}
