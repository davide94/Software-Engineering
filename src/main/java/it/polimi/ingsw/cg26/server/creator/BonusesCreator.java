package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.bonus.*;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
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

    protected static List<RewardTile> createBonuses(Node root, PoliticDeck politicDeck) {
        if (root == null || politicDeck == null)
            throw new NullPointerException();
        List<RewardTile> bonuses = new LinkedList<>();
        for (Node node: Creator.getNodes(root, "bonus"))
            bonuses.add(createBonus(node, politicDeck));
        return bonuses;
    }

    protected static RewardTile createBonus(Node root, PoliticDeck politicDeck) {
        List<Bonus> bonuses = new LinkedList<>();
        if (Creator.hasAttribute(root, "draw"))
            bonuses.add(new CardBonus(Integer.parseInt(Creator.getAttribute(root, "draw")), politicDeck));
        if (Creator.hasAttribute(root, "earn"))
            bonuses.add(new CoinBonus(Integer.parseInt(Creator.getAttribute(root, "earn"))));
        if (Creator.hasAttribute(root, "assistants"))
            bonuses.add(new AssistantBonus(Integer.parseInt(Creator.getAttribute(root, "assistants"))));
        if (Creator.hasAttribute(root, "nobility"))
            bonuses.add(new NobilityBonus(Integer.parseInt(Creator.getAttribute(root, "nobility"))));
        if (Creator.hasAttribute(root, "victory"))
            bonuses.add(new VictoryBonus(Integer.parseInt(Creator.getAttribute(root, "victory"))));
        if (Creator.hasAttribute(root, "action"))
            bonuses.add(new MainActionBonus(Integer.parseInt(Creator.getAttribute(root, "action"))));
        return new RewardTile(bonuses);
    }
}
