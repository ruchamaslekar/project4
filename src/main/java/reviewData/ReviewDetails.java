package reviewData;

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
    public List<Review> getReviews(String hotelId) {
        List<Review> reviews = reviewMap.get(hotelId);
        if(reviews != null) {
            return Collections.unmodifiableList(reviewMap.get(hotelId));
        }
        else {
            return Collections.emptyList();
        }
    }
}
