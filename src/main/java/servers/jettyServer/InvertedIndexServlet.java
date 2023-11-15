package servers.jettyServer;

import com.google.gson.JsonObject;
import invertedIndexData.InvertedIndex;
import org.apache.commons.text.StringEscapeUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class InvertedIndexServlet extends HttpServlet {

    /**
     * Method to get request and writes Json response that contains invertedIndex data
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        JsonObject responseJson = new JsonObject();
        PrintWriter out = response.getWriter();
        InvertedIndex invertedIndex = (InvertedIndex) getServletContext().getAttribute("word");
        String word = request.getParameter("word");
        word = StringEscapeUtils.escapeHtml4(word);
        if(word == null || word.isEmpty()) {
            responseJson.addProperty("success", false);
            responseJson.addProperty("word", "invalid");
        }else if(request.getParameter("num") == null || request.getParameter("num").isEmpty()) {
            responseJson.addProperty("success", false);
            responseJson.addProperty("num", "invalid");
        }else {
            int numOfReviews = (Integer.parseInt(request.getParameter("num")));
            responseJson = invertedIndex.getInvertedIndexInJSONFormat(word,numOfReviews);
        }
        response.getWriter().println(responseJson);
        out.flush();
    }
}
