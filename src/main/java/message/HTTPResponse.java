package message;

import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class HTTPResponse {

    private final String fullHTTPResponse;
    private final boolean isVerbose;
    private final RequestMessage requestMessage;
    private String httpResponse;

    public HTTPResponse(String httpResponse, RequestMessage requestMessage) {
        this.fullHTTPResponse = httpResponse;
        this.isVerbose = requestMessage.getIsVerbose();
        this.requestMessage = requestMessage;
        setHTTPResponse();
    }

    private void setHTTPResponse() {
        if(isVerbose) {
            httpResponse = fullHTTPResponse;
        } else {
            setNonVerboseResponse();
        }
    }

    private void setNonVerboseResponse() {
        httpResponse = fullHTTPResponse
                .lines()
                .dropWhile(lineIsNotEmpty())
                .collect(joinLinesWithNewLine())
                .trim();
    }

    private Predicate<String> lineIsNotEmpty() {
        return line -> !line.isEmpty();
    }

    private Collector<CharSequence, ?, String> joinLinesWithNewLine() {
        return Collectors.joining("\n");
    }

    public String getHttpResponse() {
        return this.httpResponse;
    }

    public String getFullHTTPResponse() {
        return this.fullHTTPResponse;
    }

    public boolean isPrintToFile() {
        return requestMessage.isPrintToFile();
    }

    public String getFilePath() {
        return requestMessage.getOutputFilePath();
    }

    public int getURLIndex() {
        return requestMessage.getURLIndex();
    }
}
