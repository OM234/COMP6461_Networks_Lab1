import input.Parser;
import message.Message;

public class Application {

    private Parser parser;

    public Application(Parser parser) {
        this.parser = parser;
    }

    public void makeRequest() {
        Message request = parser.getRequest();
    }
}
