package software.nhut.personalutilitiesforlife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import util.MyDialog;

public class NotificationAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MyDialog.createAppointmentNtf(context, intent.getStringExtra("appointmentcontent"));
    }
}
