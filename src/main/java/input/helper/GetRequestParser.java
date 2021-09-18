package input.helper;

import message.RequestMessage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GetRequestParser implements RequestHelper {

    private String[] args;
    private boolean isVerbose = false;
    private final boolean isHelpRequest = false;
    private Map<String, String> headers = new HashMap<>();
    private String URLString;
    private URL URL;
    private String requestString = "";

    public GetRequestParser(String[] args) {
        this.args = args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public RequestMessage getRequest() {
        setIsVerbose();
        verifyNoDorFArguments();
        initializeHeaders();
        initializeURLString();
        return createHttpRequestLine();
    }

    private void setIsVerbose() {
        if (args[1].equalsIgnoreCase("-v")) {
            isVerbose = true;
        }
    }

    private void verifyNoDorFArguments() {
        Arrays.stream(args)
                .filter(this::arguementIsDorF)
                .findAny()
                .ifPresent(badArgument -> throwIllegalArgumentException());
    }

    private void throwIllegalArgumentException() {
        throw new IllegalArgumentException("no -d or -f argument for get");
    }

    private boolean arguementIsDorF(String arg) {
        return arg.equalsIgnoreCase("-d") || arg.equalsIgnoreCase("-f");
    }

    private void initializeHeaders() {
        headers = IntStream.range(0, args.length - 1)
                .filter(argumentIsHeader())
                .mapToObj(mapKeyValToMapEntry())
                .collect(mapEntriesToHashMap());
    }

    private IntPredicate argumentIsHeader() {
        return index -> args[index].equalsIgnoreCase("-h");
    }

    private IntFunction<AbstractMap.SimpleImmutableEntry<String, String>> mapKeyValToMapEntry() {
        return index -> {
            String[] keyValArray = splitKeyValIntoArray(args[index + 1]);
            String key = keyValArray[0];
            String val = keyValArray[1];
            return new AbstractMap.SimpleImmutableEntry<>(key, val);
        };
    }

    private String[] splitKeyValIntoArray(String keyVal) {
        return keyVal.split(":");
    }

    private Collector<AbstractMap.SimpleImmutableEntry<String, String>, ?, Map<String, String>> mapEntriesToHashMap() {
        return Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue);
    }

    private void initializeURLString() {
        getURLFromLastArg();
        removeOuterURLQuotes();
    }

    private void getURLFromLastArg() {
        this.URLString = args[args.length - 1];
    }

    private void removeOuterURLQuotes() {
        this.URLString = this.URLString.substring(1, this.URLString.length()-1);
    }

    private RequestMessage createHttpRequestLine() {
        RequestMessage message;

        tryToInitializeURL();
        addGETAndHostToRequest();
        addHeadersToRequest();
        addFinalNewLineToRequest();

        message = new RequestMessage(this.isHelpRequest, this.isVerbose, this.requestString);
        return message;
    }

    private void tryToInitializeURL() {
        try {
            initializeURL();
        } catch (MalformedURLException e) {
            handleMalformedURL(e);
        }
    }

    private void initializeURL() throws MalformedURLException {
        URL = new URL(this.URLString);
    }

    private void handleMalformedURL(MalformedURLException e) {
        System.out.println(e);
        throwIllegalArgumentException();
    }

    private void addGETAndHostToRequest() {
        requestString += "GET " + URL.getFile() + " HTTP/1.1\n" +
                "Host: " + URL.getHost() + "\n";
    }

    private void addHeadersToRequest() {
        headers.forEach((key, value) -> {
            this.requestString += key + ": ";
            this.requestString += value + "\n";
        });
    }

    private void addFinalNewLineToRequest() {
        requestString += "\n";
    }
}
