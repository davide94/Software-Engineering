package it.polimi.ingsw.cg26.common.visitor;

@FunctionalInterface
public interface Visitable {

	public void accept(Visitor visitor);
}
