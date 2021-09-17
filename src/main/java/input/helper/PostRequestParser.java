package input.helper;

import message.Message;

public class PostRequestParser implements RequestHelper {

    private String[] args;

    public PostRequestParser(String[] args) {
        this.args = args;
    }

    @Override
    public Message getRequest() {
        return null;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

