package it.polimi.ingsw.cg26.actions.market;

import java.util.Collection;

import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.player.Player;

public class SellBPT extends Sell {

	private Collection<String> cities;
	
	public SellBPT(String token, int price, Collection<String> cities) {
		super(token, price);
		this.cities = cities;
	}

	@Override
	public void apply(GameBoard gameBoard, Player currentPlayer) {

	}

}
