package servers.rawSocketHttpServer.handlers;

import com.google.gson.JsonObject;
import reviewData.ThreadSafeReviewDetails;
import servers.rawSocketHttpServer.HttpHandler;
import servers.rawSocketHttpServer.HttpRequest;
import servers.rawSocketHttpServer.HttpResponse;

public class ReviewHandler implements HttpHandler {
    private ThreadSafeReviewDetails threadSafeReviewDetails;
    @Override
    public void processRequest(HttpRequest request, HttpResponse response) {
        JsonObject responseJson = new JsonObject();
        if(request.getQueryParameters().get("hotelId") == null || request.getQueryParameters().get("hotelId").isEmpty()) {
            responseJson.addProperty("hotelId", "invalid");
            responseJson.addProperty("success", false);
            response.sendPageNotFoundResponse(responseJson.toString());
        }else if(request.getQueryParameters().get("num") == null || request.getQueryParameters().get("num").isEmpty()){
            responseJson.addProperty("num", "invalid");
            responseJson.addProperty("success", false);
            response.sendPageNotFoundResponse(responseJson.toString());
        }else {
            String hotelId = request.getQueryParameters().get("hotelId");
            int numOfReviews = (Integer.parseInt(request.getQueryParameters().get("num")));
            responseJson = threadSafeReviewDetails.getReviewsInJSONFormat(hotelId,numOfReviews);
            System.out.println(responseJson.toString());
            response.sendResponse(responseJson.toString());
        }
    }
    @Override
    public void setAttribute(Object data) {
        this.threadSafeReviewDetails = (ThreadSafeReviewDetails) data;
    }
}
