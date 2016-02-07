package parkingaid.lpg.com.parkingadd;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import parkingaid.lpg.com.parkingadd.framework.MapController;
import parkingaid.lpg.com.parkingadd.framework.MapMarker;

/**
 * Fragment containing the Google Maps API
 */
public class MapFragment extends Fragment {

    private GoogleMap mMap;
    private MapView mMapView;
    private MapController mapController = new MapController();
    private Location targetLocation;
    private static final String argTarg = "TARGET_LOCATION";

    /**
     * Creates a new instance of MapFragment, stores any input information and
     * acts as a Constructor
     * @param park the current parking event
     * @return the MapFragment
     */
    public static MapFragment newInstance(Location park) {
        MapFragment fragment = new MapFragment();
        Bundle locations = new Bundle();
        locations.putParcelable(argTarg, park);
        fragment.setArguments(locations);
        return fragment;
    }
    /**
     * Creates the MapFragment and ensures that the savedInstanceState is never null when
     * it's created
     * @param savedInstanceState the Bundle of arguments required
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            targetLocation = getArguments().getParcelable(argTarg);
        }else{
            targetLocation = savedInstanceState.getParcelable(argTarg);
        }
    }

    /**
     * Retrieves Markers to be added to the map via the map controller
     * @param inflater for inflating the views in the fragment
     * @param container contains the views
     * @param savedInstanceState the Bundle of arguments required
     * @return the updated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_map_page, container, false);

        MapMarker carMarker = new MapMarker(getActivity(), targetLocation, "Car Position", R.drawable.imagecar);

        mMapView = mapController.getMapView(getActivity(),view, R.id.location_map,savedInstanceState);
        mMap = mapController.getGoogleMap(mMapView);
        mapController.addSingleMarker(mMap, carMarker);
        mapController.setLocation(mMap);
        mapController.centreCameraOnLocation(mMap,targetLocation);

        return view;
    }
    /**
     * Saves the latest information from the fragment
     * @param outState the Bundle containing the information
     */
    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putParcelable(argTarg, targetLocation);
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
