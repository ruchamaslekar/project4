package reviewData;

import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**This class is extending ReviewDetails class and implementing locks */
public class ThreadSafeReviewDetails extends ReviewDetails {
    private final ReentrantReadWriteLock lock;

    public ThreadSafeReviewDetails() {
        this.lock = new ReentrantReadWriteLock();
    }

    /**
     * Overridden method from ReviewDetails class
     * Has WriteLock
     */
    @Override
    public void addReviews(String hotelId, List<Review> review) {
        try {
            lock.writeLock().lock();
            super.addReviews(hotelId, review);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Overridden method from ReviewDetails class
     * Has ReadLock
     */
    @Override
    public List<Review> getReviews(String hotelId, int numOfReviews) {
        try {
            lock.readLock().lock();
            return super.getReviews(hotelId, numOfReviews);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public JsonObject getReviewsInJSONFormat(String hotelId, int numOfReviews) {
        try {
            lock.readLock().lock();
            return super.getReviewsInJSONFormat(hotelId, numOfReviews);
        } finally {
            lock.readLock().unlock();
        }
    }

}

