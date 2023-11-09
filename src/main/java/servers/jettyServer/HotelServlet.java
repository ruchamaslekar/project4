package servers.jettyServer;

import com.google.gson.JsonObject;
import hotelData.Hotel;
import hotelData.HotelDetails;
import org.apache.commons.text.StringEscapeUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HotelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        String hotelId = request.getParameter("hotelId");
        hotelId = StringEscapeUtils.escapeHtml4(hotelId);
        JsonObject responseJson = new JsonObject();
        if (hotelId != null && !hotelId.isEmpty()) {
            HotelDetails hotelDetails = (HotelDetails) getServletContext().getAttribute("hotel");
            Hotel hotel  = hotelDetails.getHotel(hotelId);
            if(hotel == null ){
                responseJson.addProperty("success",false);
                responseJson.addProperty("hotelId","invalid");
                out.println(responseJson);
            }
            else {
                responseJson.addProperty("success","true");
                responseJson.addProperty("hotelId",hotelId);
                responseJson.addProperty("name",hotel.getHotelName());
                responseJson.addProperty("addr",hotel.getAddress());
                responseJson.addProperty("city",hotel.getCity());
                responseJson.addProperty("lat",hotel.getLatitude());
                responseJson.addProperty("lng",hotel.getLongitude());
                out.println(responseJson.toString());
            }
            out.flush();
        } else {
            responseJson.addProperty("success",false);
            responseJson.addProperty("hotelId","invalid");
            out.println(responseJson);
            out.flush();
        }

    }
}
