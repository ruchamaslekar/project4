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
        if (hotelId == null || hotelId.isEmpty()) {
            out.println("incorrect input");
        }
        HotelDetails ht = (HotelDetails) getServletContext().getAttribute("hotel");
        JsonObject jObject = new JsonObject();
        jObject.addProperty("hotelId",hotelId);
        Hotel hotel= ht.getHotel(hotelId);
        out.println(hotel.toString());
    }
}
