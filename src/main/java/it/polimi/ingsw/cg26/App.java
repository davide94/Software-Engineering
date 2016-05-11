package it.polimi.ingsw.cg26;

import it.polimi.ingsw.cg26.controller.Controller;
import it.polimi.ingsw.cg26.creator.Creator;
import it.polimi.ingsw.cg26.exceptions.BadInputFileException;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Creator creator = new Creator();
        try {

            System.out.print("How many players? ");
            Scanner scanner = new Scanner(System.in);
            int playersNumber = scanner.nextInt();

            creator.newGame("src/main/resources/config.xml", playersNumber);

        } catch (IOException e) {
            System.out.println("Wrong file path");
        } catch (BadInputFileException e) {
            System.out.println("Config file is invalid");
        } catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
