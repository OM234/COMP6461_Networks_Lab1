package message;

public class Message {
    private final boolean isHelpRequest;
    private final boolean verbose;
    private final String message;

    public Message(boolean isHelpRequest, boolean verbose, String message) {
        this.isHelpRequest = isHelpRequest;
        this.verbose = verbose;
        this.message = message;
    }

    public boolean isHelpRequest() {
        return isHelpRequest;
    }

    public String getMessage() {
        return message;
    }
}
