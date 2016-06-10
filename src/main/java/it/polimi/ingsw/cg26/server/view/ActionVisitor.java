package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.common.commands.*;
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
}
