package servers;

import hotelData.HotelDetails;
import hotelData.HotelParser;
import hotelData.ThreadSafeHotelDetails;
import invertedIndexData.InvertedIndex;
import invertedIndexData.InvertedIndexParser;
import invertedIndexData.ThreadSafeInvertedIndex;
import reviewData.MultithreadedDirectoryParser;
import reviewData.ReviewDetails;
import reviewData.ThreadSafeReviewDetails;
import servers.jettyServer.*;

/** Driver class for running the Jetty server.
 * Create a jar file from this class.  */
public class JettyServerDriver {
    public static void main(String[] args) throws Exception {
        // FILL IN CODE:
        // Unlike the servers, the drivers will be specific to the hotel/reviews project.

        // Load hotel and review data to ThreadSafeHotelData (or whatever you called such class(es) earlier)

        // Create the JettyServer, call addMapping for the endpoints to map them to servlets,
        // pass ThreadSafeHotelData etc as the resource,
        // then start the server
        ProgramArgumentParser programParser = new ProgramArgumentParser();
        programParser.parseArgs(args);

        /**HotelParsing*/
        HotelParser parser = new HotelParser();
        HotelDetails hotelDetails = new ThreadSafeHotelDetails();
        parser.parseHotelJson(programParser.getArgument("-hotels"),hotelDetails);

        /**ReviewParsing*/
        MultithreadedDirectoryParser directoryParser = new MultithreadedDirectoryParser();
        ReviewDetails reviewDetails = new ThreadSafeReviewDetails();
        directoryParser.parseDirectory(programParser, reviewDetails, hotelDetails);

        /**InvertedIndexParsing*/
        InvertedIndex invertedIndex = new ThreadSafeInvertedIndex();
        InvertedIndexParser indexParser = new InvertedIndexParser();
        indexParser.ParseDirectory(programParser.getArgument("-reviews"),invertedIndex,reviewDetails);


        /** JettyServer */
        JettyServer server = new JettyServer();

        /** Setting resources map */
        server.addMapping("/hotelInfo", HotelServlet.class);
        server.addMapping("/reviews", ReviewServlet.class);
        server.addMapping("/index", InvertedIndexServlet.class);
        server.addMapping("/weatherInfo", WeatherServlet.class);

        /** Setting resources attributes */
        server.setResourceAttribute("hotel", hotelDetails);
        server.setResourceAttribute("review", reviewDetails);
        server.setResourceAttribute("word", invertedIndex);

        server.start();

    }
}
