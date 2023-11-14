package servers.rawSocketHttpServer;

import servers.rawSocketHttpServer.handlers.ReviewHandler;
import servers.rawSocketHttpServer.handlers.WordHandler;
import servers.rawSocketHttpServer.handlers.HotelHandler;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/** Implements the http server (that processes GET requests) using raw sockets.
 * RawSocketServer should be general and not contain anything related to hotels/reviews.
 * */
public class RawSocketServer {
    private static final int PORT = 8080;
    private static Map<String, String> handlers = new HashMap<>(); // maps each endpoint/url path to the appropriate handler
    // Think of handlers as "servlets" - helpers of the server;
    private static boolean isShutdown = false;
    private final ExecutorService threads;
    private static Object data;

    public RawSocketServer(int numOfThreads) {
        threads = Executors.newFixedThreadPool(numOfThreads);
    }
    // FILL IN CODE: you may want to add another data structure to store "resources"
    // (do not store ThreadSafeHotelData here, the server must be general - you can store "resource/s" as Object(s)

    /**
     * Maps a given URL path/endpoint to the name of the class that will handle requests coming at this endpoint
     *
     * @param path      end point
     * @param className name of the handler class
     */
    public void addMapping(String path, String className) {
        handlers.put(path, className);
    }
    public void addResources(Object data) {
        this.data = data;
    }

    // FILL IN CODE: add additional methods as needed

    public void start() {
        // start the server
        // FILL IN CODE
        //create welcoming socket
        //listen to request
        //if request found, create connectionSocket
        //refer gameServer lab assignment
        System.out.println("HTTP server started");

        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket welcomingSocket = new ServerSocket(PORT);
                    while (true) {
                        Socket connectionSocket = welcomingSocket.accept();
                        RequestWorker requestWorker = new RequestWorker(connectionSocket);
                        threads.submit(requestWorker);
                    }
                } catch (Exception ex) {

                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }
    public static class RequestWorker implements Runnable {
        final Socket clientConnectionSocket;

        /**
         * Initialize client socket
         *
         * @param clientSocket Socket
         */
        public RequestWorker(Socket clientSocket) {
            clientConnectionSocket = clientSocket;
            System.out.println("Client connected");
        }

        /**
         * Reads request from connection socket input stream and calls appropriate handler to process request
         */
        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientConnectionSocket.getInputStream())); PrintWriter writer = new PrintWriter(clientConnectionSocket.getOutputStream(), true)) {
                StringBuilder requestBuilder = new StringBuilder();
                String line;
//                while (!(line = reader.readLine()).isEmpty()) {
//                    requestBuilder.append(line).append("\r\n");
//                }
                String httpRequest = requestBuilder.toString();
                HttpRequest parsedRequest = new HttpRequest(httpRequest);
                String path = parsedRequest.getPath();
                String className= handlers.get(path);
                HttpHandler httpHandler = null;
                try {
                    httpHandler = (HttpHandler) Class.forName(className).
                            newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
//              HttpHandler httpHandler = new HotelHandler();
//              HttpHandler httpHandler = new ReviewHandler();
//              HttpHandler httpHandler = new WordHandler();
                HttpResponse response = new HttpResponse(writer);
                httpHandler.setAttribute(data);
                httpHandler.processRequest(parsedRequest,response);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientConnectionSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
