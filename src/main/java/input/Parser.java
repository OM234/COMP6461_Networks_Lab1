package input;

import input.helper.RequestHelper;
import message.RequestMessage;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Parser {

    private final String[] args;
    private final RequestHelper helpRequestParser;
    private final RequestHelper getRequestParser;
    private final RequestHelper postRequestParser;
    private RequestType requestType;

    public Parser(String[] args,
                  RequestHelper helpRequestParser,
                  RequestHelper getRequestParser,
                  RequestHelper postRequestParser) {
        this.args = args;
        this.helpRequestParser = helpRequestParser;
        this.getRequestParser = getRequestParser;
        this.postRequestParser = postRequestParser;
    }

    public RequestMessage getRequest() {
        RequestMessage request;

        verifyInitialArg();
        setRequestType();
        request = getRequestFromHelperParsers();

        return request;
    }

    private void verifyInitialArg() {
        boolean valid = verifyMinimumLength() && verifyFirstArgumentGetOrPostorHelp();
        if(!valid) {
            throw new IllegalArgumentException("first argument incorrect");
        }
    }

    private boolean verifyMinimumLength() {
        return args.length > 0;
    }

    private boolean verifyFirstArgumentGetOrPostorHelp() {
        String initialArg = args[0];
        return Arrays.stream(RequestType.values())
                .anyMatch(initialArgEqualsARequestType(initialArg));
    }

    private Predicate<RequestType> initialArgEqualsARequestType(String initialArg) {
        return requestType -> initialArg.equalsIgnoreCase(requestType.toString());
    }

    private void setRequestType() {
        String initialArg = args[0];
        Arrays.stream(RequestType.values())
                .filter(initialArgEqualsARequestType(initialArg))
                .findAny()
                .ifPresent(setRequestTypeConsumer());
    }

    private Consumer<RequestType> setRequestTypeConsumer() {
        return requestType -> this.requestType = requestType;
    }

    private RequestMessage getRequestFromHelperParsers() {
        RequestMessage request;
        if(requestType.equals(RequestType.HELP)) {
            request = helpRequestParser.getRequest();
        } else if (requestType.equals(RequestType.GET)) {
            request = getRequestParser.getRequest();
        } else if (requestType.equals(RequestType.POST)) {
            request = postRequestParser.getRequest();
        } else {
            throw new IllegalArgumentException("no request helper defined");
        }
        return request;
    }

    private RequestType getRequestType() {
        return requestType;
    }
}
