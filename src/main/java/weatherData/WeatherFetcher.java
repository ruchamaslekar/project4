package weatherData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.Socket;

public class WeatherFetcher {
    public static int PORT = 80;
    public static JsonObject fetch(String host, String pathAndResource) {
        StringBuilder buf = new StringBuilder();
        StringBuffer sb = new StringBuffer();
        JsonObject weatherDetails = null;
        try (Socket socket = new Socket(host, PORT)) {
            OutputStream out = socket.getOutputStream();
            InputStream inStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
            String request = getRequest(host, pathAndResource);
            out.write(request.getBytes());
            out.flush();
            String line = reader.readLine();
            if (line != null) {
                buf.append(line + System.lineSeparator());
                line = reader.readLine();
            }
            while((line = reader.readLine()) != null) {
                if (line.startsWith("{")) {
                    sb.append(line);
                }
            }
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject jo = (JsonObject) parser.parse(String.valueOf(sb));
            weatherDetails = jo.getAsJsonObject("current_weather");
        } catch (IOException e) {
            System.out.println("HTTPFetcher::IOException occurred during download: " + e.getMessage());
        }
        return weatherDetails;
    }

    /**
     * A method that creates a GET request for the given host and resource
     * @param host
     * @param pathResourceQuery
     * @return HTTP GET request returned as a string
     */
    private static String getRequest(String host, String pathResourceQuery) {
        return "GET " + pathResourceQuery + " HTTP/1.1" + System.lineSeparator()
                + "Host: " + host + System.lineSeparator()
                + "Connection: close" + System.lineSeparator()
                + System.lineSeparator();
    }

}




