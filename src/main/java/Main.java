

import input.helper.GetRequestParser;
import input.helper.HelpRequestParser;
import input.Parser;
import input.helper.PostRequestParser;
import output.HelpPresenter;
import output.Presenter;

public class Main {

//    private Parser parser;
//    private static String[] args;
//   //private static OptionParser parser;
//
//    static {
//        parser = getArgParser();
//    }

    public static void main(String[] args) {
        Parser parser = new Parser(args, new HelpRequestParser(args), new GetRequestParser(args), new PostRequestParser(args));
        HelpPresenter helpPresenter = new HelpPresenter();
        Presenter presenter = new Presenter(helpPresenter);
        Application application = new Application(parser, presenter);
        application.start();

//        setArgs(args);
//        createEndpointAndRunClient();
    }

//    private static void setArgs(String[] args) {
//        Main.args = args;
//    }
//
//    private static void createEndpointAndRunClient() {
//        SocketAddress endPoint = createEndPoint();
//        runClient(endPoint);
//    }
//
//    private static SocketAddress createEndPoint() {
//        OptionSet opts;
//        String host;
//        int port;
//        SocketAddress endpoint;
//
//        opts = parser.parse(args);
//        host = (String) opts.valueOf("host");
//        port = Integer.parseInt((String) opts.valueOf("port"));
//        endpoint = new InetSocketAddress(host, port);
//
//        return endpoint;
//    }
//
//    private static OptionParser getArgParser() {
//        OptionParser parser = new OptionParser();
//        parser.acceptsAll(asList("host", "h"), "EchoServer hostname")
//                .withOptionalArg()
//                .defaultsTo("localhost");
//        parser.acceptsAll(asList("port", "p"), "EchoServer listening port")
//                .withOptionalArg()
//                .defaultsTo("8007");
//        return parser;
//    }
//
//    private static void runClient(SocketAddress endPoint) {
//    }
}
