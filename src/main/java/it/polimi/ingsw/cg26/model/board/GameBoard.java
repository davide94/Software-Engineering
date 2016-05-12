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
	
	private final Collection<Councillor> councillorsPool;
	
	//private KingDeck kingDeck;
	
	private final Balcony kingBalcony;
	
	private final Collection<Region> regions;
	
	private final NobilityTrack nobilityTrack;
	
	private final King king;


    public GameBoard(PoliticDeck deck, Collection<Councillor> councillorsPool, Balcony kingbalcony, Collection<Region> regions, NobilityTrack nobilityTrack, King king) {
    	this.politicDeck=deck;
    	this.councillorsPool=councillorsPool;
    	this.kingBalcony=kingbalcony;
    	this.regions=regions;
    	this.nobilityTrack=nobilityTrack;
    	this.king=king;
    }

	public Player getCurrentPlayer() {
		// TODO
		return null;
	}

	public Region getRegion(String regionName) {
		// TODO
		return null;
	}

	public Balcony getKingBalcony() {
		return this.kingBalcony;
	}

	public Collection<Councillor> getCouncillorsPool() {
		return this.councillorsPool;
	}

	public King getKing() {
		return this.king;
	}

	public City getCity(String cityName) {
		// TODO
		return null;
	}

	public PoliticDeck getPoliticDeck() {
		// TODO
		return null;
	}

}