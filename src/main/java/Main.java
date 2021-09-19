import client.HttpClient;
import input.helper.GetRequestParser;
import input.helper.HelpRequestParser;
import input.Parser;
import input.helper.PostRequestParser;
import output.HelpPresenter;
import output.Presenter;

import static util.InitialArgsCorrector.addInDoubleQuotes;
import static util.InitialArgsCorrector.concatenateArgs;

public class Main {

    private static Application application;

    public static void main(String[] args) {
        args = correctArguments(args);
        initializeObjects(args);
        startApplication();
    }

    private static String[] correctArguments(String[] args) {
        args = concatenateArgs(args);
        addInDoubleQuotes(args);
        return args;
    }

    private static void initializeObjects(String[] args) {
        Parser parser = new Parser(args, new HelpRequestParser(args), new GetRequestParser(args), new PostRequestParser(args));
        HelpPresenter helpPresenter = new HelpPresenter();
        Presenter presenter = new Presenter(helpPresenter);
        HttpClient httpClient = new HttpClient();
        application = new Application(parser, presenter, httpClient);
    }

    private static void startApplication() {
        application.start();
    }
}
