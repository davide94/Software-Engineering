package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.model.cards.PoliticColor;

import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;



/**
 *
 */
public class Balcony {

	private final int capacity;

	private Queue<Councillor> councillors;

    public Balcony(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException();
		this.capacity = capacity;
		this.councillors = new LinkedList<>();
	}


    /**
     * @param
     * @return
     */
    public Councillor elect(Councillor c) {
    	if (c == null)
    		throw new NullPointerException();
		this.councillors.add(c);
    	if (this.councillors.size() > capacity)
			return this.councillors.poll();
		return null;
    }

    public boolean checkPoliticCardsCouncillors(List<PoliticCard> politicCards){
    	if (politicCards == null)
    		throw new NullPointerException();
    	for(Councillor iterCouncillor : councillors) {
			for(PoliticCard iterPoliticCard : politicCards) {
				if(iterCouncillor.getColor().equals(iterPoliticCard.getColor()))
					politicCards.remove(iterPoliticCard);
				if(politicCards.isEmpty())
					return true;

			}
		}
		return false;
    }

	public boolean checkPoliticCards(Collection<PoliticColor> politicCards) {
		// TODO
		return true;
	}

	@Override
	public String toString() {
		return "Balcony{" +
				"councillors=" + councillors +
				'}';
	}
}