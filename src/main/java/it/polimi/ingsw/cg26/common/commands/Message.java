package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class Message implements Command {

    private static final long serialVersionUID = 9144748620597259816L;

    private String sender;

    private String body;

    public Message(String sender, String body) {
        this.sender = sender;
        this.body = body;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getSender() {
        return sender;
    }

    public String getBody() {
        return body;
    }
}
