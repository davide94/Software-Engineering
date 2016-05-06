package it.polimi.ingsw.cg26.model.creator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * 
 */
public class XMLAdapter implements DOMParserInterface {

    /**
     * Default constructor
     */
    public XMLAdapter() {
    }

    /**
     *
     */
    public Boolean validate(String file, String schema) {
        // TODO
        return true;
    }

    /**
     * @param
     * @return Items
     */
    public Document parse(String s) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(s);
    }

}