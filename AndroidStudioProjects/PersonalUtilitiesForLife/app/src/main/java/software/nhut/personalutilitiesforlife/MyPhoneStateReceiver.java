package software.nhut.personalutilitiesforlife;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.TinNhanDanhBaCuocGoi;
import util.MyPhone;

public class MyPhoneStateReceiver extends BroadcastReceiver {
    class MyPhoneStateListener extends PhoneStateListener {
        private static final String TAG = "MyPhoneStateListener";
        private int previousState = -1, currentState = -1;
        private Context context;
        private String thisFeatureFolder;
        public MyPhoneStateListener(Context context) {
            this.context = context;
            this.thisFeatureFolder = context.getApplicationInfo().dataDir
                    + AppConstant.FS + AppConstant.THUMUC_QUANLY_TINNHANDANHBA;
        }
        @Override public void onCallStateChanged(int state, String incomingNumber){
            if (currentState != -1) previousState = currentState;
            currentState = state;
            if (previousState != -1 && previousState != TelephonyManager.CALL_STATE_IDLE
                    && currentState == TelephonyManager.CALL_STATE_IDLE) {
                final Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        if (msg.arg1 == 1) {
                            List<TinNhanDanhBaCuocGoi> list = MyPhone.getAllCallLogs(context);
                            List<List<TinNhanDanhBaCuocGoi>> listData = new ArrayList<>();
                            MyPhone.loadDataFromApp(thisFeatureFolder, listData);
                            listData.get(3).addAll(list);
                            MyPhone.saveDataApp(thisFeatureFolder, listData);
                            MyPhone.removeAllCallLogsPS(context);
                        }
                        return false;
                    }
                });
                new Thread() {
                    public void run() {
                        Message msg = new Message();
                        msg.arg1 = 1; // close progress dialog
                        handler.sendMessageDelayed(msg, 2000);
                }}.start();
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE))
                .listen(new MyPhoneStateListener(context), PhoneStateListener.LISTEN_CALL_STATE);
    }
}
