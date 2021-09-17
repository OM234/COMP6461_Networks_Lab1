package input;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Parser {

    private final String[] args;
    private final HelpRequestParser helpRequestParser;
    private final GetPostRequestParser getPostRequestParser;
    private RequestType requestType;

    public Parser(String[] args, HelpRequestParser helpRequestParser, GetPostRequestParser getPostRequestParser) {
        this.args = args;
        this.helpRequestParser = helpRequestParser;
        this.getPostRequestParser = getPostRequestParser;
    }

    public String getRequest() {
        verifyInitialArg();
        setRequestType();
        return "";
    }

    public void verifyInitialArg() {
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

    private RequestType getRequestType() {
        return requestType;
    }
}
