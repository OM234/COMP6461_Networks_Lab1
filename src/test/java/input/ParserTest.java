package input;

import input.helper.GetRequestParser;
import input.helper.HelpRequestParser;
import input.helper.PostRequestParser;
import message.RequestMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private HelpRequestParser helpRequestParser;
    private GetRequestParser getRequestParser;
    private PostRequestParser postRequestParser;
    private final String expectedGetString1 = "GET /get?course=networking&assignment=1 HTTP/1.1\n"
            + "Host: " + "httpbin.org" + "\n\n";

    @BeforeEach
    void setup() {
        helpRequestParser = null;
        getRequestParser = null;
        postRequestParser = null;
    }

    @Test
    void verifyValidHelpRequestNoArgs() {
        String args[] = {"help"};
        RequestMessage requestMessage;
        Parser parser = getParser(args);

        requestMessage = parser.getRequest();

        assertTrue(requestMessage.isHelpRequest());
        assertEquals("help", requestMessage.getMessage());
    }

    @Test
    void verifyValidHelpRequestWithGet() {
        String args[] = {"help", "get"};
        RequestMessage requestMessage;
        Parser parser = getParser(args);

        requestMessage = parser.getRequest();

        assertTrue(requestMessage.isHelpRequest());
        assertEquals("help get", requestMessage.getMessage());
    }

    @Test
    void verifyValidHelpRequestWithPost() {
        String args[] = {"help", "post"};
        RequestMessage requestMessage;
        Parser parser = getParser(args);

        requestMessage = parser.getRequest();

        assertTrue(requestMessage.isHelpRequest());
        assertEquals("help post", requestMessage.getMessage());
    }

    @Test
    void verifyInvalidHelpRequestThrowsException() {
        String args[] = {"help", "something_other_than_get_or_post_for_now"};
        RequestMessage requestMessage;
        Parser parser = getParser(args);

        assertThrows(IllegalArgumentException.class, parser::getRequest);
    }

    @Test
    void verifyValidGetRequest1() {
        String[] args = {"get", "'http://httpbin.org/get?course=networking&assignment=1'"};
        RequestMessage requestMessage;
        Parser parser = getParser(args);

        requestMessage = parser.getRequest();

        assertEquals(expectedGetString1, requestMessage.getMessage());
        System.out.println();
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