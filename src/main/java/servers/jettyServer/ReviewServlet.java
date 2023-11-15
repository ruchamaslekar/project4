package servers.jettyServer;

import com.google.gson.JsonObject;
import org.apache.commons.text.StringEscapeUtils;
import reviewData.ReviewDetails;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ReviewServlet extends HttpServlet {

    /**
     * Method to get request and writes Json response that contains reviews data
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            JsonObject responseJson = new JsonObject();
            ReviewDetails reviewDetails = (ReviewDetails) getServletContext().getAttribute("review");
            String hotelId = request.getParameter("hotelId");
            hotelId = StringEscapeUtils.escapeHtml4(hotelId);
            if(hotelId == null ) {
                responseJson.addProperty("hotelId", "invalid");
                responseJson.addProperty("success", false);
            }
            else if(request.getParameter("num")== null || request.getParameter("num" ).isEmpty()){
                responseJson.addProperty("num", "invalid");
                responseJson.addProperty("success", false);
            }else {
               int numOfReviews = (Integer.parseInt(request.getParameter("num")));
               responseJson = reviewDetails.getReviewsInJSONFormat(hotelId,numOfReviews);
           }
            response.getWriter().println(responseJson);
            out.flush();
        }catch(Exception e) {
            System.out.println("Error"+ e);
        }
    }
}
