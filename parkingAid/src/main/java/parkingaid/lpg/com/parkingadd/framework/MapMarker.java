package parkingaid.lpg.com.parkingadd.framework;

import android.content.Context;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a marker to be placed on a map
 */
public class MapMarker implements Parcelable {

    private Context context;
    private Location location;
    private String title;
    int markerImage;

    /**
     * Constructor for a map marker
     * @param context current activity context
     * @param location location for marker to be added
     * @param title title of the marker to be added
     * @param markerImage type of marker image to add
     */
    public MapMarker(Context context, Location location, String title, int markerImage) {
        this.context = context;
        this.location = location;
        this.title = title;
        this.markerImage = markerImage;
    }

    /**
     * Constructor for a map marker
     * @param context current activity context
     * @param location location for marker to be added
     * @param title title of the marker to be added
     */
    public MapMarker(Context context, Location location, String title) {
        this.context = context;
        this.location = location;
        this.title = title;
    }

    /**
     * @return the current activity context
     */
    public Context getContext() {
        return context;
    }

    /**
     * set the current activity context
     * @param context  current activity context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * @return location of marker
     */
    public Location getLocation() {
        return location;
    }

    /**
     * set the location of the marker
     * @param location of the marker
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the title of the marker
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the image relating to the marker
     */
    public int getMarkerImage() {
        return markerImage;
    }

    /**
     * set the image relating to the marker
     * @param markerImage the image relating to the marker
     */
    public void setMarkerImage(int markerImage) {
        this.markerImage = markerImage;
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
     * Method to create a Parcelable object from the marker, it is unused
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
