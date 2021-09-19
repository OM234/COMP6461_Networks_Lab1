package message;

public class RequestMessage {
    private final boolean isHelpRequest;
    private final boolean isVerbose;
    private final String message;
    private final String hostName;

    public RequestMessage(boolean isHelpRequest, boolean isVerbose, String message, String hostName) {
        this.isHelpRequest = isHelpRequest;
        this.isVerbose = isVerbose;
        this.message = message;
        this.hostName = hostName;
    }

    public boolean isHelpRequest() {
        return isHelpRequest;
    }

    public String getMessage() {
        return message;
    }

    public String getHostName() {
        return this.hostName;
    }

    public boolean getIsVerbose() {
        return this.isVerbose;
    }
}
