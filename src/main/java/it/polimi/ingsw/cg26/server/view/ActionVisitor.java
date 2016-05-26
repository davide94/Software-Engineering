package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.common.commands.AcquireCommand;
import it.polimi.ingsw.cg26.common.commands.AdditionalMainActionCommand;
import it.polimi.ingsw.cg26.common.commands.BuildCommand;
import it.polimi.ingsw.cg26.common.commands.BuildKingCommand;
import it.polimi.ingsw.cg26.common.commands.ChangeBPTCommand;
import it.polimi.ingsw.cg26.common.commands.ElectAsMainActionCommand;
import it.polimi.ingsw.cg26.common.commands.ElectAsQuickActionCommand;
import it.polimi.ingsw.cg26.common.commands.EngageAssistantCommand;
import it.polimi.ingsw.cg26.common.commands.Staccah;
import it.polimi.ingsw.cg26.common.visitor.Visitor;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.actions.main.Acquire;
import it.polimi.ingsw.cg26.server.actions.main.Build;
import it.polimi.ingsw.cg26.server.actions.main.BuildKing;
import it.polimi.ingsw.cg26.server.actions.main.ElectAsMainAction;
import it.polimi.ingsw.cg26.server.actions.quick.AdditionalMainAction;
import it.polimi.ingsw.cg26.server.actions.quick.ChangeBPT;
import it.polimi.ingsw.cg26.server.actions.quick.ElectAsQuickAction;
import it.polimi.ingsw.cg26.server.actions.quick.EngageAssistant;

public class ActionVisitor implements Visitor {

	private final ServerSocketView view;
	private final long token;

	public ActionVisitor(ServerSocketView view, long token) {
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
	public void visit(Staccah staccah) {
		

	}

}
