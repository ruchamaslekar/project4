package servers.rawSocketHttpServer.handlers;

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
        if (request.getQueryParameters().containsKey("hotelId")) {
            response.setStatusCode(200, "HTTP/1.1 200 OK" + System.lineSeparator());
            response.setContentType("ContentType: application/json" + System.lineSeparator() + System.lineSeparator());
            String hotelId = request.getQueryParameters().get("hotelId");
            int numOfReviews = (Integer.parseInt(request.getQueryParameters().get("num"))) ;
            if (hotelId == null) {
                hotelId = "-1";
            }
            else{
                List<Review> reviewsResponse = threadSafeReviewDetails.getReviews(hotelId,numOfReviews);
                response.sendReviewResponse(reviewsResponse);
            }
        }
    }
    @Override
    public void setAttribute(Object data) {
        this.threadSafeReviewDetails = (ThreadSafeReviewDetails) data;
    }
}
