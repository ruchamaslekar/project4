package servers.rawSocketHttpServer;

import java.util.HashMap;
import java.util.Map;

/** Implements the http server (that processes GET requests) using raw sockets.
 * RawSocketServer should be general and not contain anything related to hotels/reviews.
 * */
public class RawSocketServer {
    private static final int PORT = 8080;
    private Map<String, String> handlers = new HashMap<>(); // maps each endpoint/url path to the appropriate handler
    // Think of handlers as "servlets" - helpers of the server

    // FILL IN CODE: you may want to add another data structure to store "resources"
    // (do not store ThreadSafeHotelData here, the server must be general - you can store "resource/s" as Object(s)

    /**
     * Maps a given URL path/endpoint to the name of the class that will handle requests coming at this endpoint
     * @param path end point
     * @param className  name of the handler class
     */
    private void addMapping(String path, String className){
        handlers.put(path,className);
    }

    // FILL IN CODE: add additional methods as needed

    public void start() {
        // start the server
        // FILL IN CODE

    }
}
