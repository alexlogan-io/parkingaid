package parkingaid.lpg.com.parkingadd;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import parkingaid.lpg.com.parkingadd.framework.DirectionView;

/**
 * Represents a Direction View Fragment which contains an animated arrow pointing to the target
 * location and distance to that location
 */
public class DirectionViewFragment extends android.support.v4.app.Fragment implements SensorEventListener {

    private static final String argTarg = "TARGET_LOCATION";
    private static final String argCurr = "CURRENT_LOCATION";
    private ImageView image;
    private Location targetLocation;
    private Location currentLocation = new Location("x");;
    private SensorManager mSensorManager;
    private TextView tvHeading;
    private DirectionView directionView;

    /**
     * Creates a new instance of DirectionViewFragment, stores any input information and
     * acts as a Constructor
     * @param current current user location
     * @param park location of car
     * @return the DirectionViewFragment
     */
    public static DirectionViewFragment newInstance(Location current, Location park) {
        DirectionViewFragment fragment = new DirectionViewFragment();
        Bundle locations = new Bundle();
        locations.putParcelable(argTarg, park);
        locations.putParcelable(argCurr, current);
        fragment.setArguments(locations);
        return fragment;
    }

    public DirectionViewFragment() {
        // Required empty public constructor
    }

    /**
     * Creates the DirectionViewFragment and ensures that the savedInstanceState is never null when
     * it's created
     * @param savedInstanceState the Bundle of arguments required
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            targetLocation = getArguments().getParcelable(argTarg);
            currentLocation = getArguments().getParcelable(argCurr);
        }else{
            targetLocation = savedInstanceState.getParcelable("targetLocation");
            currentLocation = savedInstanceState.getParcelable("currentLocation");
        }
    }

    /**
     * Creates the view containing the arrow image and distance information
     * @param inflater for inflating the views in the fragment
     * @param container contains the views
     * @param savedInstanceState the Bundle of arguments required
     * @return the newly updated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_direction_page, container, false);
        // our compass image
        image = (ImageView) view.findViewById(R.id.imageviewcompass);
        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) view.findViewById(R.id.tvHeading);
        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        directionView = new DirectionView(targetLocation);

        return view;
    }

    /**
     * Attaches the fragment to the MainActivity
     * @param activity the MainActivity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    /**
     * Re-registers the SensorManager when the app is re-opened
     */
    public void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * pauses the SensorManager
     */
    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    /**
     * Required method to remove fragment from the MainActivity
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Updates the distance and arrow direction when the user's phone sensor senses a change
     * @param event
     */
    public void onSensorChanged(SensorEvent event) {
        tvHeading = directionView.updateDirectionText(event,tvHeading,currentLocation);
        image = directionView.updateDirectionImage(image);
    }

    /**
     * Required built in method
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Updates the current location
     * @param currentLocation the current location
     */
    public void updateLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    /**
     * Saves the latest information from the fragment
     * @param outState the Bundle containing the information
     */
    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putParcelable("targetLocation", targetLocation);
        outState.putParcelable("currentLocation", currentLocation);
        super.onSaveInstanceState(outState);
    }

    /**
     * Stops fragment orientation changing
     * @param isVisibleToUser boolean for whether an orientation is visible
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }
    }

}