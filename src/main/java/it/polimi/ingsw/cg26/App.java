package it.polimi.ingsw.cg26;

import it.polimi.ingsw.cg26.controller.Controller;
import it.polimi.ingsw.cg26.creator.Creator;
import it.polimi.ingsw.cg26.exceptions.ParserErrorException;

/**
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            Controller controller = Creator.createGame("src/main/resources/config.xml");
            controller.registerPlayer();
        } catch (ParserErrorException e) {
            System.out.println(e.getMessage());
        }
    }
}
