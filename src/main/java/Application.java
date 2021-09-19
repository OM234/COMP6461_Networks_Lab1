import client.HttpClient;
import input.Parser;
import message.RequestMessage;
import output.Presenter;

public class Application {

    private final Parser parser;
    private final Presenter presenter;
    private final HttpClient httpClient;

    private RequestMessage request;

    public Application(Parser parser, Presenter presenter, HttpClient httpClient) {
        this.parser = parser;
        this.presenter = presenter;
        this.httpClient = httpClient;
    }

    public void start() {
        getHTTPRequestLine();
        sendRequestToHTTPClientIfRequestOrToPresenterIfHelp();
    }

    private void getHTTPRequestLine() {
        request = parser.getRequest();
    }

    private void sendRequestToHTTPClientIfRequestOrToPresenterIfHelp() {
        if(request.isHelpRequest()) {
            presenter.printHelpRequest(request);
        } else {
            httpClient.makeRequest(request);
        }
    }
}
