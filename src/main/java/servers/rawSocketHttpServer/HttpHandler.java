package servers.rawSocketHttpServer;

import java.util.Map;

/** Contains a method to process http request from the client.  */
public interface HttpHandler {

    /**
     * Handles http get request from the client
     * @param request client's http request
     * @param response http response
     */
    void processRequest(HttpRequest request, HttpResponse response);

    void setAttribute(Object data);

    // Add additional methods as needed - this interface must be general - do NOT include anything related to hotels/reviews here

}
