package servers.rawSocketHttpServer.handlers;

import com.google.gson.JsonObject;
import hotelData.Hotel;
import hotelData.ThreadSafeHotelDetails;
import servers.rawSocketHttpServer.HttpHandler;
import servers.rawSocketHttpServer.HttpRequest;
import servers.rawSocketHttpServer.HttpResponse;
import weatherData.WeatherFetcher;

import java.io.IOException;

public class WeatherHandler implements HttpHandler {
    private ThreadSafeHotelDetails threadSafeHotelDetails;
    @Override
    public void processRequest(HttpRequest request, HttpResponse response) {
        JsonObject responseJson = new JsonObject();
        if (request.getQueryParameters().get("hotelId") == null || request.getQueryParameters().get("hotelId").isEmpty()) {
            responseJson.addProperty("hotelId", "invalid");
            responseJson.addProperty("success", false);
            response.sendPageNotFoundResponse(responseJson.toString());
        }
        else {
            String hotelId = request.getQueryParameters().get("hotelId");
            Hotel hotelInfoResponse = threadSafeHotelDetails.getHotel(hotelId);
            if(hotelInfoResponse != null) {
                responseJson.addProperty("hotelId", hotelId);
                responseJson.addProperty("name", hotelInfoResponse.getHotelName());
                responseJson.addProperty("addr", hotelInfoResponse.getAddress());
                responseJson.addProperty("city", hotelInfoResponse.getCity());
                responseJson.addProperty("state", hotelInfoResponse.getState());
                responseJson.addProperty("lat", hotelInfoResponse.getLatitude());
                responseJson.addProperty("lng", hotelInfoResponse.getLongitude());
                JsonObject weatherDetails = WeatherFetcher.fetch("api.open-meteo.com","/v1/forecast?latitude="+hotelInfoResponse.getLatitude()+"&longitude="+hotelInfoResponse.getLongitude()+"&current_weather=true");
                responseJson.add("weather", weatherDetails);
                responseJson.addProperty("success", true);
                response.sendResponse(responseJson.toString());
            } else {
                responseJson.addProperty("hotelId", "invalid");
                responseJson.addProperty("success", false);
                response.sendPageNotFoundResponse(responseJson.toString());
            }
            System.out.println(hotelInfoResponse.toString());
        }
    }
    @Override
    public void setAttribute(Object data) {
        this.threadSafeHotelDetails = (ThreadSafeHotelDetails) data;
    }
}
