package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;

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

	public BalconyDTO getState() {
		LinkedList<CouncillorDTO> councillorsState = new LinkedList<>();
		for (Councillor c: councillors)
			councillorsState.add(c.getState());
		return new BalconyDTO(councillorsState);
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

	public boolean checkPoliticCards(Collection<PoliticCardDTO> politicCards) {
		LinkedList<PoliticCardDTO> cards = new LinkedList<>(politicCards);
		for (Councillor councillor: this.councillors) {
			PoliticCardDTO c = null;
			for (PoliticCardDTO card: cards) {
				if (councillor.getColor().getState().equals(card.getColor())) {
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