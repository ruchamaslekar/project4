package servers.rawSocketHttpServer.handlers;

import com.google.gson.JsonObject;
import hotelData.Hotel;;
import hotelData.ThreadSafeHotelDetails;
import servers.rawSocketHttpServer.HttpHandler;
import servers.rawSocketHttpServer.HttpRequest;
import servers.rawSocketHttpServer.HttpResponse;

public class HotelHandler implements HttpHandler {
    private ThreadSafeHotelDetails threadSafeHotelDetails;
    @Override
    public void processRequest(HttpRequest request, HttpResponse response) {
        JsonObject responseJson = new JsonObject();
        if (request.getQueryParameters().get("hotelId") == null || request.getQueryParameters().get("hotelId").isEmpty()) {
            responseJson.addProperty("hotelId", "invalid");
            responseJson.addProperty("success", false);
            response.sendPageNotFoundResponse(responseJson.toString());
        } else {
            String hotelId = request.getQueryParameters().get("hotelId");
            responseJson = threadSafeHotelDetails.getHotelInJSONFormat(hotelId);
            response.sendResponse(responseJson.toString());
            System.out.println(responseJson.toString());
        }
    }
    @Override
    public void setAttribute(Object data) {
        this.threadSafeHotelDetails = (ThreadSafeHotelDetails) data;
    }
}