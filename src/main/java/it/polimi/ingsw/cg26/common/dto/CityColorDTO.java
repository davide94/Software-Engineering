package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;

/**
 *
 */
public class CityColorDTO implements Serializable {

    private static final long serialVersionUID = 1178748348875116351L;

    private final String color;

	/**
	 * Constructs a City Color DTO object
	 * @param color is the color name string
	 * @throws NullPointerException if color is null
	 * @throws IllegalArgumentException if color is empty
     */
    public CityColorDTO(String color) {
        if (color.isEmpty())
            throw new IllegalArgumentException();
        this.color = color;
    }

	/**
	 * Returns the color name string
	 * @return the color name string
     */
    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "CityColorDTO{" +
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
		CityColorDTO other = (CityColorDTO) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		return true;
	}
}
