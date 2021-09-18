package input.helper;

import input.helper.common.HTTPRequestParser;
import message.RequestMessage;

import java.util.Arrays;

public class GetRequestParser extends HTTPRequestParser implements RequestHelper {

    public GetRequestParser(String[] args) {
        super(args);
    }

    @Override
    public RequestMessage getRequest() {
        super.setIsVerbose();
        verifyNoDorFArguments();
        super.initializeHeaders();
        initializeURLString();
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

        message = new RequestMessage(super.isHelpRequest, super.isVerbose, super.requestString);
        return message;
    }
}
