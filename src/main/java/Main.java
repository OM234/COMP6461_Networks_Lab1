import client.HttpClient;
import input.helper.GetRequestParser;
import input.helper.HelpRequestParser;
import input.Parser;
import input.helper.PostRequestParser;
import output.HelpPresenter;
import output.Presenter;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        args = concatenateArgs(args);
        addInDoubleQuotes(args);

        Parser parser = new Parser(args, new HelpRequestParser(args), new GetRequestParser(args), new PostRequestParser(args));
        HelpPresenter helpPresenter = new HelpPresenter();
        Presenter presenter = new Presenter(helpPresenter);
        HttpClient httpClient = new HttpClient();
        Application application = new Application(parser, presenter, httpClient);
        application.start();
    }

    public static String[] concatenateArgs(String args[]) {
        List<String> newArgs = new ArrayList<>();
        int i = 0;
        while(i < args.length) {
            if(args[i].contains("'")) {
                String curr = args[i];
                i++;
                while(i < args.length && args[i].contains("'")) {
                    curr += " " + args[i];
                    i++;
                }
                newArgs.add(curr);
            } else {
                newArgs.add(args[i]);
                i++;
            }
        }
        String[] newArr = new String[newArgs.size()];
        return newArgs.toArray(newArr);
    }

    private static void addInDoubleQuotes(String[] args) {
        for(int i = 0; i < args.length; i++) {
            if(args[i].contains("{")) {
                int leftParanIndex = args[i].indexOf("{");
                int colonIndex = args[i].indexOf(":");

                String argWithQuotes = args[i].substring(0, leftParanIndex+1) + "\"";
                argWithQuotes += args[i].substring(leftParanIndex+1, colonIndex) + "\"";
                argWithQuotes += args[i].substring(colonIndex);

                args[i] = argWithQuotes;
            }
        }
    }
}
