import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class clientTest {
//
//    @Test
//    void aTest() throws IOException {
//        SocketChannel socket = SocketChannel.open();
//        socket.connect(new InetSocketAddress("httpbin.org", 80));
//
//        String request = "GET /get?course=networking&assignment=1 HTTP/1.1\n" + "Host: " + "httpbin.org" + "\n\n";
//
//        socket.write(ByteBuffer.wrap(request.getBytes(StandardCharsets.UTF_8)));
//        ByteBuffer bf = ByteBuffer.allocate(600);
//        socket.read(bf);
//        System.out.print(request);
//        System.out.println(new String(bf.array()));
//    }
//
//    @Test
//    void aTest2() throws IOException {
//        SocketChannel socket = SocketChannel.open();
//        socket.connect(new InetSocketAddress("httpbin.org", 80));
//
//        String request = "POST /post HTTP/1.1\n" + "Host: " + "httpbin.org"
//                + "\n" + "Content-Type: application/json\n" + "Content-Length: 17" + "\n\n" + "{\"Assignment\": 1}" ;
//        System.out.println(request);
//        socket.write(ByteBuffer.wrap(request.getBytes(StandardCharsets.UTF_8)));
//        ByteBuffer bf = ByteBuffer.allocate(600);
//        socket.read(bf);
//        System.out.println(new String(bf.array()));
//    }
//
//    @Test
//    void aTest3() throws IOException {
//        SocketChannel socket = SocketChannel.open();
//        socket.connect(new InetSocketAddress("www.google.com", 80));
//
//        String request = "GET / HTTP/1.1\n" + "Host: " + "www.google.com"
//                + "\n\n";
//        System.out.println(request);
//        socket.write(ByteBuffer.wrap(request.getBytes(StandardCharsets.UTF_8)));
//        ByteBuffer bf = ByteBuffer.allocate(600);
//        socket.read(bf);
//        System.out.println(new String(bf.array()));
//    }
//
//    @Test
//    void test4() throws MalformedURLException {
//        URL url = new URL("http://httpbin.org/get?course=networking&assignment=1");
//        System.out.println(url);
//    }

}
