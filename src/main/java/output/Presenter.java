package output;

import message.RequestMessage;

public class Presenter {

    private final HelpPresenter helpPresenter;

    public Presenter(HelpPresenter helpPresenter) {
        this.helpPresenter = helpPresenter;
    }

    public void printToScreen(String totalOutput) {
        System.out.println(totalOutput);
    }

    private void printToFile(String output) {
    }

    public void printHelpRequest(RequestMessage message) {
        helpPresenter.printHelpRequest(message);
    }
}
