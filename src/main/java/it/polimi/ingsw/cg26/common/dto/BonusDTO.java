package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;

/**
 *
 */
public class BonusDTO implements Serializable {

    private static final long serialVersionUID = 2736328090252332859L;

    private final String kind;

	private final int multiplicity;

	/**
	 * Constructs a Bonus DTO object
	 * @param kind is the kind of bonus
	 * @param multiplicity is the multiplicity
	 * @throws NullPointerException if kind is null
	 * @throws IllegalArgumentException if kind is empty or multiplicity is negative
     */
    public BonusDTO(String kind, int multiplicity) {
		if (kind.isEmpty() || multiplicity < 0)
			throw new IllegalArgumentException();
        this.kind = kind;
		this.multiplicity = multiplicity;
    }

	/**
	 * Returns the kind of bonus
	 * @return the kind of bonus
     */
    public String getKind() {
        return kind;
    }

	/**
	 * Returns the multiplicity of the bonus
	 * @return the multiplicity of the bonus
     */
	public int getMultiplicity() {
		return multiplicity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BonusDTO that = (BonusDTO) o;

		if (multiplicity != that.multiplicity) return false;
		return kind != null ? kind.equals(that.kind) : that.kind == null;

	}

	@Override
	public int hashCode() {
		int result = kind != null ? kind.hashCode() : 0;
		result = 31 * result + multiplicity;
		return result;
	}

	@Override
	public String toString() {
		return "BonusDTO{" +
				"kind='" + kind + '\'' +
				", multiplicity=" + multiplicity +
				'}';
	}

}
