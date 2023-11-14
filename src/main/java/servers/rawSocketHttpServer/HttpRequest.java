package servers.rawSocketHttpServer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.text.StringEscapeUtils;

/** A class that represents http request */
public class HttpRequest {
    // FILL IN CODE
    // Store instance variables such as type of the request ("GET"),
    // a path, a version of the protocol, and also store query parameters of the request in a map

    // This class must be general, do NOT store anything related to hotels/reviews here
    //HttpRequest
    //store map there is will
    //param1 -> value1
    //param2 -> value2
    //take a request and parse it

    private String method;
    private String path;
    private String protocol;
    private final Map<String, String> queryParameters;

    public HttpRequest(String request) {
        queryParameters = new HashMap<>();
        parseRequest(request);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    public void parseRequest(String request) {
        try {
            String[] requestComponents = request.split(" ");
            method = requestComponents[0];
            parsePath(requestComponents[1]);
            parseProtocol(requestComponents[2]);

        } catch (Exception ex) {
            System.err.println("Error parsing request: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void parsePath(String pathComponent) {
        if (pathComponent.startsWith("/")) {
            String[] handlerDetails = pathComponent.split("\\?");
            path = StringEscapeUtils.escapeHtml4(handlerDetails[0].substring(1));
            if (handlerDetails.length > 1) {
                parseParameters(handlerDetails[1]);
            }
        }
    }

    private void parseParameters(String parameters) {
        String[] keyValuePairs = parameters.split("&");
        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split("=");
            String key = StringEscapeUtils.escapeHtml4(keyValue[0]);
            String value = (keyValue.length == 2) ? StringEscapeUtils.escapeHtml4(keyValue[1]) : null;
            if (key != null && !key.isEmpty()) {
                queryParameters.put(key, value);
            }
        }
    }

    private void parseProtocol(String protocolComponent) {
        if (protocolComponent.startsWith("HTTP")) {
            protocol = protocolComponent;
        }
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", protocol='" + protocol + '\'' +
                ", queryParameters=" + queryParameters +
                '}';
    }
}
