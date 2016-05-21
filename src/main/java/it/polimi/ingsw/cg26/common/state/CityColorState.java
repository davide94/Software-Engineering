package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;

/**
 *
 */
public class CityColorState implements Serializable {

    private static final long serialVersionUID = 1178748348875116351L;

    private final String color;

    public CityColorState(String color) {
        if (color == null)
            throw new NullPointerException();
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "CityColorState{" +
                "color='" + color + '\'' +
                '}';
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CityColorState other = (CityColorState) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		return true;
	}
    
    
}
