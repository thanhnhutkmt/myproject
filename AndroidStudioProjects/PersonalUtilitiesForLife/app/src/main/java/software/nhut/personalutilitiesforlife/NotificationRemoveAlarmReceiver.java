package software.nhut.personalutilitiesforlife;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationRemoveAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent in = new Intent(context, NotificationAlarmReceiver.class);
        int alarmID = intent.getIntExtra("alarmID", -1);
        if (alarmID > -1)((AlarmManager) context.getSystemService(Context.ALARM_SERVICE))
                .cancel(PendingIntent.getBroadcast(context, alarmID, in, PendingIntent.FLAG_NO_CREATE));
    }
}
