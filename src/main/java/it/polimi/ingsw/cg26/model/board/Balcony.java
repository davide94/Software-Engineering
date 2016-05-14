package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.cards.PoliticColor;

import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;

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

	public boolean checkPoliticCards(Collection<PoliticColor> politicCardsColors) {
		LinkedList<PoliticColor> cardsColors = new LinkedList<>(politicCardsColors);
		for (Councillor councillor: this.councillors) {
			PoliticColor c = null;
			for (PoliticColor cardColor: cardsColors) {
				if (councillor.getColor().equals(cardColor)) {
					c = cardColor;
					break;
				}
			}
			if (c == null)
				return false;
			cardsColors.remove(c);
		}
		return true;
	}

	@Override
	public String toString() {
		return "Balcony{" +
				"councillors=" + councillors +
				'}';
	}
}