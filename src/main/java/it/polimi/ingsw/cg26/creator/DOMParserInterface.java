package it.polimi.ingsw.cg26.creator;

import org.w3c.dom.Document;

/**
 * 
 */
@FunctionalInterface
public interface DOMParserInterface {

    /**
     *
     */
    Document parse(String file, String schema);

}