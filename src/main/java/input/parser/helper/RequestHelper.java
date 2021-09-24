package input.parser.helper;

import message.RequestMessage;

public interface RequestHelper {
    RequestMessage getRequest(String[] args);
}
