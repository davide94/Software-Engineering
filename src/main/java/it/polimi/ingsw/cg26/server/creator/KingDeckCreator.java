package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.cards.KingDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.w3c.dom.Node;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class KingDeckCreator {

    private KingDeckCreator() {
        // Nothing to do here
    }

    protected static KingDeck createKingDeck(Node root, PoliticDeck politicDeck) {
        if (root == null || politicDeck == null)
            throw new NullPointerException();
        Node bonusesRoot = Creator.getNode(root, "kingDeck");
        List<Bonus> bonuses = BonusesCreator.createBonuses(bonusesRoot, politicDeck);
        List<RewardTile> tiles = new LinkedList<>();
        for(Bonus b : bonuses)
        	tiles.add(new RewardTile(b));
        return new KingDeck(tiles);
    }

}
