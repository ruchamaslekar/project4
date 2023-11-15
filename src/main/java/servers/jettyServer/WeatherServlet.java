package servers.jettyServer;

import com.google.gson.JsonObject;
import hotelData.Hotel;
import hotelData.HotelDetails;
import org.apache.commons.text.StringEscapeUtils;
import weatherData.WeatherFetcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class WeatherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            JsonObject responseJson = new JsonObject();
            HotelDetails hotelDetails = (HotelDetails) getServletContext().getAttribute("weather");
            String hotelId = request.getParameter("hotelId");
            hotelId = StringEscapeUtils.escapeHtml4(hotelId);
            if (hotelId == null) {
                responseJson.addProperty("hotelId", "invalid");
                responseJson.addProperty("success", false);
            } else {
                responseJson = hotelDetails.getWeatherDataInJSONFormat(hotelId);
                response.getWriter().println(responseJson);
                out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }
}
