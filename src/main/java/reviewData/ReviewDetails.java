package reviewData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import hotelData.Hotel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Demonstrating ReviewMap class */
public class ReviewDetails {
    /** Initializing reviewMap */
    private final Map<String, List<Review>> reviewMap  = new HashMap<>();

    /** This method is adding data into reviewMap
     * @param hotelId hotelId
     * @param  reviewList reviewList
     * */
    public void addReviews(String hotelId, List<Review> reviewList){
        reviewList.sort((r1, r2) -> {
            if (r2.getDate().equals(r1.getDate())) {
                return r1.getReviewId().compareTo(r2.getReviewId());
            } else {
                return r2.getDate().compareTo(r1.getDate());
            }
        });
        reviewMap.put(hotelId,reviewList);
    }

    /** This method is printing Review data based on hotelID
     * @param hotelId hotelId
     * */
    public List<Review> getReviews(String hotelId,int numOfReviews) {
        List<Review> reviews = reviewMap.get(hotelId);
        if (reviews != null) {
            if (numOfReviews >= 0 && numOfReviews <= reviews.size()) {
                return Collections.unmodifiableList(reviews.subList(0, numOfReviews));
            } else {
                return Collections.unmodifiableList(reviews);
            }
        }
        else {
            return Collections.emptyList();
        }
    }

    public JsonObject getReviewsInJSONFormat(String hotelId,int numOfReviews) {
        JsonObject responseJson = new JsonObject();
            List<Review> reviews = this.getReviews(hotelId, numOfReviews);
            if (reviews.isEmpty()) {
                responseJson.addProperty("hotelId", "invalid");
                responseJson.addProperty("success", false);
            } else {
                JsonArray array = new JsonArray();
                responseJson.addProperty("hotelId", hotelId);
                for (Review review : reviews) {
                    if (review != null) {
                        JsonObject reviewJson = new JsonObject();
                        reviewJson.addProperty("user", review.getUserNickname());
                        reviewJson.addProperty("title", review.getTitle());
                        reviewJson.addProperty("reviewId", review.getReviewId());
                        reviewJson.addProperty("reviewText", review.getReviewText());
                        array.add(reviewJson);
                        responseJson.add("reviews", array);
                        responseJson.addProperty("success", true);
                    } else {
                        responseJson.addProperty("success", false);
                        responseJson.addProperty("hotelId", "invalid");
                    }
                }
            }

        return responseJson;
    }
}
