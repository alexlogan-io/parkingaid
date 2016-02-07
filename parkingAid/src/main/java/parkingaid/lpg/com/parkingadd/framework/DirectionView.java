package parkingaid.lpg.com.parkingadd.framework;

import android.hardware.GeomagneticField;
import android.hardware.SensorEvent;
import android.location.Location;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Used to update both the text and the arrow animation in the DirectionViewFragment
 */
public class DirectionView {

    Location targetLocation;
    private double heading;
    private float currentDegree;
    private float dist;
    private static final int AnimationDuration = 210;

    /**
     * Constructor for a DirectionView
     * @param targetLocation location to be directed to
     */
    public DirectionView(Location targetLocation){
        this.targetLocation = targetLocation;
        this.currentDegree = 0f;
    }

    /**
     * Updates the distance to target location and also update the bearing for the arrow animation
     * @param event the event caused by the movement of the user's phone
     * @param tvHeading text view with current distance
     * @param currentLocation user's current location
     * @return text view with distance to the target location
     */
    public TextView updateDirectionText(SensorEvent event, TextView tvHeading, Location currentLocation) {
        heading = Math.round(event.values[0]);

        float myBearing = currentLocation.bearingTo(targetLocation);

        heading += getGeoField(currentLocation).getDeclination();

        heading = (myBearing - heading) * -1;

        heading = normalizeDegree((float) heading);

        dist = currentLocation.distanceTo(targetLocation);

        DecimalFormat df = new DecimalFormat("###");
        tvHeading.setText("Distance (m): " + df.format(dist));

        return tvHeading;
    }

    /**
     * updates the arrow animation so it points towards the target location
     * @param image the current ImageView
     * @return the rotated arrow image pointing to the target location
     */
    public ImageView updateDirectionImage(ImageView image){

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                (float)-heading,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(AnimationDuration);

        ra.setInterpolator(new LinearInterpolator());

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = (float)-heading;
        return image;
    }

    public GeomagneticField getGeoField(Location currentLocation) {
        GeomagneticField geoField = new GeomagneticField(
                Double.valueOf(currentLocation.getLatitude()).floatValue(),
                Double.valueOf(currentLocation.getLongitude()).floatValue(),
                Double.valueOf(currentLocation.getAltitude()).floatValue(),
                System.currentTimeMillis()
        );
        return geoField;
    }

    private float normalizeDegree(float value) {
        if (value >= 0.0f && value <= 180.0f) {
            return value;
        } else {
            return 180 + (180 + value);
        }
    }

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    public double getHeading() {
        return heading;
    }

    public Location getTargetLocation() {
        return targetLocation;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public float getCurrentDegree() {
        return currentDegree;
    }

    public void setCurrentDegree(float currentDegree) {
        this.currentDegree = currentDegree;
    }
}
