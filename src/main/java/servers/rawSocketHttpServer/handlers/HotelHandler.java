package servers.rawSocketHttpServer.handlers;

import hotelData.Hotel;;
import hotelData.ThreadSafeHotelDetails;
import servers.rawSocketHttpServer.HttpHandler;
import servers.rawSocketHttpServer.HttpRequest;
import servers.rawSocketHttpServer.HttpResponse;

public class HotelHandler implements HttpHandler {
    private ThreadSafeHotelDetails threadSafeHotelDetails;
    @Override
    public void processRequest(HttpRequest request, HttpResponse response) {
        if (request.getQueryParameters().containsKey("hotelId")) {
            response.setStatusCode(200, "HTTP/1.1 200 OK" + System.lineSeparator());
            response.setContentType("ContentType: application/json" + System.lineSeparator() + System.lineSeparator());
            String hotelId = request.getQueryParameters().get("hotelId");
            System.out.println("hotelId"+hotelId);
            if (hotelId == null) {
                hotelId = "-1";
            }
            else{
                Hotel hotelInfoResponse = threadSafeHotelDetails.getHotel(hotelId);
                System.out.println(hotelInfoResponse);
                response.sendResponse(hotelInfoResponse);
            }
        }
    }
    @Override
    public void setAttribute(Object data) {
        this.threadSafeHotelDetails = (ThreadSafeHotelDetails) data;
    }
}