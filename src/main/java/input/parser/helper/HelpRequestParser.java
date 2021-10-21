package input.parser.helper;

import message.RequestMessage;

import java.util.Arrays;
import java.util.function.Predicate;

public class HelpRequestParser implements RequestHelper {

    private String[] args;
    private String request;
    private String secondArgument = "";
    private final String initialMessage = "help";
    private final boolean isHelpRequest = true;
    private final boolean isVerbose = false;

    @Override
    public RequestMessage getRequest(String[] args) {
        resetInitialArgs();

        this.args = args;
        if(secondArgumentPresent()) {
            verifySecondArgument();
            setSecondArgument();
        }
        appendSecondArgToRequest();
        return new RequestMessage(isHelpRequest, isVerbose, -1, request, "", -1, false);
    }

    private void resetInitialArgs() {
        request = initialMessage;
        secondArgument = "";
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    private boolean secondArgumentPresent() {
        return args.length > 1;
    }

    private void setSecondArgument() {
        secondArgument = Arrays.stream(HelpTopics.values())
                .filter(helpArgumentMatchesHelpTopic())
                .findAny()
                .get()
                .toString()
                .toLowerCase();
    }

    private void verifySecondArgument() {
        Arrays.stream(HelpTopics.values())
                .filter(helpArgumentMatchesHelpTopic())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("help argument invalid"));
    }

    private Predicate<HelpTopics> helpArgumentMatchesHelpTopic() {
        return helpTopic -> args[1].equalsIgnoreCase(helpTopic.toString());
    }

    private void appendSecondArgToRequest() {
        request = request + " " + secondArgument;
        request = request.trim();
    }

    public enum HelpTopics {
        GET, POST
    }
}
