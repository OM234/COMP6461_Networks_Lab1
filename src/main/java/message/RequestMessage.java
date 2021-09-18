package message;

public class RequestMessage {
    private final boolean isHelpRequest;
    private final boolean verbose;
    private final String message;

    public RequestMessage(boolean isHelpRequest, boolean verbose, String message) {
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
