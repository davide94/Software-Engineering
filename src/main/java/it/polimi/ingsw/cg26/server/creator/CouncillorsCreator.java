package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.Councillor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import org.w3c.dom.Node;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class CouncillorsCreator {

    private CouncillorsCreator() {
        // Noting to do here
    }

    protected static List<Councillor> createCouncillors(Node root) {
        if (root == null)
            throw new NullPointerException();

        Node politicRoot = Creator.getNode(root, "politic");
        List<Councillor> councillors = new LinkedList<>();
        for (Node node: Creator.getNodes(politicRoot, "color")) {
            String colorString = Creator.getAttribute(node, "name");
            int councillorsNumber = 0;
            String councillorsNumberString = Creator.getAttribute(node, "councillors");
            if (!councillorsNumberString.isEmpty())
                councillorsNumber = Integer.parseInt(councillorsNumberString);
            PoliticColor color = new PoliticColor(colorString);
            for ( ; councillorsNumber > 0; councillorsNumber--) {
                Councillor councillor = Councillor.createCouncillor(color);
                councillors.add(councillor);
            }

            /*Councillor councillor = Councillor.createCouncillor(color);
            councillors.addAll(Collections.nCopies(councillorsNumber, councillor));*/
        }
        Collections.shuffle(councillors);
        return councillors;
    }
}
