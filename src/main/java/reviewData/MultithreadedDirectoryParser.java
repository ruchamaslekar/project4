package reviewData;

import hotelData.HotelDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import servers.ProgramArgumentParser;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;


/** Demonstrating DirectoryParser class */
public class MultithreadedDirectoryParser {
    private final Phaser phaser = new Phaser();
    private final Logger logger = LogManager.getLogger();

    /**
     * This method is parsing directory and subdirectories containing json files
     * This method would call parseReviewJson method
     * @param directory to search json file into
     */
    public void recursivelyParseDirectory(String directory, ReviewDetails reviewDetails,ExecutorService poolManager){
        Path p = Paths.get(directory);
        try (DirectoryStream<Path> pathsInDir = Files.newDirectoryStream(p)) {
            for (Path path : pathsInDir) {
                /** print the name of each file in the directory*/
                if (Files.isRegularFile(path) && (path.toString().endsWith(".json"))) {
                        Worker worker = new Worker(path.toFile(), reviewDetails, phaser);
                        poolManager.submit(worker);
                        phaser.register();
                        logger.debug("Created a worker for " + path);
                    }
                else if (Files.isDirectory(path)) {
                    recursivelyParseDirectory(path.toString(),reviewDetails,poolManager);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method is calling recursivelyParseDirectory method
     * Initializing ExecutorService
     * Calling awaitAdvance method for phaser
     * Calling shutdown method for ExecutorService
     */
    public void parseDirectory(ProgramArgumentParser parser,ReviewDetails reviewDetails, HotelDetails hotelDetails){
        if (hotelDetails != null) {
            ExecutorService poolManager = Executors.newFixedThreadPool(Integer.parseInt(parser.getArgument("-threads")));
            recursivelyParseDirectory(parser.getArgument("-reviews"), reviewDetails, poolManager);
            phaser.awaitAdvance(0);
            poolManager.shutdown();
            try {
                poolManager.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

}



