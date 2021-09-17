

import input.GetPostRequestParser;
import input.HelpRequestParser;
import input.Parser;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import static java.util.Arrays.asList;

public class Main {

//    private Parser parser;
//    private static String[] args;
//   //private static OptionParser parser;
//
//    static {
//        parser = getArgParser();
//    }

    public static void main(String[] args) {
        Parser parser = new Parser(args, new HelpRequestParser(), new GetPostRequestParser());
        Application application = new Application(parser);
        application.makeRequest();

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
