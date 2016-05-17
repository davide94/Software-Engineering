package it.polimi.ingsw.cg26.creator;

import it.polimi.ingsw.cg26.model.board.NobilityCell;
import it.polimi.ingsw.cg26.model.board.NobilityTrack;
import it.polimi.ingsw.cg26.model.bonus.Bonus;
import it.polimi.ingsw.cg26.model.cards.PoliticDeck;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class NobilityTrackCreator {

    private NobilityTrackCreator() {
        // Nothing to do here
    }

    protected static NobilityTrack createNobilityTrack(Node root, PoliticDeck politicDeck) {
        Node nobilityTrackRoot = Creator.getNode(root, "nobilitytrack");

        int len = Integer.parseInt(Creator.getAttribute(nobilityTrackRoot, "len"));
        List<List<Bonus>> bonuses = new ArrayList<>(Collections.nCopies(len, new LinkedList<>()));

        for (Node node: Creator.getNodes(nobilityTrackRoot, "bonus")) {
            int position = Integer.parseInt(Creator.getAttribute(node, "position")) - 1;
            bonuses.set(position, BonusesCreator.createBonus(node, politicDeck));
        }
        NobilityCell last = new NobilityCell(len - 1, null, bonuses.get(len - 1));
        for (int i = len - 2; i >= 0; i--) {
            List<Bonus> bonus = bonuses.get(i);
            last = new NobilityCell(i, last, bonus);
        }
        return new NobilityTrack(last);
    }
}
