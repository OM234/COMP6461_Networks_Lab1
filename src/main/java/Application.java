import input.Parser;

public class Application {

    private Parser parser;

    public Application( Parser parser) {
        this.parser = parser;
    }

    public void makeRequest() {
        String request = parser.getRequest();
    }
}
