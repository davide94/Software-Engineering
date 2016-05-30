package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;

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

	public boolean checkPoliticCards(Collection<PoliticCardDTO> requiredCards) {
		LinkedList<Councillor> councillors = new LinkedList<>(this.councillors);
		for (PoliticCardDTO card: requiredCards) {
			Councillor c = null;
			for (Councillor councillor: councillors) {
				if (councillor.getColor().getState().equals(card.getColor()) || card.getColor().equals(new PoliticColorDTO("multicolor"))) {
					c = councillor;
					break;
				}
			}
			if (c == null)
				return false;
			councillors.remove(c);
		}
		return true;
	}

	@Override
	public String toString() {
		return "Balcony{" +
				"councillors=" + councillors +
				'}';
	}

	public Queue<Councillor> getCouncillors() {
		return councillors;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacity;
		result = prime * result + ((councillors == null) ? 0 : councillors.hashCode());
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
		Balcony other = (Balcony) obj;
		if (capacity != other.capacity)
			return false;
		if (councillors == null) {
			if (other.councillors != null)
				return false;
		} else if (!councillors.equals(other.councillors))
			return false;
		return true;
	}
	
	

		
	
	
}