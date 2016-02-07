package parkingaid.lpg.com.parkingadd;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.gson.Gson;

import parkingaid.lpg.com.parkingadd.framework.AppPagerAdapter;
import parkingaid.lpg.com.parkingadd.framework.SetUpTabView;
import parkingaid.lpg.com.parkingadd.framework.SharedPreferenceController;

/**
 * Controls all of the fragments and the data passed to and from each of them, also acts as a listener
 * for app specific information
 */
public class MainActivity extends android.support.v4.app.FragmentActivity implements  android.location.LocationListener, ActionBar.TabListener {

    private AppPagerAdapter mAppPagerAdapter;
    private ViewPager mViewPager;
    private Location _currentLocation;
    private ParkingEvent parkEvent;
    private SharedPreferences prefs;
    private Gson gSon = new Gson();
    private FormPageFragment fragment;
    private Location lastKnownLocation;

    /**
     * @return the last known location
     */
    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    /**
     * Deals with first time launching, ensuring no duplicate information. Sets up the location
     * manager for location tracking and attaches fragments to the action bar as tabs
     * @param savedInstanceState the Bundle of arguments required
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 1, this);

        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 1, this);
        lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        fragment = FormPageFragment.newInstance(lastKnownLocation);

        SetUpTabView setUpTabView = new SetUpTabView();

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        boolean isFirst = prefs.getBoolean("firstTime", true);

        if (isFirst){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", false);
            editor.apply();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(android.R.id.content, fragment);
            fragmentTransaction.commit();
        }
        else{
            if(savedInstanceState == null) {
                parkEvent = getProfileInfo();
                if(parkEvent == null){
                    SharedPreferenceController sharedPreferenceController = new SharedPreferenceController(this);
                    sharedPreferenceController.clearData();
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                }
                if (parkEvent.getParkLocation().getLatitude() == 0 && parkEvent.getParkLocation().getLongitude() == 0)
                    _currentLocation = getLastKnownLocation();
                else
                    _currentLocation = parkEvent.getParkLocation();
            }else{
                parkEvent = savedInstanceState.getParcelable("parkEvent");
                _currentLocation = savedInstanceState.getParcelable("currentLocation");
            }

            mAppPagerAdapter = new AppPagerAdapter(getSupportFragmentManager());
            mAppPagerAdapter.addFragment(HomePageFragment.newInstance(parkEvent), "Home");
            mAppPagerAdapter.addFragment(DirectionViewFragment.newInstance(_currentLocation, parkEvent.getParkLocation()), "Direction");
            mAppPagerAdapter.addFragment(MapFragment.newInstance(parkEvent.getParkLocation()), "Map");

            View view = getWindow().getDecorView().findViewById(android.R.id.content);

            ActionBar actionBar = getActionBar();

            actionBar = setUpTabView.newActionBar(actionBar);

            mViewPager = setUpTabView.newViewPager(view, R.id.pager, mAppPagerAdapter, actionBar);

            setUpTabView.addTabsToActionBar(mAppPagerAdapter, actionBar, this);
        }
    }

    /**
     * Required method which informs the action bar when the tab is no longer selected
     * @param tab
     * @param fragmentTransaction
     */
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * Required method which informs the action bar which tab is selected
     * @param tab the current tab(fragment)
     * @param fragmentTransaction
     */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    /**
     * Required method which tells the action bar when the tab is reselected
     * @param tab
     * @param fragmentTransaction
     */
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * Retrieves parking information from the shared preferences
     * @return the parking event
     */
    public ParkingEvent getProfileInfo() {
        String json = prefs.getString("ParkingEvent", "");
        parkEvent = gSon.fromJson(json, ParkingEvent.class);
        return parkEvent;
    }

    /**
     * Used to give a fragment a tag
     * @param viewPagerId the current view
     * @param index the position of the fragment
     * @return the tag
     */
    private static String makeFragment(int viewPagerId, int index){
        return "android:switcher:" + viewPagerId + ":" + index;
    }

    /**
     * A method that notifies and updates all fragments with the latest location information
     * @param location the updated location
     */
    @Override
    public void onLocationChanged(Location location) {
        if(parkEvent!=null) {
            String temp = makeFragment(R.id.pager, 1);
            DirectionViewFragment directionViewFragment = (DirectionViewFragment) getSupportFragmentManager().findFragmentByTag(temp);
            if (directionViewFragment != null) {
                directionViewFragment.updateLocation(location);
            }
            if (fragment != null) {
                fragment.updateLocation(location);
            }
        }
    }

    /**
     * Required override method for class of type LocationListener
     * @param provider
     * @param status
     * @param extras
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Required override method class of type LocationListener
     * @param provider
     */
    @Override
    public void onProviderEnabled(String provider) {

    }

    /**
     * Required override method class of type LocationListener
     * @param provider
     */
    @Override
    public void onProviderDisabled(String provider) {
    }

    /**
     * Method to pause the activity when the app is closed
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Saves the latest information from the fragment
     * @param outState the Bundle containing the information
     */
    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putParcelable("parkEvent",parkEvent);
        outState.putParcelable("currentLocation",_currentLocation);
        super.onSaveInstanceState(outState);
    }
}
