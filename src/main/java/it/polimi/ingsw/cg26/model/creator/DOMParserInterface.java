package it.polimi.ingsw.cg26.model.creator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * 
 */
public interface DOMParserInterface {

    /**
     *
     */
    Boolean validate(String file, String schema);

    /**
     *
     */
    Document parse(String s) throws IOException, SAXException, ParserConfigurationException;

}