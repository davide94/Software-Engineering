package it.polimi.ingsw.cg26.creator;

import it.polimi.ingsw.cg26.controller.Controller;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;

/**
 * The Creator class is used to create the initial game structure.
 * To build all the classes required to start a game use newGame(String file, int playersNumber).
 */
public class Creator {

    /**
     * Default constructor
     */
    private Creator() {
        // nothing to do here
    }

    /**
     * Creates the initial game structure.
     * @param file is the path+name of the configuration file
     */
    public static Controller createGame(String file) {

        Instant before = Instant.now();

        DOMParserInterface parserInterface = new XMLAdapter();

        Document document = parserInterface.parse(file, "src/main/resources/schema.xsd");

        GameBoard gameBoard = BoardCreator.createBoard(document.getFirstChild());

        Controller controller = new Controller(gameBoard);

        //System.out.println("Game created in " + Duration.between(before, Instant.now()).toMillis() + " ms");

        return controller;

    }

    protected static Boolean hasChild(String name, Node root) {
        NodeList l = root.getChildNodes();
        for (int i = 0; i < l.getLength(); i++) {
            Node node = l.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    protected static LinkedList<Node> getNodes(Node root, String name) {
        LinkedList<Node> nodes = new LinkedList<>();
        NodeList l = root.getChildNodes();
        for (int i = 0; i < l.getLength(); i++) {
            Node node = l.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase(name)) {
                nodes.add(node);
            }
        }
        return nodes;
    }

    protected static Node getNode(Node root, String name) {
        return getNodes(root, name).poll();
    }

    protected static Boolean hasAttribute(Node root, String name) {
        NamedNodeMap attributes = root.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            if (attr.getNodeName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    protected static String getAttribute(Node root, String name) {
        NamedNodeMap attributes = root.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++ ) {
            Node attr = attributes.item(i);
            if (attr.getNodeName().equalsIgnoreCase(name))
                return attr.getNodeValue();
        }
        return "";
    }

}