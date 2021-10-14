package message;

public class RequestMessage {
    private final boolean isHelpRequest;
    private final boolean isVerbose;
    private final int port;
    private final String message;
    private final String hostName;
    private final int URLIndex;
    private boolean outputToFile = false;
    private String outputFile;

    public RequestMessage(boolean isHelpRequest, boolean isVerbose, int port, String message, String hostName, int URLIndex) {
        this.isHelpRequest = isHelpRequest;
        this.isVerbose = isVerbose;
        this.port = port;
        this.message = message;
        this.hostName = hostName;
        this.URLIndex = URLIndex;
    }

    public boolean isHelpRequest() {
        return isHelpRequest;
    }

    public String getMessage() {
        return message;
    }

    public int getPort() {
        return this.port;
    }

    public String getHostName() {
        return this.hostName;
    }

    public boolean getIsVerbose() {
        return this.isVerbose;
    }

    public boolean isPrintToFile() {
        return outputToFile;
    }

    public void setOutputToFile(boolean outputToFile) {
        this.outputToFile = outputToFile;
    }

    public String getOutputFilePath() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public int getURLIndex() {
        return URLIndex;
    }
}
