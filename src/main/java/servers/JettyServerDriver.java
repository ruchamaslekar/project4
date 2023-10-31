package servers;

/** Driver class for running the Jetty server.
 * Create a jar file from this class.  */
public class JettyServerDriver {
    public static void main(String[] args) {
        // FILL IN CODE:
        // Unlike the servers, the drivers will be specific to the hotel/reviews project.

        // Load hotel and review data to ThreadSafeHotelData (or whatever you called such class(es) earlier)

        // Create the JettyServer, call addMapping for the endpoints to map them to servlets,
        // pass ThreadSafeHotelData etc as the resource,
        // then start the server
    }
}
