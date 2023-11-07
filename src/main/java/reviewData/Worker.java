package reviewData;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

/** Demonstrating ReviewParser class */
public class Worker implements Runnable {
    private final File filePath;
    private final Logger logger = LogManager.getLogger();
    private final ReviewDetails reviewDetails;
    private final Phaser phaser;
    private final List<Review> reviewList = new ArrayList<>();
    private String hotelId;
    public Worker(File filePath, ReviewDetails reviewDetails, Phaser phaser) {
        this.filePath = filePath;
        this.reviewDetails = reviewDetails;
        this.phaser = phaser;
    }
    @Override
    public void run() {
        try {
            /**Calling method to parse review json files*/
            parseReviewJson();
        }
        finally {
            /** Adding list of Review to reviewMap */
            reviewDetails.addReviews(hotelId,reviewList);
            logger.debug("Worker working on " + filePath + " finished work");
            phaser.arriveAndDeregister();
        }
    }
    /**
     * This method is parsing multiple review.json file
     * that contains info about reviews using GSON library.
     *
     */
    public void parseReviewJson() {
        /** Reading json files */
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject object = (JsonObject) JsonParser.parseReader(reader);
            JsonObject reviewDetailsObject = (JsonObject) object.get("reviewDetails");
            JsonObject reviewCollectionObject = (JsonObject) reviewDetailsObject.get("reviewCollection");
            JsonArray reviewArray = reviewCollectionObject.getAsJsonArray("review");
            /** Inserting each review into reviewList */
            for (JsonElement element : reviewArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                hotelId = jsonObject.get("hotelId").getAsString();
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
                reviewList.add(review);
            }
        } catch (IOException e) {
            System.out.println("File not found:" + e);
        }
    }
}