package servers.rawSocketHttpServer;

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

    /** Constructor HttpRequest
     * @param request String
     */
    public HttpRequest(String request) {
        queryParameters = new HashMap<>();
        parseRequest(request);
    }

    /** Getter for Method */
    public String getMethod() {
        return method;
    }

    /** Getter for Path */
    public String getPath() {
        return path;
    }

    /** Getter for QueryParameters */
    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    /** Method to parse a request
     * @param request String
     */
    public void parseRequest(String request) {
        try{
            String[] requestArray = request.split(" ");
            method = requestArray[0];
            parsePath(requestArray[1]);
            parseProtocol(requestArray[2]);
        }catch (Exception e) {
            System.err.println("Error" + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Method to parse a request path
     * @param pathComponent String
     */
    private void parsePath(String pathComponent) {
        if (pathComponent.startsWith("/")) {
            String[] pathArray = pathComponent.split("\\?");
            path = StringEscapeUtils.escapeHtml4(pathArray[0].substring(1));
            if (pathArray.length > 1) {
                parseParameters(pathArray[1]);
            }
        }
    }

    /** Method to parse a request parameters
     * @param parameters String
     */
    private void parseParameters(String parameters) {
        String[] parameterArray = parameters.split("&");
        for (String pair : parameterArray) {
            String[] keyValuePair = pair.split("=");
            String key = StringEscapeUtils.escapeHtml4(keyValuePair[0]);
            String value = (keyValuePair.length == 2) ? StringEscapeUtils.escapeHtml4(keyValuePair[1]) : null;
            if (key != null && !key.isEmpty()) {
                queryParameters.put(key, value);
            }
        }
    }

    /** Method to parse a request protocol
     * @param protocolComponent String
     */
    private void parseProtocol(String protocolComponent) {
        if (protocolComponent.startsWith("HTTP")) {
            protocol = protocolComponent;
        }
    }

    /** toString() method to display HttpRequest */
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
