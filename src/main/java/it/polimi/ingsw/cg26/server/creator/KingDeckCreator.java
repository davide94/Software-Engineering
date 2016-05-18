package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.cards.KingDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import org.w3c.dom.Node;

import java.util.List;

/**
 *
 */
public class KingDeckCreator {

    private KingDeckCreator() {
        // Nothing to do here
    }

    protected static KingDeck createKingDeck(Node root, PoliticDeck politicDeck) {
        Node bonusesRoot = Creator.getNode(root, "kingDeck");
        List<List<Bonus>> bonuses = BonusesCreator.createBonuses(bonusesRoot, politicDeck);
        return new KingDeck(bonuses);
    }

}
