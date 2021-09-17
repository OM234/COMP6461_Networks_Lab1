package input;

import input.helper.GetRequestParser;
import input.helper.HelpRequestParser;
import input.helper.PostRequestParser;
import message.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private HelpRequestParser helpRequestParser;
    private GetRequestParser getRequestParser;
    private PostRequestParser postRequestParser;

    @BeforeEach
    void setup() {
        helpRequestParser = null;
        getRequestParser = null;
        postRequestParser = null;
    }

    @Test
    void verifyValidHelpRequestNoArgs() {
        String args[] = {"help"};
        Message message;
        Parser parser = getParser(args);

        message = parser.getRequest();

        assertTrue(message.isHelpRequest());
        assertEquals("help", message.getMessage());
    }

    @Test
    void verifyValidHelpRequestWithGet() {
        String args[] = {"help", "get"};
        Message message;
        Parser parser = getParser(args);

        message = parser.getRequest();

        assertTrue(message.isHelpRequest());
        assertEquals("help get", message.getMessage());
    }

    @Test
    void verifyValidHelpRequestWithPost() {
        String args[] = {"help", "post"};
        Message message;
        Parser parser = getParser(args);

        message = parser.getRequest();

        assertTrue(message.isHelpRequest());
        assertEquals("help post", message.getMessage());
    }

    @Test
    void verifyInvalidHelpRequestThrowsException() {
        String args[] = {"help", "something_other_than_get_or_post_for_now"};
        Message message;
        Parser parser = getParser(args);

        assertThrows(IllegalArgumentException.class, parser::getRequest);
    }

    private Parser getParser(String[] args) {
        initializeHelpers(args);
        Parser parser = new Parser(args, helpRequestParser, getRequestParser, postRequestParser);
        return parser;
    }

    private void initializeHelpers(String[] args) {
        helpRequestParser = new HelpRequestParser(args);
        getRequestParser = new GetRequestParser(args);
        postRequestParser = new PostRequestParser(args);
    }
}