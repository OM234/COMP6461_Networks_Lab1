package message;

public class RequestMessage {
    private final boolean isHelpRequest;
    private final boolean verbose;
    private final String message;
    private final String hostName;
    private String HttpResponse;

    public RequestMessage(boolean isHelpRequest, boolean verbose, String message, String hostName) {
        this.isHelpRequest = isHelpRequest;
        this.verbose = verbose;
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

    public String getHttpResponse() {
        return HttpResponse;
    }

    public void setHttpResponse(String httpResponse) {
        HttpResponse = httpResponse;
    }
}
