package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.board.NobilityTrack;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
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
        if (root == null || politicDeck == null)
            throw new NullPointerException();

        Node nobilityTrackRoot = Creator.getNode(root, "nobilitytrack");

        int len = Integer.parseInt(Creator.getAttribute(nobilityTrackRoot, "len"));
        List<RewardTile> bonuses = new ArrayList<>(Collections.nCopies(len, new RewardTile(new LinkedList<>())));

        for (Node node: Creator.getNodes(nobilityTrackRoot, "bonus")) {
            int position = Integer.parseInt(Creator.getAttribute(node, "position"));
            bonuses.set(position, BonusesCreator.createBonus(node, politicDeck));
        }
        NobilityCell last = NobilityCell.createNobilityCell(len - 1, null, bonuses.get(len - 1));
        for (int i = len - 2; i >= 0; i--) {
            RewardTile bonus = bonuses.get(i);
            last = NobilityCell.createNobilityCell(i, last, bonus);
        }
        return NobilityTrack.createNobilityTrack(last);
    }
}
