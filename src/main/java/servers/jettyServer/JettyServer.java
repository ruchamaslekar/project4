package servers.jettyServer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

/** This class uses Jetty & servlets to implement server responding to Http GET requests */
public class JettyServer {
    // FILL IN CODE
    private static final int PORT = 8090;
    private final Server jettyServer;  // Jetty server
    private final ServletContextHandler handlers = new ServletContextHandler(ServletContextHandler.SESSIONS); //handler for the jetty server

    // FILL IN CODE: add other variables and methods as needed:
    // store a resource/resources you need to pass to the servlets
    // (such as ThreadSafeHotelData, but you want to store resources as objects to make the code more general).
    public JettyServer() {
        jettyServer = new Server(PORT);
        jettyServer.setHandler(handlers);
    }
    /**
     * Maps a given URL path/endpoint to the name of the servlet class that will handle requests coming at this endpoint
     * @param path end point
     * @param className  name of the servlet class
     */
    public void addMapping(String path, Class className) {
        // FILL IN CODE: call the method on the handler that adds the mapping between the path and the servlet
         handlers.addServlet(className, path);
    }

    // FILL IN CODE: add other methods as needed
    public void setResourceAttribute(String resourceName, Object resource) {
        handlers.setAttribute(resourceName, resource);
    }

    /**
     * Function that starts the server
     * @throws Exception throws exception if access failed
     */
    public  void start() throws Exception {
        // FILL IN CODE: run the jetty server
        try {
            jettyServer.start();
            jettyServer.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Server server = new Server(PORT);
//        ServletContextHandler handlers= new ServletContextHandler();
//        handlers.addServlet(HotelServlet.class, "/hotelInfo");
//        handlers.addServlet(ReviewServlet.class, "/reviewInfo");
//        handlers.addServlet(InvertedIndexServlet.class, "/wordInfo");
//        handlers.setAttribute("hotel",resources.get("hotel"));
//        handlers.setAttribute("review",reviewDetails);
//        handlers.setAttribute("word",invertedIndex);
//        server.setHandler(handlers);
//        server.start();
//        server.join();
//        addMapping("/hotelInfo", "HotelServlet");
//        addMapping("/reviewInfo", "ReviewServlet");
//        addMapping("/wordInfo", "InvertedIndexServlet");


        /**Hotel data*/
//        HotelParser parser = new HotelParser();
//        HotelDetails hotelDetails = new HotelDetails();
//        handlers.addServlet(HotelServlet.class, "/hotelInfo");
//        parser.parseHotelJson("/Users/ruchamaslekar/Software Development/project4-ruchamaslekar/input/hotels/hotels.json",hotelDetails);
//        server.setHandler(handlers);
//        handlers.setAttribute("hotel",hotelDetails);

        /**Review data*/
//        ExecutorService poolManager = Executors.newFixedThreadPool(3);
//        MultithreadedDirectoryParser parser1 = new MultithreadedDirectoryParser();
//        HotelParser parser = new HotelParser();
//        HotelDetails hotelDetails = new ThreadSafeHotelDetails();
//        parser.parseHotelJson("/Users/ruchamaslekar/Software Development/project4-ruchamaslekar/input/hotels/hotels.json",hotelDetails);
//        ReviewDetails reviewDetails = new ThreadSafeReviewDetails();
//        handlers.addServlet(ReviewServlet.class, "/reviewInfo");
//        parser1.recursivelyParseDirectory("/Users/ruchamaslekar/Software Development/project4-ruchamaslekar/input/reviews",reviewDetails,poolManager);
//        server.setHandler(handlers);
//        handlers.setAttribute("review",reviewDetails);

    }
}
