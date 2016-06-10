package it.polimi.ingsw.cg26.common.visitor;

import it.polimi.ingsw.cg26.common.commands.*;

public interface Visitor {

	public void visit(AcquireCommand acquireCommand);
	
	public void visit(AdditionalMainActionCommand additionalMainActionCommand);
	
	public void visit(BuildCommand buildCommand);
	
	public void visit(BuildKingCommand buildKingCommand);
	
	public void visit(ChangeBPTCommand changeBPTCommand);
	
	public void visit(ElectAsMainActionCommand electAsMainActionCommmand);
	
	public void visit(ElectAsQuickActionCommand electAsQuickActionCommmand);
	
	public void visit(EngageAssistantCommand engageAssistantCommand);

	public void visit(Staccah staccah);

	public void visit(FoldQuickActionCommand foldQuickActionCommand);
	
	public void visit(ChooseBPTCommand chooseBPTCommand);
	
	public void visit(ChooseCityCommand chooseCityCommand);
}
