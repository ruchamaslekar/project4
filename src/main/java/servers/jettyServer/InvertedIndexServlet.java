package servers.jettyServer;

import com.google.gson.JsonObject;
import invertedIndexData.InvertedIndex;
import invertedIndexData.InvertedIndexParser;
import invertedIndexData.ReviewFrequency;
import org.apache.commons.text.StringEscapeUtils;
import reviewData.Review;
import reviewData.ReviewDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

public class InvertedIndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        String word = request.getParameter("word");
        word = StringEscapeUtils.escapeHtml4(word);
        if (word == null || word.isEmpty()) {
            out.println("incorrect input");
        }
        InvertedIndex ht= (InvertedIndex) getServletContext().getAttribute("word");
        JsonObject Jobject = new JsonObject();
        Jobject.addProperty("word",word);
        Set<ReviewFrequency> review= ht.searchByWord(word);
        for (ReviewFrequency r : review) {
            out.println(review.toString());
        }

    }
}
