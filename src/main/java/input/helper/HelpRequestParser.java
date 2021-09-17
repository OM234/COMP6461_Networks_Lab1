package input.helper;

import message.Message;

public class HelpRequestParser implements RequestHelper{
    private final String[] args;

    public HelpRequestParser(String[] args) {
        this.args = args;
    }

    @Override
    public Message getRequest() {

        return null;
    }
}
