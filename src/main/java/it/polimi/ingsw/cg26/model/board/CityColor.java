package it.polimi.ingsw.cg26.model.board;

/**
 * 
 */
public class CityColor {
	
	
	 /**
     * 
     */
    private String CityColor;

    
    /**
     * Default constructor
     */
    public CityColor(String color) {
    	this.CityColor=color;
    }

   
    /**
     * @return
     */
    public String getCityColor() {
        return this.CityColor;
    }



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CityColor == null) ? 0 : CityColor.hashCode());
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
		if (CityColor == null) {
			if (other.CityColor != null)
				return false;
		} else if (!CityColor.equals(other.CityColor))
			return false;
		return true;
	}
    
    

}