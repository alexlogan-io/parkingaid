package parkingaid.lpg.com.parkingadd;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import parkingaid.lpg.com.parkingadd.framework.AlarmReceiver;
import parkingaid.lpg.com.parkingadd.framework.AlarmService;
import parkingaid.lpg.com.parkingadd.framework.SharedPreferenceController;

/**
 * Fragment for user input
 */
public class FormPageFragment extends Fragment {

    private Location parkLocation = new Location("x");;
    private TimePicker timePicker1;
    private Button parkButton;
    private EditText notesBox;
    private Calendar parkTime;
    private ParkingEvent currentPark;
    private String notes;
    private SharedPreferenceController preferenceController;
    private double parkingCostPerHr;
    private double parkingCost;
    private TextView cost;

    /**
     * Creates a new instance of FormPageFragment, acts as a Constructor
     * @param lastKnown lastKnown location
     * @return the FormPageFragment
     */
    public static FormPageFragment newInstance(Location lastKnown) {
        FormPageFragment fragment = new FormPageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("lastKnown", lastKnown);
        fragment.setArguments(bundle);
        return fragment;
    }

    public FormPageFragment() {
        // Required empty public constructor
    }

    /**
     * If there is a bundle, use the last known location as the park location
     * @param savedInstanceState the Bundle of arguments required
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            parkLocation = getArguments().getParcelable("lastKnown");
        }
    }

    /**
     * Creates the view containing all of the form information and dynamic cost calculator
     * @param inflater for inflating the views in the fragment
     * @param container contains the views
     * @param savedInstanceState the Bundle of arguments required
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_form_page, container, false);

        preferenceController = new SharedPreferenceController(getActivity());

        //set parking costs as n pounds per hour
        parkingCostPerHr = 1;

        timePicker1 = (TimePicker) view.findViewById(R.id.timePicker1);

        cost = (TextView) view.findViewById(R.id.cost);

        //changes parking cost dynamically as user scrolls through hours for leaving time
        timePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hour, int minute) {
                parkingCost = 0;
                Calendar startTime = Calendar.getInstance();

                //calculates the parking cost dynamically and ensures it displays correctly
                if (parkingCostPerHr % 1 == 0){
                    if((hour - startTime.getTime().getHours()) <= 0) {
                        cost.setText("Estimated Cost: £0.00");
                    }
                    else if(hour - startTime.getTime().getHours() >= 1 && minute >= startTime.getTime().getMinutes()) {
                        parkingCost = parkingCost + ((hour - startTime.getTime().getHours()) * parkingCostPerHr);
                        cost.setText("Estimated Cost: £" + parkingCost + "0");
                    }
                    else{
                        parkingCost = parkingCost + (((hour - startTime.getTime().getHours()) - 1) * parkingCostPerHr);
                        cost.setText("Estimated Cost: £" + parkingCost + "0");
                    }
                }
                else {
                    if((hour - startTime.getTime().getHours()) < 0) {
                        cost.setText("Estimated Cost: £0.00");
                    }
                    else if(hour - startTime.getTime().getHours() >= 1 && minute >= startTime.getTime().getMinutes()) {
                        parkingCost = parkingCost + ((hour - startTime.getTime().getHours()) * parkingCostPerHr);
                        cost.setText("Estimated Cost: £" + String.format("%.2f", parkingCost));
                    }
                    else{
                        parkingCost = parkingCost + (((hour - startTime.getTime().getHours()) - 1) * parkingCostPerHr);
                        cost.setText("Estimated Cost: £" + String.format("%.2f", parkingCost));
                    }
                }
            }
        });

        notesBox = (EditText) view.findViewById(R.id.notes);
        parkButton = (Button) view.findViewById(R.id.parkButton);
        //starts the alarm service
        final AlarmService AS = new AlarmService(getActivity().getApplicationContext());
        //Button to start a new parking instance
        parkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                notes = notesBox.getText().toString();
                parkTime = Calendar.getInstance();

                currentPark = new ParkingEvent(parkTime.getTime().getHours(),
                        parkTime.getTime().getMinutes(), timePicker1.getCurrentHour(),
                        timePicker1.getCurrentMinute(), notes, parkLocation);

                //get user set leaving time and send to alarm service
                AS.startAlarm(timePicker1.getCurrentHour(), timePicker1.getCurrentMinute());

                preferenceController.firstLaunchDone();
                preferenceController.saveLocalInfo(currentPark, "ParkingEvent");
                Intent currLoc = new Intent(getActivity(), MainActivity.class);
                startActivity(currLoc);
            }
        });
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
     * Required method for detaching a fragment from an activity
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Updates the current location
     * @param location the current location
     */
    public void updateLocation(Location location){
        this.parkLocation = location;
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
