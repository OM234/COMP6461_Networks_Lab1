package input.helper;

import message.Message;

import java.util.Arrays;
import java.util.function.Predicate;

public class HelpRequestParser implements RequestHelper {

    private String[] args;
    private final String initialMessage = "help";
    private final boolean isHelpRequest = true;
    private final boolean isVerbose = false;

    public HelpRequestParser(String[] args) {
        this.args = args;
    }

    @Override
    public Message getRequest() {
        String request = this.initialMessage;
        String secondArgument = "";

        if(secondArgumentPresent()) {
            verifySecondArgument();
            secondArgument = getSecondArgument();
        }

        request = request + " " + secondArgument;
        request = request.trim();

        return new Message(isHelpRequest, isVerbose, request);
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    private boolean secondArgumentPresent() {
        return args.length > 1;
    }

    private String getSecondArgument() {
        return Arrays.stream(HelpTopics.values())
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

    private enum HelpTopics {
        GET, POST
    }
}
