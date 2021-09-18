import input.Parser;
import message.RequestMessage;

public class Application {

    private Parser parser;

    public Application(Parser parser) {
        this.parser = parser;
    }

    public void makeRequest() {
        RequestMessage request = parser.getRequest();
    }
}
