package message;

public class Message {
    private final boolean isHelpRequest;
    private final String message;

    public Message(boolean isHelpRequest, String message) {
        this.isHelpRequest = isHelpRequest;
        this.message = message;
    }

    public boolean isHelpRequest() {
        return isHelpRequest;
    }

    public String getMessage() {
        return message;
    }
}
