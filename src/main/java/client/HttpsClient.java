package client;

import message.HTTPResponse;
import message.RequestMessage;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;


class HttpsClient {

    private RequestMessage requestMessage;
    private SSLSocket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String response = "";
    private HTTPResponse responseMessage;

    public HTTPResponse makeRequest(RequestMessage request) throws IOException {
        setRequestMessage(request);
        connectToSocket();
        writeToSocket();
        readResponse();
        closeReadersAndSocket();
        return responseMessage;
    }

    private void setRequestMessage(RequestMessage request) {
        this.requestMessage = request;
    }

    private void connectToSocket() throws IOException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        this.socket = (SSLSocket) factory.createSocket(requestMessage.getHostName(), requestMessage.getPort());
        socket.startHandshake();
    }

    private void writeToSocket() throws IOException {
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

        out.println(requestMessage.getMessage());
        out.flush();

        if (out.checkError()) {
            throw new IOException(out.toString());
        }
    }

    private void readResponse() throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response += inputLine + "\n";
        }
        responseMessage = new HTTPResponse(response, this.requestMessage);
    }

    private void closeReadersAndSocket() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}