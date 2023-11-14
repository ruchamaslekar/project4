package servers.rawSocketHttpServer;

import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import hotelData.Hotel;
import invertedIndexData.ReviewFrequency;
import reviewData.Review;

public class HttpResponse {
    @Override
    public String toString() {
        return "HttpResponse{" +
                "writer=" + writer +
                ", statusCode=" + statusCode +
                ", statusMessage='" + statusMessage + '\'' +
                ", contentType='" + contentType + '\'' +
                ", content=" + content +
                '}';
    }

    private final PrintWriter writer;
    private int statusCode;
    private String statusMessage;
    private String contentType;
    private final StringBuilder content;
    // FILL IN CODE: add variables and methods as needed
    //Http response
    //200 OK
    //Content-type: application/json
    //Empty line
    public HttpResponse(PrintWriter writer) {
        this.writer = writer;
        this.statusCode = 200;
        this.statusMessage = "OK";
        this.contentType = "application/json";
        this.content = new StringBuilder();
    }

    public void sendResponse(String response) {
        writer.println("HTTP/1.1 " + statusCode + " " + statusMessage);
        writer.println();
        writer.println(content.append(response));
        writer.flush();
    }
    public void sendPageNotFoundResponse(String response){
        writer.println("HTTP/1.1 " + 404 + " " + "Not Found");
        writer.println();
        writer.println(content.append(response));
        writer.flush();
    }
    public void sendPageNotFoundResponse(){
        writer.println("HTTP/1.1 " + 404 + " " + "Not Found");
        writer.println();
        writer.flush();
    }
    public void sendMethodNotFoundResponse(){
        writer.println("HTTP/1.1 " + 405 + " " + "Not Found");
        writer.println();
        writer.flush();
    }
}
