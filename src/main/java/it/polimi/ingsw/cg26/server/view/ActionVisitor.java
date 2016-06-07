package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.common.commands.*;
import it.polimi.ingsw.cg26.common.visitor.Visitor;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.actions.main.Acquire;
import it.polimi.ingsw.cg26.server.actions.main.Build;
import it.polimi.ingsw.cg26.server.actions.main.BuildKing;
import it.polimi.ingsw.cg26.server.actions.main.ElectAsMainAction;
import it.polimi.ingsw.cg26.server.actions.quick.*;

public class ActionVisitor implements Visitor {

	private final View view;

	public ActionVisitor(View view) {
		this.view = view;
	}
	
	@Override
	public void visit(AcquireCommand acquireCommand, long token) {
		Action action = new Acquire(acquireCommand.getRegion(), acquireCommand.getCards(), acquireCommand.getPosition(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(AdditionalMainActionCommand additionalMainActionCommand, long token) {
		Action action = new AdditionalMainAction(token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(BuildCommand buildCommand, long token) {
		Action action = new Build(buildCommand.getCity(), buildCommand.getTile(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(BuildKingCommand buildKingCommand, long token) {
		Action action = new BuildKing(buildKingCommand.getCity(), buildKingCommand.getCards(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(ChangeBPTCommand changeBPTCommand, long token) {
		Action action = new ChangeBPT(changeBPTCommand.getRegion(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(ElectAsMainActionCommand electAsMainActionCommmand, long token) {
		Action action = new ElectAsMainAction(electAsMainActionCommmand.getRegion(), electAsMainActionCommmand.getCouncillor(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(ElectAsQuickActionCommand electAsQuickActionCommmand, long token) {
		Action action = new ElectAsQuickAction(electAsQuickActionCommmand.getRegion(), electAsQuickActionCommmand.getCouncillor(), token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(EngageAssistantCommand engageAssistantCommand, long token) {
		Action action = new EngageAssistant(token);
		this.view.notifyObservers(action);
	}

	@Override
	public void visit(Staccah staccah, long token) {
		

	}

	@Override
	public void visit(FoldQuickActionCommand foldQuickActionCommand, long token) {
		Action action = new FoldQuickAction(token);
		this.view.notifyObservers(action);
	}

}
