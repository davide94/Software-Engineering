package it.polimi.ingsw.cg26.model.creator;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * 
 */
public class XMLAdapter implements ConfigurationInterface {

    /**
     * Default constructor
     */
    public XMLAdapter() {
    }



    /**
     * @param
     * @return Items
     */
    public void parseConfigurationFile(String s) throws IOException, SAXException {
        // TODO implement here
        DOMParser parser = new DOMParser();
        parser.parse(s);
    }

}