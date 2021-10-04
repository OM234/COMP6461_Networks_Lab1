package input.parser.helper.common;

import message.RequestMessage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class HTTPRequestParser {

    protected String[] args;
    protected boolean isVerbose = false;
    protected final boolean isHelpRequest = false;
    protected Map<String, String> headers = new HashMap<>();
    protected String URLString;
    protected int URLIndex;
    protected java.net.URL URL;
    protected String requestString = "";

    public void setArgs(String[] args) {
        resetRequestString();
        this.args = args;
    }

    private void resetRequestString() {
        requestString = "";
    }

    protected void setIsVerbose() {
        if (args[1].equalsIgnoreCase("-v")) {
            isVerbose = true;
        } else {
            isVerbose = false;
        }
    }

    protected boolean argumentIsDorF(String arg) {
        return arg.equalsIgnoreCase("-d") || arg.equalsIgnoreCase("-f");
    }

    protected void initializeHeaders() {
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

    protected void setURLIndex() {
        this.URLIndex = IntStream.range(0, args.length)
                .filter(index -> isURLIndexWithoutQuotes(index) || isURLIndexWithQuotes(index))
                .findFirst()
                .getAsInt();
    }

    protected boolean isURLIndexWithQuotes(int index) {
        try {
            URL = new URL(args[index].substring(1, args[index].length()-1));
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }

    protected boolean isURLIndexWithoutQuotes(int index) {
        try {
            URL = new URL(args[index]);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }

    protected void tryToInitializeURL() {
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
        throw new IllegalArgumentException();
    }

    protected void getURLFromArgs() {
        setURLIndex();
        this.URLString = args[URLIndex];
    }

    protected void removeOuterURLQuotes() {
        if(outerQuotesPresent()) {
            this.URLString = this.URLString.substring(1, this.URLString.length() - 1);
        }
    }

    private boolean outerQuotesPresent() {
        return URLString.charAt(0) == '\'';
    }

    protected void addMethodAndHostToRequest(MethodsAccepted method) {
        requestString += method + " " + URL.getFile() + " HTTP/1.1\n" +
                "Host: " + URL.getHost() + "\n";
    }

    protected void addHeadersToRequest() {
        headers.forEach((key, value) -> {
            this.requestString += key + ": ";
            this.requestString += value + "\n";
        });
    }

    protected void addRequiredNewLineToRequest() {
        requestString += "\n";
    }

    protected abstract RequestMessage createHttpRequestLine();

    protected void verifyNotHTTPSRequest() {
        if(urlIsHTTPs()){
            throw new IllegalArgumentException("Cannot perform HTTPs connections");
        }
    }

    private boolean urlIsHTTPs() {
        return this.args[args.length-1].charAt(4) == 's' ||
                this.args[args.length-1].charAt(5) == 's';
    }

    protected enum MethodsAccepted {
        GET, POST
    }
}
