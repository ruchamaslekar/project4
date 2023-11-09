package invertedIndexData;

import reviewData.Review;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**This class is extending InvertedIndex class and implementing locks */
public class ThreadSafeInvertedIndex extends InvertedIndex {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public ThreadSafeInvertedIndex() {
    }

    /**
     * Overridden method from InvertedIndex class
     * Has WriteLock
     */
    @Override
    public void addReviews(Review review) {
        try {
            lock.writeLock().lock();
            super.addReviews(review);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Overridden method from InvertedIndex class
     * Has ReadLock
     */
    @Override
    public Set<ReviewFrequency> searchByWord(String word,int numOfThreads) {
        try {
            lock.readLock().lock();
            return super.searchByWord(word,numOfThreads);
        } finally {
            lock.readLock().unlock();
        }
    }
}

