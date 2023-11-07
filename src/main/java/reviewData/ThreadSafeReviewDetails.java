package reviewData;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**This class is extending ReviewDetails class and implementing locks */
public class ThreadSafeReviewDetails extends ReviewDetails {
    private final ReentrantReadWriteLock lock;

    public ThreadSafeReviewDetails() {
        this.lock = new ReentrantReadWriteLock();
    }

    /** Overridden method from ReviewDetails class
     * Has WriteLock
     * */
    @Override
    public void addReviews(String hotelId, List<Review> review){
        try {
            lock.writeLock().lock();
            super.addReviews(hotelId,review);
        }
        finally {
            lock.writeLock().unlock();
        }
    }
    /** Overridden method from ReviewDetails class
     * Has ReadLock
     * */
    @Override
    public List<Review> getReviews(String hotelId) {
        try {
            lock.readLock().lock();
            List<Review> reviews = super.getReviews(hotelId);
            if(reviews != null) {
                return Collections.unmodifiableList(super.getReviews(hotelId));
            }
            else {
                return Collections.emptyList();
            }
        }
        finally {
            lock.readLock().unlock();
        }
    }
}
