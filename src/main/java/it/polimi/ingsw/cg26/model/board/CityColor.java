package it.polimi.ingsw.cg26.model.board;

/**
 * 
 */
public class CityColor {

    /**
     * 
     */
    private String cityColor;
    
    /**
     *
     */
    public CityColor(String color) {
    	this.cityColor=color;
    }

	/**
     * @return
     */
    public String getCityColor() {
        return this.cityColor;
    }



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cityColor == null) ? 0 : cityColor.hashCode());
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
		if (cityColor == null) {
			if (other.cityColor != null)
				return false;
		} else if (!cityColor.equals(other.cityColor) || !cityColor.equalsIgnoreCase(other.cityColor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CityColor{" +
				"CityColor='" + cityColor + '\'' +
				'}';
	}

}