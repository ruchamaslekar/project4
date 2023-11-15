package servers.rawSocketHttpServer.handlers;

import com.google.gson.JsonObject;
import hotelData.ThreadSafeHotelDetails;
import servers.rawSocketHttpServer.HttpHandler;
import servers.rawSocketHttpServer.HttpRequest;
import servers.rawSocketHttpServer.HttpResponse;

public class WeatherHandler implements HttpHandler {
    private ThreadSafeHotelDetails threadSafeHotelDetails;

    /**
     * Method to get request and writes Json response that contains weather data
     * @param request HttpRequest
     * @param response HttpResponse
     */
    @Override
    public void processRequest(HttpRequest request, HttpResponse response) {
        JsonObject responseJson = new JsonObject();
        if (request.getQueryParameters().get("hotelId") == null || request.getQueryParameters().get("hotelId").isEmpty()) {
            responseJson.addProperty("hotelId", "invalid");
            responseJson.addProperty("success", false);
            response.sendPageNotFoundResponse(responseJson.toString());
        }else {
            String hotelId = request.getQueryParameters().get("hotelId");
            responseJson = threadSafeHotelDetails.getWeatherDataInJSONFormat(hotelId);
            response.sendResponse(responseJson.toString());
            System.out.println(responseJson);
        }
    }
    @Override
    public void setAttribute(Object data) {
        this.threadSafeHotelDetails = (ThreadSafeHotelDetails) data;
    }
}
