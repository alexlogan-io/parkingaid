package parkingaid.lpg.com.parkingadd;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import parkingaid.lpg.com.parkingadd.framework.SharedPreferenceController;

/**
 * Fragment that deals with displaying app specific user information
 */
public class HomePageFragment extends android.support.v4.app.Fragment {

    private ParkingEvent parkEvent;
    private static final String key = "PARK";
    private View view;
    private TextView parkingTime;
    private TextView leavingTime;
    private TextView notes;
    private Button newPark;
    private SharedPreferenceController prefs;

    /**
     * Creates a new instance of HomePageFragment, stores any input information and
     * acts as a Constructor
     * @param pEvent the current parkingEvent
     * @return a HomePageFragment
     */
    public static HomePageFragment newInstance(ParkingEvent pEvent) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle parkingInfo = new Bundle();
        parkingInfo.putParcelable(key, pEvent);
        fragment.setArguments(parkingInfo);
        return fragment;
    }

    public HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Creates the HomepageFragment and ensures that the savedInstanceState is never null when
     * it's created
     * @param savedInstanceState the Bundle of arguments required
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            parkEvent = getArguments().getParcelable(key);
        }else{
            parkEvent = savedInstanceState.getParcelable("parkEvent");
        }
    }

    /**
     * Creates the view containing the user's information and sets up listeners to deal with
     * button clicks, list clicks or form entries
     * @param inflater for inflating the views in the fragment
     * @param container contains the views
     * @param savedInstanceState the Bundle of arguments required
     * @return the updated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_page, container, false);

        parkingTime = (TextView) view.findViewById(R.id.parkingTime);
        leavingTime = (TextView) view.findViewById(R.id.leavingTime);
        notes = (TextView) view.findViewById(R.id.parkingNotes);

        parkingTime.setText(" Arrival Time: " + leadingZero(parkEvent.getParkTimeHours()) + ":" + leadingZero(parkEvent.getParkTimeMins()));
        leavingTime.setText(" Departure Time: " + leadingZero(parkEvent.getLeaveTimeHours()) + ":" + leadingZero(parkEvent.getLeaveTimeMins()));
        notes.setText(parkEvent.getNotes());

        newPark = (Button) view.findViewById(R.id.newPark);

        //button to send user to the form page and clear any data previously entered
        newPark.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                prefs = new SharedPreferenceController(getActivity());
                prefs.clearData();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }

        });

        return view;
    }

    /**
     * ensures the parking cost is formatted correctly
     * @param time
     * @return
     */
    public String leadingZero (int time){
        if(time<10)
            return "0" + Integer.toString(time);
        else
            return Integer.toString(time);
    }

    /**
     * Method to attach the fragment to the MainActivity
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    /**
     * Required method to remove fragment from the MainActivity
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Saves the latest information from the fragment
     * @param outState the Bundle containing the information
     */
    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putParcelable("parkEvent", parkEvent);
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
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}

