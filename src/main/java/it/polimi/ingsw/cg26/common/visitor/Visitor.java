package it.polimi.ingsw.cg26.common.visitor;

import it.polimi.ingsw.cg26.common.commands.*;
import it.polimi.ingsw.cg26.common.commands.market.*;

public interface Visitor {

	public void visit(AcquireCommand acquireCommand);
	
	public void visit(AdditionalMainActionCommand additionalMainActionCommand);
	
	public void visit(BuildCommand buildCommand);
	
	public void visit(BuildKingCommand buildKingCommand);
	
	public void visit(ChangeBPTCommand changeBPTCommand);
	
	public void visit(ElectAsMainActionCommand electAsMainActionCommmand);
	
	public void visit(ElectAsQuickActionCommand electAsQuickActionCommmand);
	
	public void visit(EngageAssistantCommand engageAssistantCommand);

	public void visit(StaccahCommand staccahCommand);

	public void visit(FoldQuickActionCommand foldQuickActionCommand);
	
	public void visit(ChooseBPTCommand chooseBPTCommand);
	
	public void visit(ChooseCityCommand chooseCityCommand);
	
	public void visit(ChoosePlayerBPTCommand choosePlayerBPTCommand);
	
	public void visit(ElectKingAsMainActionCommand electKingAsMainActionCommand);
	
	public void visit(ElectKingAsQuickActionCommand electKingAsQuickActionCommand);
	
	public void visit(BuyCommand buyCommand);
	
	public void visit(FoldBuyCommand foldBuyCommand);
	
	public void visit(FoldSellCommand foldSellCommand);
	
	public void visit(SellAssistantCommand sellAssistantCommand);
	
	public void visit(SellBPTCommand sellBPTCommand);

	public void visit(SellPoliticCardCommand sellPoliticCardCommand);

	public void visit(Message message);
}
