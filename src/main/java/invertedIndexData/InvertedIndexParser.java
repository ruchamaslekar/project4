package invertedIndexData;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import reviewData.Review;
import reviewData.ReviewDetails;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class InvertedIndexParser {
    private final List<Review> reviewList = new ArrayList<>();

    /**
     * Method to parse directory that contains json files
     * @param directory String
     * @param index index InvertedIndex
     * @param reviewDetails ReviewDetails
     */
    public void ParseDirectory(String directory, InvertedIndex index, ReviewDetails reviewDetails) {
        Path p = Paths.get(directory);
        try (DirectoryStream<Path> pathsInDir = Files.newDirectoryStream(p)) {
            for (Path path : pathsInDir) {
                /** print the name of each file in the directory*/
                if (Files.isRegularFile(path) && (path.toString().endsWith(".json"))) {
                    parseReviewJsonForInverted(path.toString(),index,reviewDetails);
                } else if (Files.isDirectory(path)) {
                    ParseDirectory(path.toString(),index,reviewDetails);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to parse JSON files
     * @param filePath String
     * @param invertedIndex InvertedIndex
     * @param reviewDetails ReviewDetails
     */
    public void parseReviewJsonForInverted(String filePath,InvertedIndex invertedIndex,ReviewDetails reviewDetails){
        /** Reading json files*/
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject object = (JsonObject) JsonParser.parseReader(reader);
            JsonObject reviewDetailsObject = (JsonObject) object.get("reviewDetails");
            JsonObject reviewCollectionObject = (JsonObject) reviewDetailsObject.get("reviewCollection");
            JsonArray reviewArray = reviewCollectionObject.getAsJsonArray("review");
            /** Inserting values in reviewMap */
            for (JsonElement element : reviewArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                String hotelId = jsonObject.get("hotelId").getAsString();
                String reviewId = jsonObject.get("reviewId").getAsString();
                int ratingOverall = jsonObject.get("ratingOverall").getAsInt();
                String title = jsonObject.get("title").getAsString();
                String reviewText = jsonObject.get("reviewText").getAsString();
                String userNickname = jsonObject.get("userNickname").getAsString();
                if (userNickname.isEmpty()) {
                    userNickname = "Anonymous";
                }
                String date = jsonObject.get("reviewSubmissionTime").getAsString();
                Review review = new Review(hotelId, reviewId, ratingOverall, title, reviewText, userNickname, date);
                invertedIndex.addReviews(review);
            }
        } catch (IOException e) {
            System.out.println("File not found:" + e);
        }
    }
}
