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
        super.initializeURLString();
        return createHttpRequestLine();
    }

    private void verifyNoDorFArguments() {
        Arrays.stream(super.args)
                .filter(super::arguementIsDorF)
                .findAny()
                .ifPresent(badArgument -> {
                    throw new IllegalArgumentException("no -d or -f argument for get");
                });
    }

    private RequestMessage createHttpRequestLine() {
        RequestMessage message;

        super.tryToInitializeURL();
        super.addMethodToRequest(MethodsAccepted.GET);
        super.addHeadersToRequest();
        super.addFinalNewLineToRequest();

        message = new RequestMessage(super.isHelpRequest, super.isVerbose, super.requestString);
        return message;
    }




}
