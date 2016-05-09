package it.polimi.ingsw.cg26.creator;

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
    Document parse(String file, String schema) throws IOException, SAXException, ParserConfigurationException;

}