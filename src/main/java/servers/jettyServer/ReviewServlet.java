package servers.jettyServer;

import com.google.gson.JsonObject;
import hotelData.Hotel;
import hotelData.HotelDetails;
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
        hotelId = StringEscapeUtils.escapeHtml4(hotelId);
        if (hotelId == null || hotelId.isEmpty()) {
            out.println("incorrect input");
        }
        ReviewDetails ht= (ReviewDetails) getServletContext().getAttribute("review");
        JsonObject Jobject = new JsonObject();
        Jobject.addProperty("hotelId",hotelId);
        List<Review> review= ht.getReviews(hotelId);
            for (Review r : review) {
                out.println(review.toString());
            }

        }

}
