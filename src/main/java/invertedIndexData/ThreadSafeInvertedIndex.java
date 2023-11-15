package invertedIndexData;

import com.google.gson.JsonObject;
import reviewData.Review;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**This class is extending InvertedIndex class and implementing locks */
public class ThreadSafeInvertedIndex extends InvertedIndex {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public ThreadSafeInvertedIndex() {
    }

    /** Overridden addReviews() method from InvertedIndex class*/
    @Override
    public void addReviews(Review review) {
        try {
            lock.writeLock().lock();
            super.addReviews(review);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /** Overridden searchByWord() method from InvertedIndex class */
    @Override
    public Set<ReviewFrequency> searchByWord(String word,int numOfThreads) {
        try {
            lock.readLock().lock();
            return super.searchByWord(word,numOfThreads);
        } finally {
            lock.readLock().unlock();
        }
    }

    /** Overridden getInvertedIndexInJSONFormat() method from InvertedIndex class*/
    @Override
    public JsonObject getInvertedIndexInJSONFormat(String word, int numOfThreads) {
        try {
            lock.readLock().lock();
            return super.getInvertedIndexInJSONFormat(word,numOfThreads);
        } finally {
            lock.readLock().unlock();
        }
    }
}

