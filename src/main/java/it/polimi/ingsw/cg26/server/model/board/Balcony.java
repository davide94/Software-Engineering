package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.common.state.BalconyState;
import it.polimi.ingsw.cg26.common.state.CouncillorState;

import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;

/**
 *
 */
public class Balcony {

	private final int capacity;

	private Queue<Councillor> councillors;

	private Balcony(int capacity, Queue<Councillor> councillors) {
		if (councillors == null)
			throw new NullPointerException();
		if (capacity < 1)
			throw new IllegalArgumentException();
		this.capacity = capacity;
		this.councillors = councillors;
	}

	public static Balcony createBalcony(int capacity) {
		return new Balcony(capacity, new LinkedList<>());
	}

	public BalconyState getState() {
		LinkedList<CouncillorState> councillorsState = new LinkedList<>();
		for (Councillor c: councillors)
			councillorsState.add(c.getState());
		return new BalconyState(councillorsState);
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