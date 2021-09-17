package input.helper;

import message.Message;

public class GetRequestParser implements RequestHelper {

    private final String[] args;

    public GetRequestParser(String[] args) {
        this.args = args;
    }

    @Override
    public Message getRequest() {
        return null;
    }
}
