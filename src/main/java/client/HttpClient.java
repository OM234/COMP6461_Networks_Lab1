package client;

import message.HTTPResponse;
import message.RequestMessage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class HttpClient {

    private RequestMessage request;

    public HTTPResponse makeRequest(RequestMessage request) {
        setRequest(request);
        try(SocketChannel socket = SocketChannel.open()) {
            return tryToMakeHTTPRequest(socket);
        } catch (IOException e) {
            handleHTTPError(e);
            throw new RuntimeException();
        }
    }

    private HTTPResponse tryToMakeHTTPRequest(SocketChannel socket) throws IOException {
        connectToHost(socket);
        writeToSocket(socket);
        return readResponse(socket);
    }

    private void setRequest(RequestMessage request) {
        this.request = request;
    }

    private void connectToHost(SocketChannel socket) throws IOException {
        socket.connect(new InetSocketAddress(this.request.getHostName(), 80));
    }

    private void writeToSocket(SocketChannel socket) throws IOException {
        socket.write(ByteBuffer.wrap(this.request.getMessage().getBytes(StandardCharsets.UTF_8)));
    }

    private HTTPResponse readResponse(SocketChannel socket) throws IOException {
        ByteBuffer bf = ByteBuffer.allocate(10000);
        byte[] bytesWithoutNullBytes;

        readFromSocketIntoByteBuffer(socket, bf);
        bytesWithoutNullBytes = removeNullBytes(bf);
        
        return convertByteToHTTPReponseObject(bytesWithoutNullBytes);
    }

    private void readFromSocketIntoByteBuffer(SocketChannel socket, ByteBuffer bf) throws IOException {
        socket.read(bf);
    }

    private byte[] removeNullBytes(ByteBuffer bf) {
        byte[] bytes = bf.array();

        int i = bytes.length - 1;
        while (i >= 0 && bytes[i] == 0) {
            --i;
        }
        return Arrays.copyOf(bytes, i + 1);
    }

    private HTTPResponse convertByteToHTTPReponseObject(byte[] bytesWithoutNullBytes) {
        String response =  new String(bytesWithoutNullBytes);
        return new HTTPResponse(response, this.request);
    }

    private void handleHTTPError(IOException e) {
        e.printStackTrace();
        System.out.println("error when making HTTP request");
    }
}
