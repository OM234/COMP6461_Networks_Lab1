package input.parser.redirect;

import message.HTTPResponse;

public class Redirector {

    public boolean redirectNecessary(HTTPResponse response) {
        String strResponse = response.getFullHTTPResponse();
        String HTTPCode = getHttpCode(strResponse);
        if(isHTTPCodeRedirect(HTTPCode)) {
            return true;
        } else {
            return false;
        }
    }

    private String getHttpCode(String strResponse) {
        return strResponse
                .lines()
                .findFirst()
                .get()
                .split(" ")[1];
    }

    private boolean isHTTPCodeRedirect(String HTTPCode) {
        return HTTPCode.charAt(0) == '3';
    }

    public String getRedirectURL(HTTPResponse response) {
        String strResponse = response.getFullHTTPResponse();
        return getRedirectURLFromResponse(strResponse);
    }

    private String getRedirectURLFromResponse(String strResponse) {
        return strResponse
                .lines()
                .filter(line -> line.contains("Location:") || line.contains("location:"))
                .findFirst()
                .get()
                .split(" ")[1];
    }
}
