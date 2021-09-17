package input.helper;

import message.Message;

public class PostRequestParser implements RequestHelper {

    private final String[] args;

    public PostRequestParser(String[] args) {
        this.args = args;
    }

    @Override
    public Message getRequest() {
        return null;
    }
}
