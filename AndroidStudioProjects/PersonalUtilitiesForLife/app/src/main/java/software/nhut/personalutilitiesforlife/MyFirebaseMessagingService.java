package software.nhut.personalutilitiesforlife;

/**
 * Created by Nhut on 10/7/2016.
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.TinNhanDanhBaCuocGoi;
import util.MyDateTime;
import util.MyDialog;
import util.MyPhone;
import util.MyStringFormater;

/**
 * Created by Nhut on 7 Oct 2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage == null) return;
        processDataMessage(remoteMessage);

        RemoteMessage.Notification ntf = remoteMessage.getNotification();
        if (ntf == null) return;

        String msgBody = "\"" + ntf.getTitle() + "\" : " + ntf.getBody();
        Context context = getApplicationContext();
        int type = TinNhanDanhBaCuocGoi.RECEIVED;
        String phoneNumber = "FCM server";
        String title = phoneNumber + " -> \"" + ntf.getTitle() + "\"";
        long msgTime = System.currentTimeMillis();
        String prefix = TinNhanDanhBaCuocGoi.geMessageTypeName(type, context);
        String content = prefix + " " + phoneNumber + " - " +
                MyDateTime.getDateString(msgTime, AppConstant.FULLTIMEFORMAT_WITHOUTNEWLINE) + "\n" + msgBody;
        String thisFeatureFolder = context.getApplicationInfo().dataDir
                + AppConstant.FS + AppConstant.THUMUC_QUANLY_TINNHANDANHBA;
        List<List<TinNhanDanhBaCuocGoi>> l = new ArrayList<>();
        MyPhone.loadDataFromApp(thisFeatureFolder, l);
        l.get(2).add(0, new TinNhanDanhBaCuocGoi(type, content, phoneNumber, msgTime));
        MyPhone.saveDataApp(thisFeatureFolder, l);
        MyDialog.createFCMNtf(context, msgBody, title);
    }

    void processDataMessage(RemoteMessage remoteMessage) {
        //simple data processing for demo
        String from = remoteMessage.getFrom();
        String msgBody = remoteMessage.getData().get("message");
        msgBody = from.equals(AppConstant.SENDER_ID) ? "My app server " + from + " -> FCM server -> " + msgBody :
                "app server -> FCM server -> " + from + " : " + msgBody;
        Log.i("MyTag", msgBody);
    }
}