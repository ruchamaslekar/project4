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

    public void setStatusCode(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void sendResponse(Hotel hotelInfoResponse) {
        writer.println("HTTP/1.1 " + statusCode + " " + statusMessage);
        writer.println();
        writer.println(content.append(hotelInfoResponse.toString()));
        writer.flush();
    }

    public void sendReviewResponse(List<Review> reviewsResponse) {
        writer.println("HTTP/1.1 " + statusCode + " " + statusMessage);
        writer.println();
        writer.println(content.append(reviewsResponse.toString()));
        writer.flush();
    }

    public void sendInvertedIndexResponse(Set<ReviewFrequency> invertedIndexResponse) {
        writer.println("HTTP/1.1 " + statusCode + " " + statusMessage);
        writer.println();
        writer.println(content.append(invertedIndexResponse.toString()));
        writer.flush();
    }
}
