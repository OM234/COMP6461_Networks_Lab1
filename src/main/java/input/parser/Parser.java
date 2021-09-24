package input.parser;

import input.parser.helper.RequestHelper;
import message.RequestMessage;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.spi.AbstractResourceBundleProvider;
import java.util.stream.IntStream;

public class Parser implements RequestHelper{

    private String[] args;
    private final RequestHelper helpRequestParser;
    private final RequestHelper getRequestParser;
    private final RequestHelper postRequestParser;
    private RequestType requestType;

    public Parser(RequestHelper helpRequestParser,
                  RequestHelper getRequestParser,
                  RequestHelper postRequestParser) {
        this.helpRequestParser = helpRequestParser;
        this.getRequestParser = getRequestParser;
        this.postRequestParser = postRequestParser;
    }

    @Override
    public RequestMessage getRequest(String[] args) {
        RequestMessage request;

        setArgs(args);
        removeHTTPCArg();
        verifyInitialArg();
        setRequestType();
        request = getRequestFromHelperParsers();
        handleToTextOutput(request);

        return request;
    }
    private void removeHTTPCArg() {
        args = Arrays.copyOfRange(args, 1, args.length);
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
            request = helpRequestParser.getRequest(this.args);
        } else if (requestType.equals(RequestType.GET)) {
            request = getRequestParser.getRequest(this.args);
        } else if (requestType.equals(RequestType.POST)) {
            request = postRequestParser.getRequest(this.args);
        } else {
            throw new IllegalArgumentException("no request helper defined");
        }
        return request;
    }

    private void handleToTextOutput(RequestMessage request) {
        if(hasOutputArgument()) {
            setRequestOutputData(request);
        }
    }

    private boolean hasOutputArgument() {
        return Arrays.stream(args)
                .anyMatch(this::isOAarg);
    }

    private boolean isOAarg(String arg) {
        return arg.equalsIgnoreCase("-o");
    }

    private void setRequestOutputData(RequestMessage request) {
        int filePathArgIndex = getFilePathArgIndex();

        request.setOutputToFile(true);
        request.setOutputFile(args[filePathArgIndex]);
    }

    private int getFilePathArgIndex() {
        return IntStream.range(0, args.length)
                .filter(index -> oArgIndex(index))
                .findFirst()
                .getAsInt() + 1;
    }

    private boolean oArgIndex(int index) {
        return isOAarg(args[index]);
    }

    private RequestType getRequestType() {
        return requestType;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    private enum RequestType {
        GET, POST, HELP
    }
}
