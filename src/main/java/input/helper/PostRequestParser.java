package input.helper;

import message.RequestMessage;

public class PostRequestParser implements RequestHelper {

    private String[] args;

    public PostRequestParser(String[] args) {
        this.args = args;
    }

    @Override
    public RequestMessage getRequest() {
        return null;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}

