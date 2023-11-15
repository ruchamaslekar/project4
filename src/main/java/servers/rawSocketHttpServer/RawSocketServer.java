package servers.rawSocketHttpServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Map<String, String> handlers = new HashMap<>(); // maps each endpoint/url path to the appropriate handler
    // Think of handlers as "servlets" - helpers of the server;
    private static boolean isShutdown = false;
    private final ExecutorService threads;
    private static Object data;
    private static final Logger logger = LogManager.getLogger();
    private static final Map<String, Object> resources = new HashMap<>();
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
    public void setResourceAttribute(String resourceName, Object resource) {
        resources.put(resourceName, resource);
    }

    // FILL IN CODE: add additional methods as needed

    public void start() {
        // start the server
        // FILL IN CODE
        System.out.println("RawSocketServer started");
        try(ServerSocket welcomingSocket = new ServerSocket(PORT)){
            while(!isShutdown) {
                Socket connectionSocket = welcomingSocket.accept();
                RequestWorker requestWorker = new RequestWorker(connectionSocket);
                threads.submit(requestWorker);
            }
        } catch (Exception ex) {
            logger.debug("Error"+ex);
        }
    }
    public static class RequestWorker implements Runnable {
        private final Socket clientConnectionSocket;

        /**
         * Initialize client socket
         *
         * @param clientSocket Socket
         */
        public RequestWorker(Socket clientSocket) {
            clientConnectionSocket = clientSocket;
        }

        /**
         * Reads request from connection socket input stream and calls appropriate handler to process request
         */
        @Override
        public void run() {
            System.out.println("Client Connected");
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(clientConnectionSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientConnectionSocket.getOutputStream(), true)) {
                StringBuilder requestBuilder = new StringBuilder();
                String line;
                if(!(line = reader.readLine()).isEmpty()) {
                    requestBuilder.append(line).append(System.lineSeparator());
                }
                String httpRequest = requestBuilder.toString();
                HttpResponse response = new HttpResponse(writer);
                if(httpRequest.startsWith("POST")){
                    response.sendMethodNotFoundResponse();
                }
                HttpRequest parsedRequest = new HttpRequest(httpRequest);
                String className = handlers.get(parsedRequest.getPath());
                if(className != null && !className.isEmpty()) {
                    StringBuilder packagePath = new StringBuilder("servers.rawSocketHttpServer.handlers.");
                    StringBuilder path = packagePath.append(className);
                    try{
                        HttpHandler httpHandler = (HttpHandler) Class.forName(path.toString()).newInstance();
                        httpHandler.setAttribute(resources.get(className));
                        httpHandler.processRequest(parsedRequest, response);
                    }finally {
                        clientConnectionSocket.close();
                    }
                }else {
                    response.sendPageNotFoundResponse();
                }
            }catch(IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                logger.debug("Error"+e);
//                throw new RuntimeException(e);
            }
        }
    }
}
