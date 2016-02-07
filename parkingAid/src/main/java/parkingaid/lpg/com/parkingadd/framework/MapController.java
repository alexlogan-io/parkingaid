package parkingaid.lpg.com.parkingadd.framework;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Creates a Google Map and sets it to the view, deals with adding single and multiple markers,
 * centering the camera and getting the user's current location
 */
public class MapController extends Fragment {

    public MapController() {
    }

    /**
     * gets the current Map View, error catching if map does not initialise
     * @param context the context of the current activity
     * @param view current view
     * @param viewId current view id
     * @param savedInstanceState
     * @return
     */
    public MapView getMapView(Context context, View view, int viewId, Bundle savedInstanceState){
        MapView mMapView = (MapView) view.findViewById(viewId);
        mMapView.onCreate(null);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mMapView;
    }

    /**
     * adds the map view to an instance of Google Maps
     * @param mMapView the current map view
     * @return the Google Map
     */
    public GoogleMap getGoogleMap(MapView mMapView){
        GoogleMap mMap = mMapView.getMap();
        return mMap;
    }

    /**
     * Adds a single marker to the map
     * @param mMap the Google Map to add the marker to
     * @param mapMarker the marker to be added containing the Lat and Long
     */
    public void addSingleMarker(GoogleMap mMap, MapMarker mapMarker){
        MapsInitializer.initialize(mapMarker.getContext());
        MarkerOptions mp = new MarkerOptions();
        mp.position(new LatLng(mapMarker.getLocation().getLatitude(), mapMarker.getLocation().getLongitude()));
        mp.title(mapMarker.getTitle());
        mp.icon(BitmapDescriptorFactory.fromResource(mapMarker.getMarkerImage()));
        mMap.addMarker(mp);
    }

    /**
     * Adds multiple markers to the map
     * @param mMap the Google Map to add the marker to
     * @param mapMarkers the markers to be added containing each Lat and Long
     */
    public void addMultipleMarkers(GoogleMap mMap, ArrayList<MapMarker> mapMarkers){
        for (MapMarker mapMarker : mapMarkers){
            MapsInitializer.initialize(mapMarker.getContext());
            MarkerOptions mp = new MarkerOptions();
            mp.position(new LatLng(mapMarker.getLocation().getLatitude(), mapMarker.getLocation().getLongitude()));
            mp.title(mapMarker.getTitle());
            mMap.addMarker(mp);
        }
    }

    /**
     * Sets the current user location on the map
     * @param mMap the current Google Map
     */
    public void setLocation(GoogleMap mMap){
        mMap.setMyLocationEnabled(true);
        mMap.getMyLocation();
    }

    /**
     * Centres the camera on a specified location on the Google Map
     * @param mMap the current Google Map
     * @param targetLocation the location to be centred
     */
    public void centreCameraOnLocation(GoogleMap mMap, Location targetLocation){
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(targetLocation.getLatitude(), targetLocation.getLongitude()));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(17);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }
}
