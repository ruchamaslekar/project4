package invertedIndexData;

import reviewData.Review;

import java.util.*;

public class InvertedIndex {

    /** Initializing invertedIndex Map */
    private final Map<String, TreeSet<ReviewFrequency>> invertedIndex = new HashMap<>();

    /** Getter method for map*/
    public Map<String, TreeSet<ReviewFrequency>> getInvertedIndex() {
        return Collections.unmodifiableMap(invertedIndex);
    }

    /** Method to add Reviews into InvertedIndex */
    public void addReviews(Review review) {
        String[] cleanedWords = review.getReviewText().split(" ");
        for (String word : cleanedWords) {
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (!invertedIndex.containsKey(word)) {
                ReviewFrequency rf = new ReviewFrequency(review, 1);
                TreeSet<ReviewFrequency> treeSet = new TreeSet<>((rf1, rf2) -> {
                    int frequencyComparison = Integer.compare(rf1.getFrequency(), rf2.getFrequency());
                    if (frequencyComparison == 0) {
                        if (rf1.getDate().equals(rf2.getDate())) {
                            return rf2.getReview().getReviewId().compareTo(rf1.getReview().getReviewId());
                        } else {
                            return rf1.getDate().compareTo(rf2.getDate());
                        }
                    }
                    return frequencyComparison;
                });
                treeSet.add(rf);
                invertedIndex.put(word,treeSet);
            } else {
                TreeSet<ReviewFrequency> reviewFrequencySet = invertedIndex.get(word);
                Iterator<ReviewFrequency> iterator = reviewFrequencySet.iterator();
                boolean found = false;
                while (iterator.hasNext()) {
                    ReviewFrequency reviewFrequency = iterator.next();
                    Review currentReview = reviewFrequency.getReview();
                    if (currentReview.equals(review)) {
                        reviewFrequency.setFrequency(reviewFrequency.getFrequency() + 1);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    ReviewFrequency newReviewFrequency = new ReviewFrequency(review, 1);
                    reviewFrequencySet.add(newReviewFrequency);
                }
                invertedIndex.put(word, new TreeSet<>(reviewFrequencySet));
            }
        }
    }

    /** This method is searching to get details from invertedIndex
     * @param word word
     * */
    public Set<ReviewFrequency> searchByWord (String word,int numOfReviews) {
        Set<ReviewFrequency> matchingReviews = invertedIndex.get(word);
        if (matchingReviews != null) {
            List<ReviewFrequency> reviewList = new ArrayList<>(matchingReviews);

            if (numOfReviews >= 0 && numOfReviews <= reviewList.size()) {
                return new TreeSet<>(reviewList.subList(0, numOfReviews));
            } else {
                return new TreeSet<>(reviewList);
            }
        } else {
            return null;
        }
    }
}

