package servers.jettyServer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import invertedIndexData.InvertedIndex;
import invertedIndexData.ReviewFrequency;
import org.apache.commons.text.StringEscapeUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class InvertedIndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        String word = request.getParameter("word");
        int numOfReviews = (Integer.parseInt(request.getParameter("num"))) ;
        word = StringEscapeUtils.escapeHtml4(word);
        JsonArray array = new JsonArray();
        JsonObject responseJson = new JsonObject();
        JsonObject responseJson1 = new JsonObject();
        if(word != null && !word.isEmpty()) {
            InvertedIndex invertedIndex = (InvertedIndex) getServletContext().getAttribute("word");
            Set<ReviewFrequency> reviews = invertedIndex.searchByWord(word,numOfReviews);
            if(reviews == null){
                responseJson.addProperty("success",false);
                responseJson.addProperty("word","invalid");
                out.println(responseJson);
            }
            else {
                responseJson.addProperty("success",true);
                responseJson.addProperty("word",word);
                for (ReviewFrequency review : reviews) {
                    responseJson1.addProperty("reviewId",review.getReview().getReviewId());
                    responseJson1.addProperty("title",review.getReview().getTitle());
                    responseJson1.addProperty("user",review.getReview().getUserNickname());
                    responseJson1.addProperty("reviewText",review.getReview().getReviewText());
                    responseJson1.addProperty("date",review.getReview().getDate().toString());
                    array.add(responseJson1);
                    responseJson.add("reviews", array);
                }
            }
        } else {
            responseJson.addProperty("success",false);
            responseJson.addProperty("hotelId","invalid");
        }
        out.println(responseJson);
        out.flush();

    }
}
