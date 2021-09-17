package input.helper;

import message.Message;

public class GetPostRequestParser implements RequestHelper {

    private final String[] args;

    public GetPostRequestParser(String[] args) {
        this.args = args;
    }

    @Override
    public Message getRequest() {
        return null;
    }
}
