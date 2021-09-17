package input.helper;

import message.Message;

public class GetRequestParser implements RequestHelper {

    private String[] args;
    private boolean isVerbose;

    public GetRequestParser(String[] args) {
        this.args = args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Message getRequest() {
        return null;
    }
}
