package weatherData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.net.Socket;

public class WeatherFetcher {
    private static final int PORT = 80;
    public static JsonObject fetch(String host, String pathAndResource) {
        StringBuilder buf = new StringBuilder();
        StringBuffer stringBuffer = new StringBuffer();
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
                    stringBuffer.append(line);
                }
            }
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject jo = (JsonObject) parser.parse(String.valueOf(stringBuffer));
            weatherDetails = jo.getAsJsonObject("current_weather");
        } catch (IOException e) {
            System.out.println("WeatherFetcher::IOException occurred during download: " + e.getMessage());
        }
        return weatherDetails;
    }

    /**
     * Method to create a GET request for the given host and resource
     * @param host String
     * @param pathResourceQuery String
     * @return HTTP GET request returned as a string
     */
    private static String getRequest(String host, String pathResourceQuery) {
        return "GET " + pathResourceQuery + " HTTP/1.1" + System.lineSeparator()
                + "Host: " + host + System.lineSeparator()
                + "Connection: close" + System.lineSeparator()
                + System.lineSeparator();
    }
}




