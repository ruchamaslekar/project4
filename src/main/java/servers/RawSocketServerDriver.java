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
import servers.rawSocketHttpServer.RawSocketServer;

/** Driver class for running the RawSocketServer.
 * Create a jar file from this class. */
public class RawSocketServerDriver {
    public static void main(String[] args) {
        // FILL IN CODE:
        // Unlike the servers, the drivers will be specific to the hotel/reviews project.
        // Load hotel and review data to ThreadSafeHotelData (or whatever you called such class(es) earlier)
        //create HTTPRAWSOCKETSERVER OBJECT
        //map /hotel with hotelhandler
        //call start object here
        // Create the RawSocketServer,
        // call addMapping for different endpoints,
        // set the resource for each endpoint (such as ThreadSafeHotelData)
        // and run the server

        /** Loading data for ProgramArgumentParser */
        ProgramArgumentParser programParser = new ProgramArgumentParser();
        programParser.parseArgs(args);

        /**Loading data for Hotel */
        HotelParser parser = new HotelParser();
        HotelDetails hotelDetails = new ThreadSafeHotelDetails();
        parser.parseHotelJson(programParser.getArgument("-hotels"),hotelDetails);

        /** Loading data for Reviews */
        MultithreadedDirectoryParser directoryParser = new MultithreadedDirectoryParser();
        ReviewDetails reviewDetails = new ThreadSafeReviewDetails();
        directoryParser.parseDirectory(programParser, reviewDetails, hotelDetails);

        /** Loading data for InvertedIndex */
        InvertedIndex invertedIndex = new ThreadSafeInvertedIndex();
        InvertedIndexParser indexParser = new InvertedIndexParser();
        indexParser.ParseDirectory(programParser.getArgument("-reviews"),invertedIndex,reviewDetails);

        /** Creating object of RawSocketServer */
        RawSocketServer rawSocketServer = new RawSocketServer((Integer.parseInt(programParser.getArgument("-threads"))));

        /** Setting ServletHandlers */
        rawSocketServer.addMapping("hotelInfo", "HotelHandler");
        rawSocketServer.addMapping("reviews", "ReviewHandler");
        rawSocketServer.addMapping("index", "WordHandler");
        rawSocketServer.addMapping("weather", "WeatherHandler");

        /** Setting resources map */
        rawSocketServer.setResourceAttribute("HotelHandler",hotelDetails);
        rawSocketServer.setResourceAttribute("ReviewHandler",reviewDetails);
        rawSocketServer.setResourceAttribute("WordHandler",invertedIndex);
        rawSocketServer.setResourceAttribute("WeatherHandler",hotelDetails);

        /** Starting the server */
        rawSocketServer.start();
    }
}
