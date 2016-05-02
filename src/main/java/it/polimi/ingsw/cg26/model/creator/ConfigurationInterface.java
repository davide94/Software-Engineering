package it.polimi.ingsw.cg26.model.creator;

import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * 
 */
public interface ConfigurationInterface {

    /**
     *
     */
    public void parseConfigurationFile(String s) throws IOException, SAXException;

}