package it.polimi.ingsw.cg26;

import it.polimi.ingsw.cg26.controller.Controller;
import it.polimi.ingsw.cg26.creator.Creator;
import it.polimi.ingsw.cg26.exceptions.BadInputFileException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

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

            Instant before = Instant.now();

            Controller game = creator.newGame("src/main/config.xml", playersNumber);

            Instant after = Instant.now();
            long delta = Duration.between(before, after).toMillis();
            System.out.println("Game created in " + delta + " ms");

            game.start();


        } catch (IOException e) {
            System.out.printf("Wrong file path");
        } catch (BadInputFileException e) {
            System.out.println("Config file is invalid");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
