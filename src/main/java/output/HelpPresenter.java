package output;

import input.helper.HelpRequestParser;
import message.RequestMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class HelpPresenter {

    private String request;
    private String[] messageArray;
    private String output = "";
    private final String GENERIC_HELP_TEXT_FILE_PATH = "./src/main/resources/genericHelpText.txt";
    private final String GET_HELP_TEXT_FILE_PATH = "./src/main/resources/getHelpText.txt";
    private final String POST_HELP_TEXT_FILE_PATH = "./src/main/resources/postHelpText.txt";

    public void printHelpRequest(RequestMessage request) {
        setRequest(request);
        setMessageArray();
        printHelpRequestText(request.getMessage());
    }

    private void setRequest(RequestMessage request) {
        this.request = request.getMessage();
    }

    private void setMessageArray() {
        messageArray = request.split(" ");
    }

    private void printHelpRequestText(String message) {
        if (isGenericHelpMessage()) {
            setOutputToGenericMessage();
        } else {
            setOutputMethodSpecificHelpMessage();
        }
        System.out.println(output);
    }

    private boolean isGenericHelpMessage() {
        return messageArray.length == 1;
    }

    private void setOutputToGenericMessage() {
        setOutputFile(GENERIC_HELP_TEXT_FILE_PATH);
    }

    private void setOutputFile(String fileName) {
        try {
            tryToSetOutputFile(fileName);
        } catch (IOException e) {
            handleError(e);
        }
    }

    private void tryToSetOutputFile(String fileName) throws IOException {
        output = Files.readString(Path.of(fileName));
    }

    private void handleError(IOException e) {
        System.out.println("Problem with loading help text");
        e.printStackTrace();
        throw new RuntimeException();
    }

    private void setOutputMethodSpecificHelpMessage() {
        String secondArgument = messageArray[1];
        HelpRequestParser.HelpTopics helpTopic = initializeHelpTopic(secondArgument);
        chooseAppropriatePrintOutputForHelpTopic(helpTopic);
    }

    private HelpRequestParser.HelpTopics initializeHelpTopic(String secondArgument) {
        return Arrays.stream(HelpRequestParser.HelpTopics.values())
                .filter(secondArgumentEqualsAHelpTopic(secondArgument))
                .findAny()
                .orElseThrow(throwHelpTopicDoesNotExistException());
    }

    private Predicate<HelpRequestParser.HelpTopics> secondArgumentEqualsAHelpTopic(String secondArgument) {
        return topic -> secondArgument.equalsIgnoreCase(topic.toString());
    }

    private Supplier<IllegalArgumentException> throwHelpTopicDoesNotExistException() {
        return () -> new IllegalArgumentException("help topic does not exist");
    }


    private void chooseAppropriatePrintOutputForHelpTopic(HelpRequestParser.HelpTopics helpTopic) {
        if(helpTopic.equals(HelpRequestParser.HelpTopics.GET)) {
            setGetRequestOutput();
        } else if (helpTopic.equals(HelpRequestParser.HelpTopics.POST)) {
            setPostRequestOutput();
        } else {
            throwNoOutputCreatedForHealthTopicError();
        }
    }

    private void throwNoOutputCreatedForHealthTopicError() {
        throw new RuntimeException("No output created for health topic");
    }

    private void setGetRequestOutput() {
        setOutputFile(GET_HELP_TEXT_FILE_PATH);
    }

    private void setPostRequestOutput() {
        setOutputFile(POST_HELP_TEXT_FILE_PATH);
    }
}
