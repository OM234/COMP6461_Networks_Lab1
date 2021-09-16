import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class clientTest {

    @Test
    void aTest() throws IOException {
        SocketChannel socket = SocketChannel.open();
        socket.connect(new InetSocketAddress("httpbin.org", 80));
        socket.write(ByteBuffer.wrap("GET /status/418 HTTP/1.0".getBytes()));
        ByteBuffer bf = ByteBuffer.allocate(100);
        socket.read(bf);
        System.out.println();
    }

}
