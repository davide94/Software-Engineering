package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

import java.util.List;

/**
 *
 */
public class AcquireCommand implements Command {

    private static final long serialVersionUID = 4122987227120035880L;

    /**
     * The region to use in the command
     */
    private final RegionDTO region;

    /**
     * The list of Cards to use in the command
     */
    private final List<PoliticCardDTO> cards;

    /**
     * The position of the BPT
     */
    private final int position;

    /**
     * Constructs a Command to acquire a BPT
     * @param region the region in which is located the BPT to buy
     * @param cards the cards the player wants to use
     * @param position the position of the BPT
     * @throws NullPointerException if one or more arguments are null
     */
    public AcquireCommand(RegionDTO region, List<PoliticCardDTO> cards, int position) {
    	if(region == null || cards == null)
    		throw new NullPointerException();
        this.region = region;
        this.cards = cards;
        this.position = position;
    }

    /**
     * Get the regionDTO
     * @return the RegionDTO of the command
     */
    public RegionDTO getRegion() {
        return region;
    }

    /**
     * Get the cardsDTO
     * @return a list of PoliticCardDTO of the command
     */
    public List<PoliticCardDTO> getCards() {
        return cards;
    }

    /**
     * Get the position of the BPTDTO
     * @return the position of the BPTDTO of the command
     */
    public int getPosition() {
        return position;
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
