package servers.rawSocketHttpServer.handlers;

import com.google.gson.JsonObject;
import invertedIndexData.ThreadSafeInvertedIndex;
import servers.rawSocketHttpServer.HttpHandler;
import servers.rawSocketHttpServer.HttpRequest;
import servers.rawSocketHttpServer.HttpResponse;

public class WordHandler implements HttpHandler {
    private ThreadSafeInvertedIndex threadSafeInvertedIndex;
    @Override
    public void processRequest(HttpRequest request, HttpResponse response) {
        JsonObject responseJson = new JsonObject();
        if(request.getQueryParameters().get("word") == null || request.getQueryParameters().get("word").isEmpty()) {
            responseJson.addProperty("word", "invalid");
            responseJson.addProperty("success", false);
            response.sendPageNotFoundResponse(responseJson.toString());
        }else if(request.getQueryParameters().get("num") == null || request.getQueryParameters().get("num").isEmpty()) {
            responseJson.addProperty("num", "invalid");
            responseJson.addProperty("success", false);
            response.sendPageNotFoundResponse(responseJson.toString());
        }else {
            String word = request.getQueryParameters().get("word");
            int numOfReviews = (Integer.parseInt(request.getQueryParameters().get("num"))) ;
            responseJson = threadSafeInvertedIndex.getInvertedIndexInJSONFormat(word,numOfReviews);
            response.sendResponse(responseJson.toString());
            System.out.println(responseJson);
        }
    }
    @Override
    public void setAttribute(Object data) {
        this.threadSafeInvertedIndex = (ThreadSafeInvertedIndex) data;
    }
}
