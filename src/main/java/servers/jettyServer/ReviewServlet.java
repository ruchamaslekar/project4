package servers.jettyServer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.text.StringEscapeUtils;
import reviewData.Review;
import reviewData.ReviewDetails;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ReviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        String hotelId = request.getParameter("hotelId");
        int numOfReviews = (Integer.parseInt(request.getParameter("num"))) ;
        hotelId = StringEscapeUtils.escapeHtml4(hotelId);
        JsonArray array = new JsonArray();
        JsonObject responseJson = new JsonObject();
        JsonObject responseJson1 = new JsonObject();
        if(hotelId != null && !hotelId.isEmpty()) {
            ReviewDetails reviewDetails = (ReviewDetails) getServletContext().getAttribute("review");
            List<Review> reviews = reviewDetails.getReviews(hotelId,numOfReviews);
            if(reviews == null ){
                responseJson.addProperty("success",false);
                responseJson.addProperty("hotelId","invalid");
            }
            else{
                responseJson.addProperty("success","true");
                responseJson.addProperty("hotelId",hotelId);
                for(Review review: reviews){
                    responseJson1.addProperty("reviewId",review.getReviewId());
                    responseJson1.addProperty("reviewText",review.getReviewText());
                    responseJson1.addProperty("rating",review.getRatingOverall());
                    array.add(responseJson1);
                    responseJson.add("reviews", array);
                }

            }
        } else{
            responseJson.addProperty("success:",false);
            responseJson.addProperty("hotelId:","invalid");
        }
        out.println(responseJson);
        out.flush();
    }

}
