package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.common.commands.*;
import it.polimi.ingsw.cg26.common.commands.market.BuyCommand;
import it.polimi.ingsw.cg26.common.commands.market.FoldBuyCommand;
import it.polimi.ingsw.cg26.common.commands.market.FoldSellCommand;
import it.polimi.ingsw.cg26.common.commands.market.SellAssistantCommand;
import it.polimi.ingsw.cg26.common.commands.market.SellBPTCommand;
import it.polimi.ingsw.cg26.common.commands.market.SellPoliticCardCommand;
import it.polimi.ingsw.cg26.common.visitor.Visitor;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.actions.Staccah;
import it.polimi.ingsw.cg26.server.actions.answer.ChooseBPT;
import it.polimi.ingsw.cg26.server.actions.answer.ChooseCity;
import it.polimi.ingsw.cg26.server.actions.answer.ChoosePlayerBPT;
import it.polimi.ingsw.cg26.server.actions.main.Acquire;
import it.polimi.ingsw.cg26.server.actions.main.Build;
import it.polimi.ingsw.cg26.server.actions.main.BuildKing;
import it.polimi.ingsw.cg26.server.actions.main.ElectAsMainAction;
import it.polimi.ingsw.cg26.server.actions.main.ElectKingAsMainAction;
import it.polimi.ingsw.cg26.server.actions.market.Buy;
import it.polimi.ingsw.cg26.server.actions.market.FoldBuy;
import it.polimi.ingsw.cg26.server.actions.market.FoldSell;
import it.polimi.ingsw.cg26.server.actions.market.SellAssistant;
import it.polimi.ingsw.cg26.server.actions.market.SellBPT;
import it.polimi.ingsw.cg26.server.actions.market.SellPoliticCard;
import it.polimi.ingsw.cg26.server.actions.quick.*;

public class ActionVisitor implements Visitor {

	private final View view;

	private final long token;

	public ActionVisitor(View view, long token) {
		this.view = view;
		this.token = token;
	}
	
	@Override
	public void visit(AcquireCommand acquireCommand) {
		Action action = new Acquire(acquireCommand.getRegion(), acquireCommand.getCards(), acquireCommand.getPosition(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(AdditionalMainActionCommand additionalMainActionCommand) {
		Action action = new AdditionalMainAction(token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(BuildCommand buildCommand) {
		Action action = new Build(buildCommand.getCity(), buildCommand.getTile(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(BuildKingCommand buildKingCommand) {
		Action action = new BuildKing(buildKingCommand.getCity(), buildKingCommand.getCards(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(ChangeBPTCommand changeBPTCommand) {
		Action action = new ChangeBPT(changeBPTCommand.getRegion(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(ElectAsMainActionCommand electAsMainActionCommmand) {
		Action action = new ElectAsMainAction(electAsMainActionCommmand.getRegion(), electAsMainActionCommmand.getCouncillor(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(ElectAsQuickActionCommand electAsQuickActionCommmand) {
		Action action = new ElectAsQuickAction(electAsQuickActionCommmand.getRegion(), electAsQuickActionCommmand.getCouncillor(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(EngageAssistantCommand engageAssistantCommand) {
		Action action = new EngageAssistant(token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(StaccahCommand staccahCommand) {
		Action action = new Staccah(token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(FoldQuickActionCommand foldQuickActionCommand) {
		Action action = new FoldQuickAction(token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(ChooseBPTCommand chooseBPTCommand) {
		Action action = new ChooseBPT(chooseBPTCommand.getChosenRegion(), chooseBPTCommand.getChosenPosition(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(ChooseCityCommand chooseCityCommand) {
		Action action = new ChooseCity(chooseCityCommand.getChosenCities(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(ChoosePlayerBPTCommand choosePlayerBPTCommand) {
		Action action = new ChoosePlayerBPT(choosePlayerBPTCommand.getChosenBPT(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(ElectKingAsMainActionCommand electKingAsMainActionCommand) {
		Action action = new ElectKingAsMainAction(electKingAsMainActionCommand.getCouncillor(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(ElectKingAsQuickActionCommand electKingAsQuickActionCommand) {
		Action action = new ElectKingAsQuickAction(electKingAsQuickActionCommand.getCouncillor(), token);
		this.view.notifyObservers(action);
	}
	
	@Override
	public void visit(BuyCommand buyCommand) {
		Action action = new Buy(buyCommand.getSellable(), token);
		this.view.notifyObservers(action);
	}
	
	@Override
	public void visit(FoldBuyCommand foldBuyCommand) {
		Action action = new FoldBuy(token);
		this.view.notifyObservers(action);
	}
	
	@Override
	public void visit(FoldSellCommand foldSellCommand) {
		Action action = new FoldSell(token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(SellAssistantCommand sellAssistantCommand) {
		Action action = new SellAssistant(sellAssistantCommand.getPrice(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(SellBPTCommand sellBPTCommand) {
		Action action = new SellBPT(sellBPTCommand.getPrice(), sellBPTCommand.getTile(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(SellPoliticCardCommand sellPoliticCardCommand) {
		Action action = new SellPoliticCard(sellPoliticCardCommand.getPrice(), sellPoliticCardCommand.getPoliticCard(), token);
		this.view.notifyObservers(action);
	}
}
