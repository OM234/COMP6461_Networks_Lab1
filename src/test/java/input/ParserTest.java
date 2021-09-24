package input;

import input.parser.helper.GetRequestParser;
import input.parser.helper.HelpRequestParser;
import input.parser.helper.PostRequestParser;
import input.parser.Parser;
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
    private final String expectedGetStringWithParameters = "GET /get?course=networking&assignment=1 HTTP/1.1\n"
            + "Host: " + "httpbin.org\n" +  "anotherkey: anothervalue\n" + "Accept: application/json" + "\n\n";
    private final String expectedPostRequestString = "POST /post HTTP/1.1\n" + "Host: " + "httpbin.org"
            + "\n" +  "Content-Length: 17\n" + "Content-Type: application/json" + "\n\n" + "{\"Assignment\": 1}";

    @BeforeEach
    void setup() {
        helpRequestParser = null;
        getRequestParser = null;
        postRequestParser = null;
    }

    @Test
    void verifyValidHelpRequestNoArgs() {
        String args[] = {"httpc", "help"};
        RequestMessage requestMessage;
        Parser parser = getParser(args);

        requestMessage = parser.getRequest(args);

        assertTrue(requestMessage.isHelpRequest());
        assertEquals("help", requestMessage.getMessage());
    }

    @Test
    void verifyValidHelpRequestWithGet() {
        String args[] = {"httpc", "help", "get"};
        RequestMessage requestMessage;
        Parser parser = getParser(args);

        requestMessage = parser.getRequest(args);

        assertTrue(requestMessage.isHelpRequest());
        assertEquals("help get", requestMessage.getMessage());
    }

    @Test
    void verifyValidHelpRequestWithPost() {
        String args[] = {"httpc", "help", "post"};
        RequestMessage requestMessage;
        Parser parser = getParser(args);

        requestMessage = parser.getRequest(args);

        assertTrue(requestMessage.isHelpRequest());
        assertEquals("help post", requestMessage.getMessage());
    }

    @Test
    void verifyInvalidHelpRequestThrowsException() {
        String args[] = {"httpc", "help", "something_other_than_get_or_post_for_now"};
        RequestMessage requestMessage;
        Parser parser = getParser(args);

        assertThrows(IllegalArgumentException.class, () -> parser.getRequest(args));
    }

    @Test
    void verifyValidGetRequest1() {
        String[] args = {"httpc", "get", "'http://httpbin.org/get?course=networking&assignment=1'"};
        RequestMessage requestMessage;
        Parser parser = getParser(args);

        requestMessage = parser.getRequest(args);

        assertEquals(expectedGetString1, requestMessage.getMessage());
        System.out.println();
    }


    @Test
    void verifyValidGetRequestWithHeaders() {
        String[] args = {"httpc", "get", "-h", "Accept:application/json", "-h", "anotherkey:anothervalue",  "'http://httpbin.org/get?course=networking&assignment=1'"};

        RequestMessage requestMessage;
        Parser parser = getParser(args);

        requestMessage = parser.getRequest(args);

        assertEquals(expectedGetStringWithParameters, requestMessage.getMessage());
        System.out.println();
    }

    @Test
    void verifyValidPostRequest() {
        String[] args = {"httpc", "post", "-h", "Content-Type:application/json", "-d", "'{\"Assignment\": 1}'",
                "http://httpbin.org/post"};

        RequestMessage requestMessage;
        Parser parser = getParser(args);

        requestMessage = parser.getRequest(args);

        assertEquals(expectedPostRequestString, requestMessage.getMessage());
        System.out.println();
    }

    private Parser getParser(String[] args) {
        initializeHelpers(args);
        Parser parser = new Parser(helpRequestParser, getRequestParser, postRequestParser);
        return parser;
    }

    private void initializeHelpers(String[] args) {
        helpRequestParser = new HelpRequestParser();
        getRequestParser = new GetRequestParser();
        postRequestParser = new PostRequestParser();
    }
}