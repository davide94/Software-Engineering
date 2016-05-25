package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.dto.CityColorDTO;

/**
 * 
 */
public class CityColor {

	/**
	 *
	 */
	private String color;

	/**
	 *
	 */
	private CityColor(String color) {
		if (color == null)
			throw new NullPointerException();
		this.color = color;
	}

	public static CityColor createCityColor(String color) {
		return new CityColor(color);
	}

	public CityColorDTO getState() {
		return new CityColorDTO(color);
	}

	/**
     * @return
     */
    public String getColor() {
        return this.color;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CityColor other = (CityColor) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  "CityColor='" + color + '\'' +
				'}';
	}

}