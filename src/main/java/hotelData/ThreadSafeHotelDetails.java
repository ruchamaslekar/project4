package hotelData;

import java.util.concurrent.locks.*;

/**This class is extending HotelDetails class and implementing locks */
public class ThreadSafeHotelDetails extends HotelDetails {
    private final ReentrantReadWriteLock lock;
    public ThreadSafeHotelDetails() {
        lock = new ReentrantReadWriteLock();

    }
    /** Overridden method from HotelDetails class
     * Has ReadLock
     * */
    @Override
    public Hotel getHotel(String hotelID) {
        try{
            lock.readLock().lock();
            return super.getHotel(hotelID);
        }
        finally {
            lock.readLock().unlock();
        }
    }
    /** Overridden method from HotelDetails class
     * Has WriteLock
     * */
    @Override
    public void addHotel(String hotelId, Hotel hotel) {
        try{
            lock.writeLock().lock();
            super.addHotel(hotelId,hotel);
        }
        finally {
            lock.writeLock().unlock();
        }
    }
}
