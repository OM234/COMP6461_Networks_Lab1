package input.parser.helper;

import input.parser.helper.common.HTTPRequestParser;
import message.RequestMessage;

import java.util.Arrays;

public class GetRequestParser extends HTTPRequestParser implements RequestHelper {

    @Override
    public RequestMessage getRequest(String[] args) {
        setArgs(args);
        super.setIsVerbose();
        verifyNoDorFArguments();
        super.initializeHeaders();
        initializeURLString();
        super.verifyNotHTTPSRequest();
        return createHttpRequestLine();
    }

    private void verifyNoDorFArguments() {
        Arrays.stream(super.args)
                .filter(super::argumentIsDorF)
                .findAny()
                .ifPresent(badArgument -> {
                    throw new IllegalArgumentException("no -d or -f argument for get");
                });
    }

    private void initializeURLString() {
        getURLFromLastArg();
        super.removeOuterURLQuotes();
    }

    @Override
    protected RequestMessage createHttpRequestLine() {
        RequestMessage message;

        super.tryToInitializeURL();
        super.addMethodAndHostToRequest(MethodsAccepted.GET);
        super.addHeadersToRequest();
        super.addRequiredNewLineToRequest();

        message = new RequestMessage(super.isHelpRequest, super.isVerbose, super.requestString, super.URL.getHost());
        return message;
    }
}
