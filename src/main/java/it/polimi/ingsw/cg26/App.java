package it.polimi.ingsw.cg26;

import it.polimi.ingsw.cg26.controller.Controller;
import it.polimi.ingsw.cg26.creator.Creator;
import it.polimi.ingsw.cg26.exceptions.BadInputFileException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

/**
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Creator creator = new Creator();
        try {
            Controller controller = creator.newGame("src/main/resources/config.xml");
            controller.registerPlayer();
        } catch (IOException e) {
            Logger.log(Logger.ALL, "Wrong file path");
        } catch (BadInputFileException e) {
            Logger.log(Logger.ALL, "Config file is invalid");
        } catch (ParserConfigurationException e) {
			Logger.log(Logger.ERROR, e.getMessage());
		}
    }
}
