package it.polimi.ingsw.cg26.common.commands.market;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

public class SellPoliticCardCommand extends SellCommand {

	private static final long serialVersionUID = -5722603893600408024L;
	
	/**
	 * The card to sell
	 */
	private final PoliticCardDTO politicCard;
	
	/**
	 * Constructs a command for sell a Politic Card
	 * @param price the price to set to the Politic Card
	 * @param politicCard the card to sell
	 * @throws IllegalArgumentException if price is less than 1
	 * @throws NullPointerException if the politicCard is null
	 */
	public SellPoliticCardCommand(int price, PoliticCardDTO politicCard) {
		super(price);
		if(politicCard == null)
			throw new NullPointerException();
		this.politicCard = politicCard;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @return the politicCard
	 */
	public PoliticCardDTO getPoliticCard() {
		return politicCard;
	}

}
