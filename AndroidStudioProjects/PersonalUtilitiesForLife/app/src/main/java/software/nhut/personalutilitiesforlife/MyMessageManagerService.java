package software.nhut.personalutilitiesforlife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.TinNhanDanhBaCuocGoi;
import util.MyDateTime;
import util.MyDialog;
import util.MyPhone;
import util.MyStringFormater;

public class MyMessageManagerService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_DELIVER_ACTION.equals(intent.getAction())) {
            int type = TinNhanDanhBaCuocGoi.RECEIVED;
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msgFrom = "", msgBody = "", msgTime = "";
            if (bundle != null) {
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i = 0; i <msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msgFrom += msgs[i].getOriginatingAddress();
                        msgTime += msgs[i].getTimestampMillis();
                        msgBody += msgs[i].getMessageBody();
                    }
                    String phoneNumber = MyStringFormater.standardizePhoneNumber(msgFrom);
                    String prefix = TinNhanDanhBaCuocGoi.geMessageTypeName(type, context);
                    long time = Long.parseLong(msgTime);
                    String content = prefix + " " + phoneNumber + " - " +
                        MyDateTime.getDateString(time, AppConstant.FULLTIMEFORMAT_WITHOUTNEWLINE) + "\n" + msgBody;
                    List<List<TinNhanDanhBaCuocGoi>> l = new ArrayList<>();
                    String thisFeatureFolder = context.getApplicationInfo().dataDir
                            + AppConstant.FS + AppConstant.THUMUC_QUANLY_TINNHANDANHBA;
                    MyPhone.loadDataFromApp(thisFeatureFolder, l);
                    l.get(2).add(0, new TinNhanDanhBaCuocGoi(type, content, phoneNumber, time));
                    MyPhone.saveDataApp(thisFeatureFolder, l);

                    MyDialog.createMessageNtf(context, msgBody);
                    Intent it = new Intent("broadCastMessage");
                    it.putExtra("MessageType", QuanLyTinNhanActivity.SMS);
                    context.sendBroadcast(it);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
//                    it.putExtra("SMS", content);
//                    it.putExtra("SMSFromTo", phoneNumber);
//                    it.putExtra("SMSType", type);