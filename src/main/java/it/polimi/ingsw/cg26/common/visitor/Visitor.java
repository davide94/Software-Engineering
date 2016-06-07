package it.polimi.ingsw.cg26.common.visitor;

import it.polimi.ingsw.cg26.common.commands.*;

public interface Visitor {

	public void visit(AcquireCommand acquireCommand, long token);
	
	public void visit(AdditionalMainActionCommand additionalMainActionCommand, long token);
	
	public void visit(BuildCommand buildCommand, long token);
	
	public void visit(BuildKingCommand buildKingCommand, long token);
	
	public void visit(ChangeBPTCommand changeBPTCommand, long token);
	
	public void visit(ElectAsMainActionCommand electAsMainActionCommmand, long token);
	
	public void visit(ElectAsQuickActionCommand electAsQuickActionCommmand, long token);
	
	public void visit(EngageAssistantCommand engageAssistantCommand, long token);

	public void visit(Staccah staccah, long token);

	public void visit(FoldQuickActionCommand foldQuickActionCommand, long token);
}
