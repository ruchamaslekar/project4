package reviewData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Demonstrating Review class */
public class Review {
    private final String hotelId;
    private final String reviewId;
    private final int ratingOverall;
    private final String title;
    private final String reviewText;
    private final String userNickname;
    private final LocalDate date;

    /** getter method for reviewText*/
    public String getReviewText() {  return reviewText;}

    /** getter method for hotelId*/
    public String getHotelId() { return hotelId;}

    /** getter method for reviewId*/
    public String getReviewId() { return reviewId;}

    /** getter method for date*/
    public LocalDate getDate() {
        return date;}

    /** getter method for ratingOverall*/
    public int getRatingOverall() {
        return ratingOverall;
    }
    /** getter method for title*/
    public String getTitle() {
        return title;
    }
    /** getter method for userNickname*/
    public String getUserNickname() {
        return userNickname;
    }

    /**
     * Constructor Review
     *@param reviewId reviewId
     *@param ratingOverall ratingOverall
     *@param title title
     *@param reviewText reviewText
     *@param userNickname userNickname
     *@param date date
     */
    public Review(String hotelId, String reviewId, int ratingOverall, String title, String reviewText, String userNickname, String date) {
        this.hotelId = hotelId;
        this.reviewId = reviewId;
        this.ratingOverall = ratingOverall;
        this.title = title;
        this.reviewText=reviewText;
        this.userNickname=userNickname;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        this.date = LocalDate.parse(date, formatter);
    }

    /**
     * toString
     * @return string representation of this review
     */
    @Override
    public String toString () {
        return  "Review by " + this.userNickname + " on " +
                 this.date + System.lineSeparator() +
                "Rating: " + this.ratingOverall + System.lineSeparator() +
                "ReviewId: " + this.reviewId + System.lineSeparator() +
                this.title + System.lineSeparator() +
                this.reviewText +System.lineSeparator() ;
    }
    public String toStringDisplay () {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.atStartOfDay().format(formatter);
        return  "ReviewId = " + this.reviewId + System.lineSeparator() +
                "averageRating = " + this.ratingOverall + System.lineSeparator() +
                "Title = " + this.title + System.lineSeparator() +
                "reviewText = " + this.reviewText + System.lineSeparator() +
                "userNickname = " + this.userNickname + System.lineSeparator() +
                "submissionDate = " + formattedDate;
    }

}

