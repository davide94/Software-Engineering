package it.polimi.ingsw.cg26;

import it.polimi.ingsw.cg26.exceptions.BadInputFileException;
import it.polimi.ingsw.cg26.model.creator.ModelCreator;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ModelCreator creator = new ModelCreator();
        try {

            Instant before = Instant.now();

            creator.newGame("src/main/config.xml", 1);

            Instant after = Instant.now();
            long delta = Duration.between(before, after).toMillis();
            System.out.println("Game created in " + delta + " ms");

        } catch (IOException e) {
            System.out.printf("Wrong file path");
        } catch (BadInputFileException e) {
            System.out.println("Config file is invalid");
        }
    }
}
