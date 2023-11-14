package servers.jettyServer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import invertedIndexData.InvertedIndex;
import invertedIndexData.ReviewFrequency;
import org.apache.commons.text.StringEscapeUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class InvertedIndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        JsonObject responseJson = new JsonObject();
        PrintWriter out = response.getWriter();
        String word = request.getParameter("word");
        word = StringEscapeUtils.escapeHtml4(word);
        if (word == null || word.isEmpty()) {
            responseJson.addProperty("success", false);
            responseJson.addProperty("word", "invalid");
            response.getWriter().println(responseJson);
            out.flush();
        } else if (request.getParameter("num") == null || request.getParameter("num").isEmpty()) {
            responseJson.addProperty("success", false);
            responseJson.addProperty("num", "invalid");
            response.getWriter().println(responseJson);
            out.flush();
        } else {
            int numOfReviews = (Integer.parseInt(request.getParameter("num")));
            JsonArray array = new JsonArray();
            InvertedIndex invertedIndex = (InvertedIndex) getServletContext().getAttribute("word");
            Set<ReviewFrequency> reviews = invertedIndex.searchByWord(word, numOfReviews);
            if (reviews != null && !reviews.isEmpty()) {
                ReviewFrequency[] setArray = reviews.toArray(new ReviewFrequency[0]);
                responseJson.addProperty("word", word);
                for(ReviewFrequency reviewFrequency : setArray) {
                    JsonObject reviewJson = new JsonObject();
                    reviewJson.addProperty("reviewId", reviewFrequency.getReview().getReviewId());
                    reviewJson.addProperty("title", reviewFrequency.getReview().getTitle());
                    reviewJson.addProperty("user", reviewFrequency.getReview().getUserNickname());
                    reviewJson.addProperty("reviewText", reviewFrequency.getReview().getReviewText());
                    reviewJson.addProperty("date", reviewFrequency.getReview().getDate().toString());
                    array.add(reviewJson);
                    responseJson.add("reviews", array);
                }
                responseJson.addProperty("success", true);
            } else {
                responseJson.addProperty("success", false);
                responseJson.addProperty("word", "invalid");
            }
            response.getWriter().println(responseJson);
            out.flush();
        }
    }
}
