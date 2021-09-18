package input.helper;

import input.helper.common.HTTPRequestParser;
import message.RequestMessage;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class PostRequestParser extends HTTPRequestParser implements RequestHelper {

    private String body = "";

    public PostRequestParser(String[] args) {
        super(args);
    }

    @Override
    public RequestMessage getRequest() {
        super.setIsVerbose();
        verifyOnlyOneDorFArgument();
        super.initializeHeaders();
        initializeURLString();
        initializeBody();
        addContentLengthToHeader();
        return createHttpRequestLine();
    }

    private void initializeBody() {
        boolean bodyIsFile = bodyIsFile();
        if (bodyIsFile) {
            setBodyFromFileContents();
        } else {
            setBodyFromBodyArgument();
            removeUnnecessaryBodyQuotes();
        }
    }

    private void setBodyFromFileContents() {
        //TODO: check if implementation is necessary
    }

    private boolean bodyIsFile() {
        return IntStream.range(0, super.args.length)
                .anyMatch(argumentIsF());
    }

    private IntPredicate argumentIsF() {
        return index -> args[index].equalsIgnoreCase("-f");
    }

    private void setBodyFromBodyArgument() {
        IntStream.range(0, super.args.length)
                .filter(getDArgumentIndex())
                .mapToObj(mapDArgumentToString())
                .findAny()
                .ifPresent(initializeBodyFromArgument());
    }

    private IntPredicate getDArgumentIndex() {
        return index -> argumentIsDorF(args[index]);
    }

    private IntFunction<String> mapDArgumentToString() {
        return index -> args[index + 1];
    }

    private Consumer<String> initializeBodyFromArgument() {
        return bodyArguement -> body = bodyArguement;
    }

    private void removeUnnecessaryBodyQuotes() {
        body = body.substring(1, body.length()-1);
    }

    public void addContentLengthToHeader() {
        headers.putIfAbsent("Content-Length", getBodyLength());
    }

    private String getBodyLength() {
        return Integer.toString(body.length());
    }

    protected void initializeURLString() {
        super.getURLFromLastArg();
    }

    private void verifyOnlyOneDorFArgument() {
        long numArgs = Arrays.stream(super.args)
                .filter(super::argumentIsDorF)
                .limit(2)
                .count();
        super.initializeHeaders();

        if (numArgs > 1) {
            throw new IllegalArgumentException("only 1 -d of -f argument allowed");
        }
    }

    @Override
    protected RequestMessage createHttpRequestLine() {
        RequestMessage message;

        super.tryToInitializeURL();
        super.addMethodAndHostToRequest(MethodsAccepted.POST);
        super.addHeadersToRequest();
        super.addRequiredNewLineToRequest();
        addBodyToRequest();

        message = new RequestMessage(super.isHelpRequest, super.isVerbose, super.requestString, super.URL.getHost());
        return message;
    }

    private void addBodyToRequest() {
        super.requestString += body;
    }
}

