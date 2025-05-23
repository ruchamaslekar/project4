package hotelData;


/** Demonstrating Hotel class */
public class Hotel {
    private final String hotelName;
    private final String hotelId;
    private final String latitude;
    private final String city;
    private final String state;
    private final String address;
    private final String longitude;

    /** Constructor Hotel
     * @param hotelName name of hotel
     * @param hotelId hotelId
     * @param latitude latitude
     * @param city city
     * @param state state
     */
    public Hotel(String hotelName, String hotelId, String address,String longitude,String latitude, String city, String state) {
        this.hotelName = hotelName;
        this.hotelId = hotelId;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.state = state;
    }

    /** Getter for HotelName */
    public String getHotelName() {
        return hotelName;
    }

    /** Getter for HotelId */
    public String getHotelId() {
        return hotelId;
    }

    /** Getter for Latitude */
    public String getLatitude() {
        return latitude;
    }

    /** Getter for City */
    public String getCity() {
        return city;
    }

    /** Getter for State */
    public String getState() {
        return state;
    }

    /** Getter for Address */
    public String getAddress() {
        return address;
    }

    /** Getter for Longitude */
    public String getLongitude() {
        return longitude;
    }

    /** toString
     * @return string representation of this hotel
     */
    @Override
    public String toString() {
        return  this.hotelName +": "+
                this.hotelId + System.lineSeparator()+
                this.address + System.lineSeparator()+
                this.city +", "+this.state;
    }

    /** toStringDisplay
     * @return string representation of this hotel in different format
     */
    public String toStringDisplay() {
        return  "HotelName = " + this.hotelName + System.lineSeparator()+
                "HotelId = " + this.hotelId + System.lineSeparator()+
                "Latitude = " +  this.latitude+ System.lineSeparator()+
                "Longitude = " + this.longitude + System.lineSeparator()+
                "Address = " +  this.address +","+ this.city+","+this.state;
    }
}


