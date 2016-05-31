package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.common.dto.AssistantDTO;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class BuyAssistant extends Buy {

	private AssistantDTO assistantDTO;
	
	/**
	 * Simple constructor of the action, does nothing
	 */
	public BuyAssistant(long token, AssistantDTO assistantDTO) {
		super(token);
		if(assistantDTO == null)
			throw new NullPointerException();
		this.assistantDTO = assistantDTO;
	}

	@Override
	public void apply(GameBoard gameBoard) {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		Sellable assistant = gameBoard.getMarket().getRealSellable(assistantDTO);
		Sellable buyedSellable = super.buy(gameBoard, currentPlayer, assistant);
		buyedSellable.setPrice(0);
		buyedSellable.giveToNewOwner(currentPlayer);
	}

}
