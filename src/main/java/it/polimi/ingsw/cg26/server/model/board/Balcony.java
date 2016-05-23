package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.state.PoliticCardState;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
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

	public boolean checkPoliticCards(Collection<PoliticCardState> politicCards) {
		LinkedList<PoliticCardState> cards = new LinkedList<>(politicCards);
		for (Councillor councillor: this.councillors) {
			PoliticCardState c = null;
			for (PoliticCardState card: cards) {
				if (councillor.getColor().equals(card.getColor())) {
					c = card;
					break;
				}
			}
			if (c == null)
				return false;
			cards.remove(c);
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