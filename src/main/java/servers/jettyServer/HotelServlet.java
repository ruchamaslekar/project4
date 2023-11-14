package servers.jettyServer;

import com.google.gson.JsonObject;
import hotelData.Hotel;
import hotelData.HotelDetails;
import org.apache.commons.text.StringEscapeUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class HotelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try{
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            JsonObject responseJson = new JsonObject();
            HotelDetails hotelDetails = (HotelDetails) getServletContext().getAttribute("hotel");
            String hotelId = request.getParameter("hotelId");
            hotelId = StringEscapeUtils.escapeHtml4(hotelId);
            if(hotelId == null){
                responseJson.addProperty("hotelId", "invalid");
                responseJson.addProperty("success", false);
            }
            else {
                Hotel hotel  = hotelDetails.getHotel(hotelId);
                if(hotel != null) {
                    responseJson.addProperty("hotelId", hotelId);
                    responseJson.addProperty("name", hotel.getHotelName());
                    responseJson.addProperty("addr", hotel.getAddress());
                    responseJson.addProperty("city", hotel.getCity());
                    responseJson.addProperty("state", hotel.getState());
                    responseJson.addProperty("lat", hotel.getLatitude());
                    responseJson.addProperty("lng", hotel.getLongitude());
                    responseJson.addProperty("success", true);
                } else {
                    responseJson.addProperty("hotelId", "invalid");
                    responseJson.addProperty("success", false);
                }
            }
            response.getWriter().println(responseJson);
            out.flush();
        } catch(Exception e) {
            System.out.println("Error"+e);
        }
    }
}
