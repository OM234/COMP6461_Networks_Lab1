package input.helper;

import input.helper.common.HTTPRequestParser;
import message.RequestMessage;

import java.util.Arrays;

public class PostRequestParser extends HTTPRequestParser implements RequestHelper {

    public PostRequestParser(String[] args) {
        super(args);
    }

    @Override
    public RequestMessage getRequest() {
        super.setIsVerbose();
        verifyOnlyOneDorFArgument();
        super.initializeHeaders();
        super.initializeURLString();
        return createHttpRequestLine();
    }

    private void verifyOnlyOneDorFArgument() {
        long numArgs = Arrays.stream(super.args)
                .filter(super::arguementIsDorF)
                .limit(2)
                .count();
        super.initializeHeaders();

        if(numArgs > 1) {
            throw new IllegalArgumentException("only 1 -d of -f argument allowed");
        }
    }

    private RequestMessage createHttpRequestLine() {
        RequestMessage message;

        message = new RequestMessage(super.isHelpRequest, super.isVerbose, super.requestString);
        return message;
    }
}

