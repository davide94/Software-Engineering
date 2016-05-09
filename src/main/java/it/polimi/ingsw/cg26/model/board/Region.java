package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.bonus.Bonus;
import it.polimi.ingsw.cg26.model.cards.*;
import it.polimi.ingsw.cg26.model.player.Player;
import it.polimi.ingsw.cg26.exceptions.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 
 */
public class Region {

    /**
     * 
     */
    private String name;
    private Collection<City> cities;
    private Balcony balcony;
    private BusinessPermissionTileDeck BPTdeck;
    private Collection<Bonus> bonuses;
    

    public Region(String name, Collection<City> cities, BusinessPermissionTileDeck deck, Balcony balcony, Collection<Bonus> bonuses) {
    	if(name==null || cities==null || deck==null || balcony==null || bonuses==null)
    	{throw new NullPointerException();}
    	this.name=name;
    	this.cities=cities;
    	this.BPTdeck=deck;
    	this.balcony=balcony;
    	this.bonuses=bonuses;
    	
    	
    }

    /**
     * @param
     * @return
     */
    public City getCity(String city) {
        for(City iterCity: cities){
        	if(iterCity.getName().equalsIgnoreCase(city)){
        		return iterCity;
        	}
        }
        throw new NotValidCityException();
    }

    
    
    /**
     * @param councillor
     */
    public Councillor elect(Councillor councillor) {
    	Councillor droppedCouncillor = balcony.elect(councillor);
    	return droppedCouncillor;
    }

    /**
     * @param
     * @param city 
     * @return
     */
    public void build(Player p, String city) {
        getCity(city).build(p);
    }
    
    public BusinessPermissionTile acquireBPT(List<PoliticCard> politicCards, int numberBPT){
    	if(politicCards == null){
    		throw new NullPointerException();
    	} else if(balcony.checkPoliticCardsCouncillors(politicCards)){
    		if(numberBPT == 1){
    			return BPTdeck.draw();
    		} else if(numberBPT == 2) {
    			return BPTdeck.drawSecond();
    		} else {
    			throw new IllegalArgumentException();
    		}
    	} else {
    		throw new InvalidCardsException();
    	}
    }
    
    /**
     * 
     */
    public void changeBPT(){
    	//this.BPTDeck.changeTiles; metodo da aggiungere in BPTDeck
    }

    /*public Set<City> getCities() {
        //implement here
        return null;
    }
    */
    
    
	/**
	 * @return the name of the region
	 */
	public String getName() {
		return name;
	}

}