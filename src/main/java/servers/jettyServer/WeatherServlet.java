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
                Hotel hotel = hotelDetails.getHotel(hotelId);
                if (hotel != null) {
                    String latitude = hotel.getLatitude();
                    String longitude = hotel.getLongitude();
                    JsonObject weatherDetails = WeatherFetcher.fetch("api.open-meteo.com","/v1/forecast?latitude="+latitude+"&longitude="+longitude+"&current_weather=true");
                    System.out.println(weatherDetails.toString());
                    responseJson.addProperty("hotelId", hotelId);
                    responseJson.addProperty("name", hotel.getHotelName());
                    responseJson.addProperty("addr", hotel.getAddress());
                    responseJson.addProperty("city", hotel.getCity());
                    responseJson.addProperty("state", hotel.getState());
                    responseJson.addProperty("lat", hotel.getLatitude());
                    responseJson.addProperty("lng", hotel.getLongitude());
                    responseJson.add("weather", weatherDetails);
                    responseJson.addProperty("success", true);
                    response.getWriter().println(responseJson);
                    out.flush();
                } else {
                    responseJson.addProperty("hotelId", "invalid");
                    responseJson.addProperty("success", false);
                    response.getWriter().println(responseJson);
                    out.flush();
                }
            }

        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }
}
