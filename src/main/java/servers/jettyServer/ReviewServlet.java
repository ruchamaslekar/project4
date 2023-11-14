package servers.jettyServer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.text.StringEscapeUtils;
import reviewData.Review;
import reviewData.ReviewDetails;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class ReviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            JsonObject responseJson = new JsonObject();
            ReviewDetails reviewDetails = (ReviewDetails) getServletContext().getAttribute("review");
            String hotelId = request.getParameter("hotelId");
            hotelId = StringEscapeUtils.escapeHtml4(hotelId);
            if (hotelId == null ) {
                responseJson.addProperty("hotelId", "invalid");
                responseJson.addProperty("success", false);
                response.getWriter().println(responseJson);
                out.flush();
            } else if(request.getParameter("num")== null || request.getParameter("num" ).isEmpty()){
                responseJson.addProperty("num", "invalid");
                responseJson.addProperty("success", false);
                response.getWriter().println(responseJson);
                out.flush();
            }
            else {
                int numOfReviews = (Integer.parseInt(request.getParameter("num")));
                List<Review> reviews = reviewDetails.getReviews(hotelId, numOfReviews);
                if (reviews.isEmpty()) {
                    responseJson.addProperty("hotelId", "invalid");
                    responseJson.addProperty("success", false);
                }else {
                    JsonArray array = new JsonArray();
                    responseJson.addProperty("hotelId", hotelId);
                    for (Review review : reviews) {
                        if(review!= null) {
                            JsonObject reviewJson = new JsonObject();
                            reviewJson.addProperty("user", review.getUserNickname());
                            reviewJson.addProperty("title", review.getTitle());
                            reviewJson.addProperty("reviewId", review.getReviewId());
                            reviewJson.addProperty("reviewText", review.getReviewText());
                            array.add(reviewJson);
                            responseJson.add("reviews", array);

                        }else {
                            responseJson.addProperty("success", false);
                            responseJson.addProperty("hotelId", "invalid");
                        }
                    }
                    responseJson.addProperty("success", true);
                }
                response.getWriter().println(responseJson);
                out.flush();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
