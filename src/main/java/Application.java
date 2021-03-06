import client.HttpClient;
import input.parser.Parser;
import input.parser.helper.GetRequestParser;
import input.parser.helper.HelpRequestParser;
import input.parser.helper.PostRequestParser;
import input.parser.redirect.Redirector;
import message.HTTPResponse;
import message.RequestMessage;
import output.HelpPresenter;
import output.Presenter;

import static input.ArgsCollector.getArguments;

public class Application {

    private final Parser parser = new Parser(new HelpRequestParser(), new GetRequestParser(), new PostRequestParser());
    private final Presenter presenter = new Presenter(new HelpPresenter());
    private final HttpClient httpClient = new HttpClient();
    private final Redirector redirector = new Redirector();
    private String[] args;

    private RequestMessage request;

    public void start() {
        while(true) {
            try {
                setArgs();
                handleRequest();
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }

    private void setArgs() {
        this.args = getArguments();
    }

    private void handleRequest() {
        getHTTPRequestLine();
        sendRequestToHTTPClientIfRequestOrToPresenterIfHelp();
    }

    private void getHTTPRequestLine() {
        request = parser.getRequest(args);
    }

    private void sendRequestToHTTPClientIfRequestOrToPresenterIfHelp() {
        if(request.isHelpRequest()) {
            presenter.printHelpRequest(request);
        } else {
            HTTPResponse response = httpClient.makeRequest(request);
            presenter.print(response);
            attemptRedirectIfNeeded(response);
        }
    }

    private void attemptRedirectIfNeeded(HTTPResponse response) {
        if(redirector.redirectNecessary(response)){
            System.out.println("\n\n***Redirecting***\n\n");
            String newURL = redirector.getRedirectURL(response);
            setRedirectArg(newURL, response);
            handleRequest();
        }
    }

    private void setRedirectArg(String newURL, HTTPResponse response) {
        args[response.getURLIndex()+1] = newURL;
    }
}
