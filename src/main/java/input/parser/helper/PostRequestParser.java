package input.parser.helper;

import input.parser.helper.common.HTTPRequestParser;
import message.RequestMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class PostRequestParser extends HTTPRequestParser implements RequestHelper {

    private String body = "";

    @Override
    public RequestMessage getRequest(String[] args) {
        setArgs(args);
        super.setIsVerbose();
        verifyOnlyOneDorFArgument();
        super.initializeHeaders();
        initializeURLString();
        super.verifyNotHTTPSRequest();
        initializeBody();
        addContentLengthToHeader();
        return createHttpRequestLine();
    }

    private void initializeBody() {
        boolean bodyIsFile = bodyIsFile();
        if (bodyIsFile) {
            setBodyFromFileContents();
        } else {
            setBodyFromBodyArgument();
            removeUnnecessaryBodyQuotes();
        }
    }

    private void setBodyFromFileContents() {
        String filePath = getFilePath();
        try {
            tryToSetBodyFromFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tryToSetBodyFromFile(String filePath) throws IOException {
        this.body = Files.readString(Path.of(filePath));
    }

    private String getFilePath() {
        return IntStream.range(0, super.args.length)
                .filter(getDorFArgumentIndex())
                .mapToObj(mapDorFArgumentToString())
                .findAny()
                .get();
    }

    private boolean bodyIsFile() {
        return IntStream.range(0, super.args.length)
                .anyMatch(argumentIsF());
    }

    private IntPredicate argumentIsF() {
        return index -> args[index].equalsIgnoreCase("-f");
    }

    private void setBodyFromBodyArgument() {
        IntStream.range(0, super.args.length)
                .filter(getDorFArgumentIndex())
                .mapToObj(mapDorFArgumentToString())
                .findAny()
                .ifPresent(initializeBodyFromArgument());
    }

    private IntPredicate getDorFArgumentIndex() {
        return index -> argumentIsDorF(args[index]);
    }

    private IntFunction<String> mapDorFArgumentToString() {
        return index -> args[index + 1];
    }

    private Consumer<String> initializeBodyFromArgument() {
        return bodyArguement -> body = bodyArguement;
    }

    private void removeUnnecessaryBodyQuotes() {
        body = body.substring(1, body.length()-1);
    }

    public void addContentLengthToHeader() {
        headers.putIfAbsent("Content-Length", getBodyLength());
    }

    private String getBodyLength() {
        return Integer.toString(body.length());
    }

    protected void initializeURLString() {
        super.getURLFromArgs();
    }

    private void verifyOnlyOneDorFArgument() {
        long numArgs = Arrays.stream(super.args)
                .filter(super::argumentIsDorF)
                .limit(2)
                .count();
        super.initializeHeaders();

        if (numArgs > 1) {
            throw new IllegalArgumentException("only 1 -d of -f argument allowed");
        }
    }

    @Override
    protected RequestMessage createHttpRequestLine() {
        RequestMessage message;

        super.tryToInitializeURL();
        super.addMethodAndHostToRequest(MethodsAccepted.POST);
        super.addHeadersToRequest();
        super.addRequiredNewLineToRequest();
        addBodyToRequest();

        message = new RequestMessage(super.isHelpRequest, super.isVerbose, super.requestString,
                super.URL.getHost(), super.URLIndex);
        return message;
    }

    private void addBodyToRequest() {
        super.requestString += body;
    }
}

