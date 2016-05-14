package it.polimi.ingsw.cg26;

/**
 * Created by davide on 13/05/16.
 */
public class Logger {


    public final static int ALL = 0;
    public final static int INFO = 1;
    public final static int ERROR = 2;
    public final static int DEBUG = 3;


    private static int alertLevel = 2;

    public static void log(String msg) {
        System.out.println(msg);
    }

    public static void log(int level, String msg) {
        if (level < alertLevel)
            log(msg);
    }

    public static void setAlertLevel(int level) {
        alertLevel = level;
    }

}
