package parkingaid.lpg.com.parkingadd;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class representing a parking event which deals with all of the data relating to that parking event
 */
public class ParkingEvent implements Parcelable {

    private int parkTimeHours;
    private int parkTimeMins;
    private Integer leaveTimeHours;
    private Integer leaveTimeMins;
    private String notes;
    private Location parkLocation;

    /**
     * Constructor for a parking event
     * @param parkTimeHours the hour of the park time
     * @param parkTimeMins the minute of the park time
     * @param leaveTimeHours the departure hour
     * @param leaveTimeMins the departure minute
     * @param notes information about the parking space
     * @param parkLocation the location of the parking space
     */
    ParkingEvent(int parkTimeHours, int parkTimeMins, Integer leaveTimeHours, Integer leaveTimeMins, String notes, Location parkLocation){
        this.parkTimeHours = parkTimeHours;
        this.parkTimeMins = parkTimeMins;
        this.leaveTimeHours = leaveTimeHours;
        this.leaveTimeMins = leaveTimeMins;
        this.notes = notes;
        this.parkLocation = parkLocation;
    }

    /**
     * @return the hour of the park time
     */
    public int getParkTimeHours() {
        return parkTimeHours;
    }

    /**
     * set the hour of the park time
     * @param parkTimeHours
     */
    public void setParkTimeHours(int parkTimeHours) {
        this.parkTimeHours = parkTimeHours;
    }

    /**
     * @return the minute of the park time
     */
    public int getParkTimeMins() {
        return parkTimeMins;
    }

    /**
     * set the minute of the park time
     * @param parkTimeMins
     */
    public void setParkTimeMins(int parkTimeMins) {
        this.parkTimeMins = parkTimeMins;
    }

    /**
     * @return the hour of the departure time
     */
    public Integer getLeaveTimeHours() {
        return leaveTimeHours;
    }

    /**
     * set the hour of the departure time
     * @param leaveTimeHours
     */
    public void setLeaveTimeHours(Integer leaveTimeHours) {
        this.leaveTimeHours = leaveTimeHours;
    }

    /**
     * @return the minute of the departure time
     */
    public Integer getLeaveTimeMins() {
        return leaveTimeMins;
    }

    /**
     * set the minute of the departure time
     * @param leaveTimeMins
     */
    public void setLeaveTimeMins(Integer leaveTimeMins) {
        this.leaveTimeMins = leaveTimeMins;
    }

    /**
     * @return the parking space information
     */
    public String getNotes() {
        return notes;
    }

    /**
     * set the parking space information
     * @param notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the location of the parking space
     */
    public Location getParkLocation() {
        return parkLocation;
    }

    /**
     * set the location of the parking space
     * @param parkLocation
     */
    public void setParkLocation(Location parkLocation) {
        this.parkLocation = parkLocation;
    }

    /**
     * Required override method that is unused
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Writes the parking information to a Parcelable object
     * @param dest the Parcelable object
     * @param flags the tag
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(parkTimeHours);
        dest.writeInt(parkTimeMins);
        dest.writeInt(leaveTimeHours);
        dest.writeInt(leaveTimeMins);
        dest.writeString(notes);
        dest.writeParcelable(parkLocation, flags);
    }

    /**
     * Method to create a Parcelable object from a Parking Event object
     */
    public static final Parcelable.Creator<ParkingEvent> CREATOR = new Parcelable.Creator<ParkingEvent>(){
        public ParkingEvent createFromParcel(Parcel in){
            return new ParkingEvent(in);
        }
        public ParkingEvent[] newArray(int size){
            return new ParkingEvent[size];
        }
    };

    /**
     * Reads the Parking Event information from the Parcelable object
     * @param in
     */
    private ParkingEvent(Parcel in){
        parkTimeHours = in.readInt();
        parkTimeMins = in.readInt();
        leaveTimeHours = in.readInt();
        leaveTimeMins = in.readInt();
        notes = in.readString();
        parkLocation = in.readParcelable(Location.class.getClassLoader());
    }
}
