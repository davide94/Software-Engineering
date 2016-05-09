package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.exceptions.*;
import it.polimi.ingsw.cg26.model.cards.*;
import it.polimi.ingsw.cg26.model.player.Player;
import java.util.Collection;
import java.util.List;

/**
 * 
 */
public class GameBoard {
	
	private final PoliticDeck politicDeck;
	
	private Collection<Councillor> councillorsPool;
	
	private Balcony kingBalcony;
	
	private Collection<Region> regions;
	
	private NobilityTrack nobilityTrack;
	
	private King king;


    public GameBoard(PoliticDeck deck, Collection<Councillor> councillorsPool, Balcony kingbalcony, Collection<Region> regions, NobilityTrack nobilityTrack, King king) {
    	this.politicDeck=deck;
    	this.councillorsPool=councillorsPool;
    	this.kingBalcony=kingbalcony;
    	this.regions=regions;
    	this.nobilityTrack=nobilityTrack;
    	this.king=king;
    }
    
    /**
     * 
     * @param city
     * @return
     */
    public Region getCityRegion(String city){
    	if(city == null){
    		throw new NullPointerException();
    	} else {
    		for(Region iterRegion : regions){
    			try{
    				iterRegion.getCity(city);
    				return iterRegion;
    			} catch (NotValidCityException cne) {}
    		}
    		throw new NotValidCityException();
    	}
    }

    /**
     * @param region 
     * @param color
     */
    public void elect(String region, String color) {
    	if(region == null || color == null){
    		throw new NullPointerException();
    	} else {
    		Councillor addCouncillor=null;
    		Councillor droppedCouncillor;
    	
    		for(Councillor councillor : councillorsPool){
    			if(councillor.getColor().colorString().equalsIgnoreCase(color)){
    				addCouncillor = councillor;
    				break;
    			}
    		}
    		if(addCouncillor==null){
    			throw new NotExistingCouncillorException();
    		} else {
    			for (Region iterRegion : regions) {
    				if(iterRegion.getName().equalsIgnoreCase(region)){
    					droppedCouncillor = iterRegion.elect(addCouncillor);
    					this.councillorsPool.add(droppedCouncillor);
    					this.councillorsPool.remove(addCouncillor);
    					return;
    				}
    			}
    			throw new NotValidRegionException();
    		}
    	}
    }

    /**
     * @param
     * @return
     */
    public BusinessPermissionTile acquireBPT(List<PoliticCard> politicCards, String region, int numberBPT) {
        if(politicCards == null || region == null){
        	throw new NullPointerException();
        } else {
        	for(Region iterRegion : regions) {
        		if(iterRegion.getName().equalsIgnoreCase(region)){
        			BusinessPermissionTile acquiredBPT = iterRegion.acquireBPT(politicCards, numberBPT);
        			for(PoliticCard iterCard : politicCards){
        				this.politicDeck.discard(iterCard);
        			}
        			return acquiredBPT;
        		}
        	}
        }
        throw new NotValidRegionException();
    }

    /**
     * @return
     */
    public void build(Player player, String city) {
        if(player == null || city == null){
        	throw new NullPointerException();
        } else {
        	this.getCityRegion(city).build(player, city);
        }
    }

    /**
     * @param region 
     * @return
     */
    public void changeBPT(String region) {
        if(region == null){
        	throw new NullPointerException();
        } else {
        	for(Region iterRegion : regions){
        		if(iterRegion.getName().equalsIgnoreCase(region)){
        			iterRegion.changeBPT();
        			return;
        		}
        	}
        	throw new NotValidCityException();
        }
    }

    /**
     * 
     * @param politicCards
     * @param city
     * @param player
     */
    public void buildKing(List<PoliticCard> politicCards, String city, Player player) {
        if(politicCards == null || city == null){
        	throw new NullPointerException();
        } else {
        	if(kingBalcony.checkPoliticCardsCouncillors(politicCards)){
        		//controllo distanza quindi monete del giocatore
        		/*City nextCity = null;
        		for(Region iterRegion : regions){
        			try{
        				nextCity = iterRegion.getCity(city);
        				break;
        			} catch (NotValidCityException cne) {
        				nextCity = null; //necessario?
        			}
        		}
        		if(nextCity == null){
        			throw new NotValidCityException();
        		} else {*/
        		Region nextCityRegion = this.getCityRegion(city);
        		this.king.Move(nextCityRegion.getCity(city));
        		nextCityRegion.build(player, city);
        	}
        }
    }

    /**
     * @return
     */
    public int distance(String a, String b) {
        // TODO implement here
        return 0;
    }

	/**
	 * @return the politicDeck
	 */
	public PoliticDeck getPoliticDeck() {
		return politicDeck;
	}

}