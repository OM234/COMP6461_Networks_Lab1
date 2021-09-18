import input.Parser;
import message.RequestMessage;
import output.Presenter;

public class Application {

    private Parser parser;
    private final Presenter presenter;
    private RequestMessage request;

    public Application(Parser parser, Presenter presenter) {
        this.parser = parser;
        this.presenter = presenter;
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
        }
    }
}
