package it.polimi.ingsw.cg26.client.commands;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.actions.main.ElectAsMainAction;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;

/**
 *
 */
public class ElectAsMainActionCommand extends Command {

    private static final long serialVersionUID = -8264148065088075874L;

    private final String region;

    private final PoliticColor councillorColor;

    public ElectAsMainActionCommand(String region, PoliticColor councillorColor) {
        if (councillorColor == null)
            throw new NullPointerException();
        this.region = region;
        this.councillorColor = councillorColor;
    }

    public String getRegion() {
        return region;
    }

    public PoliticColor getCouncillorColor() {
        return councillorColor;
    }

    @Override
    public Action generateAction() {
        return new ElectAsMainAction(this.region, this.councillorColor);
    }
}
