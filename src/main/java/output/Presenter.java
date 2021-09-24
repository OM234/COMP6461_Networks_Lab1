package output;

import message.HTTPResponse;
import message.RequestMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Presenter {

    private final HelpPresenter helpPresenter;
    private final String FILE_PATH = "./src/main/resources/";
    public Presenter(HelpPresenter helpPresenter) {
        this.helpPresenter = helpPresenter;
    }

    public void print(HTTPResponse response) {
       if(response.isPrintToFile()) {
           printToFile(response);
       } else {
           System.out.println(response.getHttpResponse());
       }
    }

    private void printToFile(HTTPResponse response) {
        String path = FILE_PATH + response.getFilePath();
        try {
            Files.write(Path.of(path), response.getHttpResponse().getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printHelpRequest(RequestMessage message) {
        helpPresenter.printHelpRequest(message);
    }
}
