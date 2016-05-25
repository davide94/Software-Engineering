package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;

/**
 *
 */
public class BonusState implements Serializable {

    private static final long serialVersionUID = 2736328090252332859L;

    private final String name;

	private final int multiplicity;

    public BonusState(String name, int multiplicity) {
		if (name == null)
			throw new NullPointerException();
		if (multiplicity < 0)
			throw new IllegalArgumentException();
        this.name = name;
		this.multiplicity = multiplicity;
    }

    public String getName() {
        return name;
    }

	public int getMultiplicity() {
		return multiplicity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BonusState that = (BonusState) o;

		if (multiplicity != that.multiplicity) return false;
		return name != null ? name.equals(that.name) : that.name == null;

	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + multiplicity;
		return result;
	}

	@Override
	public String toString() {
		return "BonusState{" +
				"name='" + name + '\'' +
				", multiplicity=" + multiplicity +
				'}';
	}

}
