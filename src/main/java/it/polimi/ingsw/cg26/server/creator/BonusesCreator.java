package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.bonus.*;
import org.w3c.dom.Node;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class BonusesCreator {

    private BonusesCreator() {
        // Nothing to do here
    }

    protected static List<Bonus> createBonuses(Node root, PoliticDeck politicDeck) {
        if (root == null || politicDeck == null)
            throw new NullPointerException();
        List<Bonus> bonuses = new LinkedList<>();
        for (Node node: Creator.getNodes(root, "bonus"))
            bonuses.add(createBonus(node, politicDeck));
        return bonuses;
    }

    protected static Bonus createBonus(Node root, PoliticDeck politicDeck) {
        Bonus bonuses = new EmptyBonus();
        if (Creator.hasAttribute(root, "draw"))
            bonuses = new CardBonus(bonuses, Integer.parseInt(Creator.getAttribute(root, "draw")), politicDeck);
        if (Creator.hasAttribute(root, "earn"))
            bonuses = new CoinBonus(bonuses, Integer.parseInt(Creator.getAttribute(root, "earn")));
        if (Creator.hasAttribute(root, "assistants"))
            bonuses = new AssistantBonus(bonuses, Integer.parseInt(Creator.getAttribute(root, "assistants")));
        if (Creator.hasAttribute(root, "nobility"))
            bonuses = new NobilityBonus(bonuses, Integer.parseInt(Creator.getAttribute(root, "nobility")));
        if (Creator.hasAttribute(root, "victory"))
            bonuses = new VictoryBonus(bonuses, Integer.parseInt(Creator.getAttribute(root, "victory")));
        if (Creator.hasAttribute(root, "action"))
            bonuses = new MainActionBonus(bonuses, Integer.parseInt(Creator.getAttribute(root, "action")));
        return bonuses;
    }
}
