package invertedIndexData;

import reviewData.Review;

import java.time.LocalDate;

public class ReviewFrequency implements Comparable<ReviewFrequency> {
    private final Review review;
    private int frequency;

    public ReviewFrequency(Review review, int frequency) {
        this.review = review;
        this.frequency = frequency;
    }

    public Review getReview() {
        return review;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public LocalDate getDate() {
        return review.getDate();
    }

    @Override
    public String toString() {
        return "HotelId = " + review.getHotelId() + System.lineSeparator() +
                "ReviewId = " + review.getReviewId() + System.lineSeparator() +
                "averageRating = " + review.getRatingOverall() + System.lineSeparator() +
                "Title = " + review.getTitle() + System.lineSeparator() +
                "reviewText = " + review.getReviewText() + System.lineSeparator() +
                "userNickname = " + review.getUserNickname() + System.lineSeparator() +
                "submissionDate = " + review.getDate() + System.lineSeparator()
                + getFrequency();
    }

    @Override
    public int compareTo(ReviewFrequency rf1) {
        int frequencyComparison = Integer.compare(rf1.getFrequency(), this.getFrequency());
        if (frequencyComparison == 0) {
            if (this.getDate().equals(rf1.getDate())) {
                return this.getReview().getReviewId().compareTo(rf1.getReview().getReviewId());
            } else {
                return rf1.getDate().compareTo(this.getDate());
            }
        }
        return frequencyComparison;
    }
}
