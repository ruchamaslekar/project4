package servers.rawSocketHttpServer;

import java.io.PrintWriter;

public class HttpResponse {
    // FILL IN CODE: add variables and methods as needed
    //Http response
    //200 OK
    //Content-type: application/json
    //Empty line

    private final PrintWriter writer;
    private int statusCode;
    private String statusMessage;
    private final String contentType;
    private final StringBuilder content;

    /** Constructor HttpResponse */
    public HttpResponse(PrintWriter writer) {
        this.writer = writer;
        this.contentType = "application/json";
        this.content = new StringBuilder();
    }

    /** Method to send response as OK
     * @param response String
     */
    public void sendResponse(String response) {
        writer.println("HTTP/1.1 " + 200 + " " + "OK");
        writer.println("Content-Type: " + contentType + "; charset=UTF-8");
        writer.println();
        writer.println(content.append(response));
        writer.flush();
    }

    /** Method to send response as page not found
     * @param response String
     */
    public void sendPageNotFoundResponse(String response){
        writer.println("HTTP/1.1 " + 404 + " " + "Not Found");
        writer.println("Content-Type: " + contentType + "; charset=UTF-8");
        writer.println();
        writer.println(content.append(response));
        writer.flush();
    }

    /**Method to send response as page not found */
    public void sendPageNotFoundResponse(){
        writer.println("HTTP/1.1 " + 404 + " " + "Not Found");
        writer.println("Content-Type: " + contentType + "; charset=UTF-8");
        writer.println();
        writer.flush();
    }

    /**Method to send response as method not found */
    public void sendMethodNotFoundResponse(){
        writer.println("HTTP/1.1 " + 405 + " " + "Method Not Found");
        writer.println("Content-Type: " + contentType + "; charset=UTF-8");
        writer.println();
        writer.flush();
    }

    /** toString() method to display httpResponse*/
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
}
