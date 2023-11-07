package hotelData;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/** Demonstrating HotelDetails class */
public class HotelDetails {

    /** Initializing hotelMap */
    private final Map<String, Hotel> hotelMap = new TreeMap<>();

    /** This method is adding data into hotelMap
     * @param hotelId hotelId
     * @param hotel hotel
     * */
    public void addHotel(String hotelId, Hotel hotel) {
        hotelMap.put(hotelId, hotel);
    }

    /** This method is printing Hotel data based on hotelID
     * @param hotelID hotelID
     */
    public Hotel getHotel(String hotelID) {
        return hotelMap.get(hotelID);
    }

    /** This method is returning HotelIds of all hotels
     */
    public Set<String> getIdForHotel() {
        return Collections.unmodifiableSet(hotelMap.keySet());
    }
}