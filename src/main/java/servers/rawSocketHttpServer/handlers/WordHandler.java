package servers.rawSocketHttpServer.handlers;

import invertedIndexData.ReviewFrequency;
import invertedIndexData.ThreadSafeInvertedIndex;
import servers.rawSocketHttpServer.HttpHandler;
import servers.rawSocketHttpServer.HttpRequest;
import servers.rawSocketHttpServer.HttpResponse;
import java.util.Set;

public class WordHandler implements HttpHandler {
    private ThreadSafeInvertedIndex threadSafeInvertedIndex;
    @Override
    public void processRequest(HttpRequest request, HttpResponse response) {
        if (request.getQueryParameters().containsKey("word")) {
            response.setStatusCode(200, "HTTP/1.1 200 OK" + System.lineSeparator());
            response.setContentType("ContentType: application/json" + System.lineSeparator() + System.lineSeparator());
            String word = request.getQueryParameters().get("word");
            int numOfReviews = (Integer.parseInt(request.getQueryParameters().get("num"))) ;
            if (word == null) {
                word = "-1";
            }
            else{
                Set<ReviewFrequency> invertedIndexResponse = threadSafeInvertedIndex.searchByWord(word,numOfReviews);
                response.sendInvertedIndexResponse(invertedIndexResponse);
            }
        }
    }
    @Override
    public void setAttribute(Object data) {
        this.threadSafeInvertedIndex = (ThreadSafeInvertedIndex) data;
    }
}
