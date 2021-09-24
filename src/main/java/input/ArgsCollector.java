package input;

import com.sun.security.jgss.GSSUtil;

import java.util.Scanner;

import static util.InitialArgsCorrector.addInDoubleQuotes;
import static util.InitialArgsCorrector.concatenateArgs;

public class ArgsCollector {

    private static boolean firstRun = true;
    private static  String[] args;
    private static Scanner in = new Scanner(System.in);

    public static String[] getArguments() {
        displayWelcome();
        collectArgs();
        correctArgs();
        return args;
    }

    private static void displayWelcome() {
        if(firstRun) {
            diaplayInitialMessage();
        }
        System.out.print("> ");
    }

    private static void diaplayInitialMessage() {
        System.out.println("http client");
        firstRun = false;
    }

    private static void collectArgs() {
        try{
            getInput();
        } catch (Exception e) {
            handleInputException(e);
        }
    }

    private static void getInput() {
        String input = in.nextLine();
        args = input.split(" ");
    }

    private static void handleInputException(Exception e) {
        System.out.println("Problem with input");
        e.printStackTrace();
        throw new RuntimeException();
    }

    private static void correctArgs() {
        args = concatenateArgs(args);
        //addInDoubleQuotes(args);
    }
}
