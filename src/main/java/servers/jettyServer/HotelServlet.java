package servers.jettyServer;

import com.google.gson.JsonObject;
import hotelData.HotelDetails;
import org.apache.commons.text.StringEscapeUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HotelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try{
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            JsonObject responseJson = new JsonObject();
            HotelDetails hotelDetails = (HotelDetails) getServletContext().getAttribute("hotel");
            String hotelId = request.getParameter("hotelId");
            hotelId = StringEscapeUtils.escapeHtml4(hotelId);
            if(hotelId == null){
                responseJson.addProperty("hotelId", "invalid");
                responseJson.addProperty("success", false);
            } else {
                responseJson = hotelDetails.getHotelInJSONFormat(hotelId);
            }
            response.getWriter().println(responseJson);
            out.flush();
            } catch (IOException ex) {
                System.out.println("Error"+ex);
        }
    }
}
