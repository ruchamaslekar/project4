package servers.jettyServer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/** This class uses Jetty & servlets to implement server responding to Http GET requests */
public class JettyServer {
    // FILL IN CODE
    private static final int PORT = 8090;
    private Server jettyServer;  // Jetty server
    private ServletHandler handler; // handler for the jetty server
    // FILL IN CODE: add other variables and methods as needed:
    // store a resource/resources you need to pass to the servlets
    // (such as ThreadSafeHotelData, but you want to store resources as objects to make the code more general).

    public JettyServer() {
        jettyServer = new Server(PORT);
        handler = new ServletHandler();
        jettyServer.setHandler(handler);
    }
    /**
     * Maps a given URL path/endpoint to the name of the servlet class that will handle requests coming at this endpoint
     * @param path end point
     * @param className  name of the servlet class
     */
    private void addMapping(String path, String className){
        // FILL IN CODE: call the method on the handler that adds the mapping between the path and the servlet

    }

    // FILL IN CODE: add other methods as needed

    /**
     * Function that starts the server
     * @throws Exception throws exception if access failed
     */
    public  void start() throws Exception {
        // FILL IN CODE: run the jetty server

    }
}
