package servers.rawSocketHttpServer.handlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import reviewData.Review;
import reviewData.ThreadSafeReviewDetails;
import servers.rawSocketHttpServer.HttpHandler;
import servers.rawSocketHttpServer.HttpRequest;
import servers.rawSocketHttpServer.HttpResponse;
import java.util.List;

public class ReviewHandler implements HttpHandler {
    private ThreadSafeReviewDetails threadSafeReviewDetails;
    @Override
    public void processRequest(HttpRequest request, HttpResponse response) {
        JsonObject responseJson = new JsonObject();
        if (request.getQueryParameters().get("hotelId") == null || request.getQueryParameters().get("hotelId").isEmpty()) {
            responseJson.addProperty("hotelId", "invalid");
            responseJson.addProperty("success", false);
            response.sendPageNotFoundResponse(responseJson.toString());
        } else if(request.getQueryParameters().get("num") == null || request.getQueryParameters().get("num").isEmpty()){
            responseJson.addProperty("num", "invalid");
            responseJson.addProperty("success", false);
            response.sendPageNotFoundResponse(responseJson.toString());
        } else {
            String hotelId = request.getQueryParameters().get("hotelId");
            int numOfReviews = (Integer.parseInt(request.getQueryParameters().get("num")));
            List<Review> reviewsResponse = threadSafeReviewDetails.getReviews(hotelId, numOfReviews);
            if (reviewsResponse.isEmpty()) {
                responseJson.addProperty("hotelId", "invalid");
                responseJson.addProperty("success", false);
                response.sendPageNotFoundResponse(responseJson.toString());
            } else {
                JsonArray array = new JsonArray();
                responseJson.addProperty("hotelId", hotelId);
                for (Review review : reviewsResponse) {
                    if (review != null) {
                        JsonObject reviewJson = new JsonObject();
                        reviewJson.addProperty("user", review.getUserNickname());
                        reviewJson.addProperty("title", review.getTitle());
                        reviewJson.addProperty("reviewId", review.getReviewId());
                        reviewJson.addProperty("reviewText", review.getReviewText());
                        array.add(reviewJson);
                        responseJson.add("reviews", array);
                    } else {
                        responseJson.addProperty("success", false);
                        responseJson.addProperty("hotelId", "invalid");
                        response.sendPageNotFoundResponse(response.toString());
                    }
                }
                responseJson.addProperty("success", true);
                System.out.println(reviewsResponse.toString());
                response.sendResponse(responseJson.toString());
            }
        }
    }
    @Override
    public void setAttribute(Object data) {
        this.threadSafeReviewDetails = (ThreadSafeReviewDetails) data;
    }
}
