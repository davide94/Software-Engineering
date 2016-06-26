package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 */
public class Balcony {

	private final int capacity;

	private Queue<Councillor> councillors;

	/**
	 * Create a Balcony
	 * @param capacity of the balcony
	 * @param councillors that are located on the balcony
	 * @throws IllegalArgumentException if the capacity is less than 1
     * @throws NullPointerException if councillors is null
	 */
	private Balcony(int capacity, Queue<Councillor> councillors) {
		if (councillors == null)
			throw new NullPointerException();
		if (capacity < 1)
			throw new IllegalArgumentException();
		this.capacity = capacity;
		this.councillors = councillors;
	}

	/**
	 * Create a Balcony
	 * @param capacity of the balcony 
	 * @return a new empty balcony
	 */
	public static Balcony createBalcony(int capacity) {
		return new Balcony(capacity, new LinkedList<>());
	}

	/**
	 * This method get the state of the balcony
	 * @return balcony DTO that is a copy of the balcony and the councillors on the balcony
	 */
	public BalconyDTO getState() {
		LinkedList<CouncillorDTO> councillorsState = new LinkedList<>();
		for (Councillor c: councillors)
			councillorsState.add(c.getState());
		return new BalconyDTO(councillorsState);
	}

    /**
     * elect a councillor on the balcony
     * @param c is the councillor you want to elect
     * @return the councillor that is dropped or null if the balcony is not full
     * @throws NullPointerException if someone elect a null councillor
     */
    public Councillor elect(Councillor c) {
    	if (c == null)
    		throw new NullPointerException();
		this.councillors.add(c);
    	if (this.councillors.size() > capacity)
			return this.councillors.poll();
		return null;
    }

    /**
     * A comparison with a collection of cards that player is using and the color of the councillors on the balcony 
     * @param requiredCards is a collection of Politic Cards DTO
     * @return true if you pass a collection of cards that is compatible with the councillors on the balcony
     * @throws IllegalArgumentException if you pass a collection of cards that is empty
     */
	public boolean checkPoliticCards(Collection<PoliticCardDTO> requiredCards) {
		if (requiredCards.isEmpty())
    		throw new IllegalArgumentException();
        List<PoliticCardDTO> cardsList = new LinkedList<>(requiredCards);
        PoliticCardDTO multicolor = new PoliticCardDTO(new PoliticColorDTO("multicolor"), 0, "");
        while(true)
            if (!(cardsList.remove(multicolor)))
                break;
		LinkedList<Councillor> councillors = new LinkedList<>(this.councillors);
		for (PoliticCardDTO card: cardsList) {
			Councillor c = null;
			for (Councillor councillor: councillors) {
				if (councillor.getColor().getState().equals(card.getColor())) {
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

	/**
	 * This method get the councillors on the balcony
	 * @return the collection of councillors on the balcony
	 */
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