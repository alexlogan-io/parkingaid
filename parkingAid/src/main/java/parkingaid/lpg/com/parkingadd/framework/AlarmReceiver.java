package parkingaid.lpg.com.parkingadd.framework;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import parkingaid.lpg.com.parkingadd.MainActivity;
import parkingaid.lpg.com.parkingadd.R;

/**
 * Sets up all the information held in the notification including the launching of the application
 * if required. The notification is then issued.
 */
public class AlarmReceiver extends BroadcastReceiver {

   long[] thrice = { 0, 100, 400, 100, 400, 100 };
    /**
     * method that sets the content of a notification along with the pending intent to launch
     * the app if the user is not currently using it
     * @param context the context of the activity that starts the notification
     * @param intent
     */
    public void onReceive(Context context, Intent intent){

       NotificationManager mNM;
       mNM = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

       //Set the icon, scrolling text and timestamp
       final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

       NotificationCompat.Builder builder = mBuilder.setSmallIcon(R.mipmap.ic_launcher)
               .setContentTitle("Parking Aid")
               .setContentText("You have ten minutes until your leave time!")
               .setTicker("Leave time close!")
               .setSound(Uri.parse("android.resource://parkingadd.com.lpg.parkingaid"))
               .setVibrate(thrice)
               .setWhen(System.currentTimeMillis())
               .setShowWhen(true);

       //The PendingIntent to launch our activity if the user selects this notification
       PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

       //Set the info for the views that show in the notification panel.
       builder.setContentIntent(contentIntent);
       int mNotificationId = 001;

       //Builds the notification and issues it.
       mNM.notify(mNotificationId, mBuilder.build());

   }

}
