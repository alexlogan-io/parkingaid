package parkingaid.lpg.com.parkingadd.framework;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Determines the time to send the notification and passes this to the AlarmManager
 */
public class AlarmService {
    private Context context;
    private PendingIntent mAlarmSender;
    /**
     * Constructor for the AlarmService
     * @param context context of the activity which starts the AlarmService
     */
    public AlarmService(Context context) {
        this.context = context;
        mAlarmSender = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver.class), 0);
    }
    /**
     * Gets the current time of day and alters it so that it matches the time for the notification
     * to be sent, this time is then set in the AlarmManager
     * @param leaveHours
     * @param leaveMinutes
     */
    public void startAlarm(int leaveHours, int leaveMinutes){
        //Set the alarm to the leaving time minus 10 minutes
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, leaveHours);
        c.set(Calendar.MINUTE, leaveMinutes-10);

        long firstTime = c.getTimeInMillis();

        // Schedule the alarm
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, firstTime, mAlarmSender);
    }

}
