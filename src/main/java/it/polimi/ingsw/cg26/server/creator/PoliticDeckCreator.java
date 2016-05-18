package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import org.w3c.dom.Node;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 */
public class PoliticDeckCreator {

    private PoliticDeckCreator() {
        // Nothing to do here
    }

    protected static PoliticDeck createDeck(Node root) {
        Node politicRoot = Creator.getNode(root, "politic");

        LinkedList<PoliticCard> cards = new LinkedList<>();
        for (Node node: Creator.getNodes(politicRoot, "color")) {
            String colorString = Creator.getAttribute(node, "name");
            int cardsNumber = Integer.parseInt(Creator.getAttribute(node, "cards"));
            PoliticColor color = new PoliticColor(colorString);
            for ( ; cardsNumber > 0; cardsNumber--) {
                PoliticCard card = new PoliticCard(color);
                cards.add(card);
            }
        }
        Collections.shuffle(cards);

        return new PoliticDeck(cards);

    }

}
