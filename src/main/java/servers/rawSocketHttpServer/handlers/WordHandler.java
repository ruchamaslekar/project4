package servers.rawSocketHttpServer.handlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import invertedIndexData.ReviewFrequency;
import invertedIndexData.ThreadSafeInvertedIndex;
import servers.rawSocketHttpServer.HttpHandler;
import servers.rawSocketHttpServer.HttpRequest;
import servers.rawSocketHttpServer.HttpResponse;
import java.util.Set;


public class WordHandler implements HttpHandler {
    private ThreadSafeInvertedIndex threadSafeInvertedIndex;
    @Override
    public void processRequest(HttpRequest request, HttpResponse response) {
        JsonObject responseJson = new JsonObject();
        if(request.getQueryParameters().get("word") == null || request.getQueryParameters().get("word").isEmpty()) {
            responseJson.addProperty("word", "invalid");
            responseJson.addProperty("success", false);
            response.sendPageNotFoundResponse(responseJson.toString());
        }else if(request.getQueryParameters().get("num") == null || request.getQueryParameters().get("num").isEmpty()) {
            responseJson.addProperty("num", "invalid");
            responseJson.addProperty("success", false);
            response.sendPageNotFoundResponse(responseJson.toString());
        }else {
            String word = request.getQueryParameters().get("word");
            int numOfReviews = (Integer.parseInt(request.getQueryParameters().get("num"))) ;
            JsonArray array = new JsonArray();
            Set<ReviewFrequency> invertedIndexResponse = threadSafeInvertedIndex.searchByWord(word,numOfReviews);
            if (invertedIndexResponse != null) {
                ReviewFrequency[] setArray = invertedIndexResponse.toArray(new ReviewFrequency[0]);
                responseJson.addProperty("word", word);
                for(ReviewFrequency reviewFrequency : setArray) {
                    if (reviewFrequency != null) {
                        JsonObject reviewJson = new JsonObject();
                        reviewJson.addProperty("reviewId", reviewFrequency.getReview().getReviewId());
                        reviewJson.addProperty("title", reviewFrequency.getReview().getTitle());
                        reviewJson.addProperty("user", reviewFrequency.getReview().getUserNickname());
                        reviewJson.addProperty("reviewText", reviewFrequency.getReview().getReviewText());
                        reviewJson.addProperty("date", reviewFrequency.getReview().getDate().toString());
                        array.add(reviewJson);
                        responseJson.add("reviews", array);
                    }
                }
                responseJson.addProperty("success", true);
                System.out.println(invertedIndexResponse.toString());
                response.sendResponse(responseJson.toString());
            } else {
                responseJson.addProperty("success", false);
                responseJson.addProperty("word", "invalid");
//                response.sendPageNotFoundResponse(responseJson.toString());
            }
        }
    }
    @Override
    public void setAttribute(Object data) {
        this.threadSafeInvertedIndex = (ThreadSafeInvertedIndex) data;
    }
}
