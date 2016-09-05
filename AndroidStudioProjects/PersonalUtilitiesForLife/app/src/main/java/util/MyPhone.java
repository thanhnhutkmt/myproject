package util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Message;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import software.nhut.personalutilitiesforlife.QuanLyCuocHenActivity;
import software.nhut.personalutilitiesforlife.QuanLyTinNhanActivity;
import software.nhut.personalutilitiesforlife.R;
import software.nhut.personalutilitiesforlife.adapter.AdapterTextViewMauNen;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.Simpletndbcg;
import software.nhut.personalutilitiesforlife.data.TinNhanDanhBaCuocGoi;

/**
 * Created by Nhut on 6/22/2016.
 */
public class MyPhone {
    public static Map<String, String> getContactBook(ContentResolver cr, boolean keyIsName) {
        Uri uriContact = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = cr.query(uriContact, null, null, null, null);
        Map<String, String> listContact = new HashMap<String, String>();
        if (keyIsName)
            while (cursor.moveToNext()) {
                listContact.put(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)),
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            }
        else
            while (cursor.moveToNext()) {
                listContact.put(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)),
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            }
        return listContact;
    }

    private static final boolean KEYISNAME = true;
    private static final boolean KEYISNUMBER = false;
    public static String replacePhoneNumberWithContactName(String phoneNumber, ContentResolver cr) {
        Map <String, String> contactBook = getContactBook(cr, KEYISNUMBER);
        return contactBook.get(phoneNumber);
    }

    public static String replaceContactNameWithPhoneNumber(String contactName, ContentResolver cr) {
        Map<String, String> contactBook = getContactBook(cr, KEYISNAME);
        String phoneNumber = contactBook.get(contactName);
        return (phoneNumber != null) ? phoneNumber : contactName;
    }

    public static void bringKeyboardDown(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static List<TinNhanDanhBaCuocGoi> getAllMessages(Context context) {
        List<TinNhanDanhBaCuocGoi> listTinNhan = new ArrayList<TinNhanDanhBaCuocGoi>();
        String []arrayUri = {"content://sms/sent", "content://sms/inbox", "content://sms/draft"};
        for (String uri : arrayUri) {
            int i = (uri.equals(arrayUri[0])) ? TinNhanDanhBaCuocGoi.SENT : (uri.equals(arrayUri[1])) ?
                    TinNhanDanhBaCuocGoi.RECEIVED : TinNhanDanhBaCuocGoi.DRAFT;
            Cursor cur = context.getContentResolver().query(Uri.parse(uri), null, null, null, null);
            while (cur.moveToNext()) {
                String rawPhoneNumber = cur.getString(cur.getColumnIndex("address"));
                String phoneNumber = MyStringFormater.standardizePhoneNumber(rawPhoneNumber);
                String contactName = MyPhone.replacePhoneNumberWithContactName(rawPhoneNumber, context.getContentResolver());
                String address = (contactName != null) ? contactName + " - " + phoneNumber + " - " : phoneNumber + " - ";
                String messageTypeName = TinNhanDanhBaCuocGoi.geMessageTypeName(i, context);
                Long time = Long.parseLong(cur.getString(cur.getColumnIndex("date")));
                listTinNhan.add(new TinNhanDanhBaCuocGoi(i, messageTypeName + " " + address +
                        MyDateTime.getDateString(time, AppConstant.FULLTIMEFORMAT_WITHOUTNEWLINE)
                        + "\n" + cur.getString(cur.getColumnIndex("body")), phoneNumber, time));
            }
            cur.close();
        }
        return listTinNhan;
    }

    public static final int HINTID[] = {R.string.AlertDialog_contact_quanlytinnhan_ten,
            R.string.AlertDialog_contact_quanlytinnhan_phonenumber,
            R.string.AlertDialog_contact_quanlytinnhan_email,
            R.string.AlertDialog_contact_quanlytinnhan_skype,
            R.string.AlertDialog_contact_quanlytinnhan_viper,
            R.string.AlertDialog_contact_quanlytinnhan_zalo,
            R.string.AlertDialog_contact_quanlytinnhan_yahoo,
            R.string.AlertDialog_contact_quanlytinnhan_facebook,
            R.string.AlertDialog_contact_quanlytinnhan_twitter,
            R.string.AlertDialog_contact_quanlytinnhan_googleplus
    };
    
    public static List<TinNhanDanhBaCuocGoi> getAllContacts(Context context) {
        Uri uriContact = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(uriContact, null, null, null, null);
        List<TinNhanDanhBaCuocGoi> listContact = new ArrayList<TinNhanDanhBaCuocGoi>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String number = MyStringFormater.standardizePhoneNumber(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            String content = name + " - " + number;
            List<String> listInfo = new ArrayList<String>(); 
            listInfo.add(0, name); listInfo.add(1, number); 
            for (int i = 2; i < HINTID.length; i++) 
                listInfo.add(i, "");
            listContact.add(new TinNhanDanhBaCuocGoi(content, listInfo));
        }
        cursor.close();
        return listContact;
    }

    public static int removeAllMessages(Context context) {
        int count = 0;
        Cursor c = context.getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
        while (c.moveToNext()) {
            try {
                count += context.getContentResolver().delete(Uri.parse("content://sms/" + c.getString(0)), null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    public static void removeAllContacts(Context context) {
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            context.getContentResolver().delete(Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI,
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY))), null, null);
        }
    }

    public static boolean sendSMS(Context context, String smsContent,String... phoneNo) {
        boolean result = true;
        BroadcastReceiver sentbr = new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, R.string.Toast_deliverymessage_sendSMS, Toast.LENGTH_LONG).show();
                context.unregisterReceiver(this);
            }
        };
        BroadcastReceiver deliverybr = new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent intent) {
                int result = R.string.Toast_deliverymessage_sendSMS_OK;
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        result = R.string.Toast_deliverymessage_sendSMS_OK;//"Transmission successful";
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        result = R.string.Toast_deliverymessage_sendSMS_failed;//"Transmission failed";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        result = R.string.Toast_deliverymessage_sendSMS_radio_off;//"Radio off";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        result = R.string.Toast_deliverymessage_sendSMS_noPDU;//"No PDU defined";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        result = R.string.Toast_deliverymessage_sendSMS_noService;//"No service";
                        break;
                }
                final int frs = result; final Context c = context;
                final android.os.Handler handler = new android.os.Handler(new android.os.Handler.Callback() {
                    @Override public boolean handleMessage(Message msg) {
                        if (msg.arg1 == 1) Toast.makeText(c, frs, Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
                new Thread() {
                    public void run() {
                        Message msg = new Message();
                        msg.arg1 = 1; // close progress dialog
                        handler.sendMessageDelayed(msg, 3000);
                    }}.start();
                context.unregisterReceiver(this);
            }
        };
        try {
            for (String pNumber: phoneNo) {
                context.registerReceiver(sentbr, new IntentFilter("sent"));
                context.registerReceiver(deliverybr, new IntentFilter("delivered"));
                PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent("sent"), PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0, new Intent("delivered"), PendingIntent.FLAG_UPDATE_CURRENT);
                ArrayList<String> parts = SmsManager.getDefault().divideMessage(smsContent);
                ArrayList<PendingIntent> listSendPI = new ArrayList<>();
                ArrayList<PendingIntent> listdeliverPI = new ArrayList<>();
                for (String mp : parts) {listSendPI.add(sentPI); listdeliverPI.add(deliverPI);}
                SmsManager.getDefault().sendMultipartTextMessage(pNumber, null, parts, listSendPI, listdeliverPI);
            }
        } catch (Exception e) {
            result = false;
            Toast.makeText(context, "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return result;
    }

    public static List<List<TinNhanDanhBaCuocGoi>> loadDataFromPhoneAndSim(Activity activity,
                                                       List<List<TinNhanDanhBaCuocGoi>> listOnScreen) {
        List<TinNhanDanhBaCuocGoi> listContact = MyPhone.getAllContacts(activity);
        List<TinNhanDanhBaCuocGoi> listMessage = MyPhone.getAllMessages(activity);
        List<TinNhanDanhBaCuocGoi> listCall = MyPhone.getAllCallLogs(activity);
        List<TinNhanDanhBaCuocGoi> listSpinner = new ArrayList<>();
        for (int i = 0; i < ((QuanLyTinNhanActivity)activity).GROUPIDSTRING.length; i++)
            listSpinner.add(new TinNhanDanhBaCuocGoi(
                    activity.getResources().getString(((QuanLyTinNhanActivity)activity).GROUPIDSTRING[i]),
                    ((QuanLyTinNhanActivity)activity).COLORID[i]));
        listOnScreen.add(listSpinner);
        listOnScreen.add(listContact);
        listOnScreen.add(listMessage);
        listOnScreen.add(listCall);
        return listOnScreen;
    }

    public static List<TinNhanDanhBaCuocGoi> getAllCallLogs(Context context) {
        List<TinNhanDanhBaCuocGoi> listCallLog = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null,null, null, null);
        while (cursor.moveToNext()) {
            String phNum = MyStringFormater.standardizePhoneNumber(cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)));
            int callType = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)));
            String callTypeName = TinNhanDanhBaCuocGoi.getCallTypeName(callType, context);
            Long time = Long.parseLong(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE)));
            String callDate = MyDateTime.getDateString(time, AppConstant.FULLTIMEFORMAT_WITHOUTNEWLINE);
            String callDuration = MyStringFormater.convertSecondStringToTimeString(
                    Long.parseLong(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION))));
            listCallLog.add(new TinNhanDanhBaCuocGoi(TinNhanDanhBaCuocGoi.getTypeByCallLog(callType),
                    callTypeName + " " + phNum +  " - " + callDate + "\n" + callDuration, phNum, time));
        }
        cursor.close();
        return listCallLog;
    }

    public static void loadDataFromApp(String thisFeatureFolder, List<List<TinNhanDanhBaCuocGoi>> list) {
        List lt = MyFileIO.loadData(thisFeatureFolder, AppConstant.DATAGTDG);
        if ((lt.size() > 0) && (((List<Object>)lt.get(0)).get(0) instanceof Simpletndbcg)) {
            List<List<Simpletndbcg>> savedList = lt;
            for (int i = 0; i < savedList.size(); i++) {
                List<TinNhanDanhBaCuocGoi> l = new ArrayList<>();
                for (Simpletndbcg s : savedList.get(i))
                    l.add(Simpletndbcg.convertSimpleToFull(s));
                list.add(l);
            }
        }
    }

    public static boolean saveDataApp(String thisFeatureFolder, List<List<TinNhanDanhBaCuocGoi>> list) {
        List<List<Simpletndbcg>> savedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<Simpletndbcg> l = new ArrayList<>();
            for (TinNhanDanhBaCuocGoi t : list.get(i))
                l.add(new Simpletndbcg(t));
            savedList.add(l);
        }
        return MyFileIO.saveData(savedList, thisFeatureFolder, AppConstant.DATAGTDG);
    }

    public static class MyMessageContactCall {
        /* list* (1) listGroup
         *       (2) listcontact
         *       (3) listMessage
         *       (4) listCall
         */
        private List<List<TinNhanDanhBaCuocGoi>> listOnScreen;
        private String storePath;
        private Context context;

        public MyMessageContactCall(List<List<TinNhanDanhBaCuocGoi>> listOnScreen, String storePath, Context context) {
            this.listOnScreen = listOnScreen;
            this.storePath = storePath;
            this.context = context;
        }

        /**
         * Save *OnScreen items to disk
         */
        public boolean saveToDisk() {
            return MyFileIO.saveData(listOnScreen, storePath, AppConstant.DATAGTDG);
        }

        /**
         * Load data from disk and store into *OnScreen items
         */
        public List<List<TinNhanDanhBaCuocGoi>> loadFromDisk() {
            listOnScreen = (List<List<TinNhanDanhBaCuocGoi>>) MyFileIO.loadData(storePath, AppConstant.DATAGTDG);
            return listOnScreen;
        }

        /**
         * Merge data between app and Phone
         *      Action : copy all msgs/contact/call only
         /* list* (0) listGroup     compare content; others ignored : lay tung phan tu : do het nguyen danh sach - co trung : bo qua - khac : add vo
         *        (1) listContact   compare listInfo[1] with content; others ignored
         *        (2) listMessage   compare content, type; others ignored
         *        (3) listCall      compare content, type; others ignored
         */
        public void mergeWithPhone(Activity activity) {
            List<List<TinNhanDanhBaCuocGoi>> list = new ArrayList<>();
            loadDataFromPhoneAndSim(activity, list);
            CompareList c = new CompareList() {@Override public boolean compareTwoList(
                List<TinNhanDanhBaCuocGoi> lPhone, List<TinNhanDanhBaCuocGoi> l, int i, int j) {
                    TinNhanDanhBaCuocGoi tPhone = lPhone.get(i); TinNhanDanhBaCuocGoi t = l.get(j);
                    return tPhone.getContent().equals(t.getContent()) && tPhone.getType() == t.getType()
                            && tPhone.getListInfo().get(1).equals(t.getListInfo().get(1));
                }
            };
            for (int i = 1; i < 4; i ++) merge(list.get(i), listOnScreen.get(i), c);
        }

        private void merge(List<TinNhanDanhBaCuocGoi> lPhone, List<TinNhanDanhBaCuocGoi> l, CompareList c) {
            for (int i = 0; i < lPhone.size(); i++) {
                boolean duplicate = false;
                for (int j = 0; j < l.size(); j++) {
                    if (c.compareTwoList(lPhone, l, i, j)) {
                        duplicate = true; break;
                    }
                }
                if (duplicate) continue;
                else l.add(lPhone.get(i));
            }
        }

        /**
         * Merge data between app and backup
         * Merge listNhom* :
         *      same mean :
         *          same content conly, others ignored
         *          merge action :
         *              set msg of *OnScreen to *OnDisk
         *      difference mean :
         *
         */
        public void mergeWithBackUp() {

        }
    }

    private interface CompareList {
        boolean compareTwoList(List<TinNhanDanhBaCuocGoi> lPhone, List<TinNhanDanhBaCuocGoi> l, int i, int j);
    }

    public static boolean isInternetOn(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        boolean onWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting(); //For WiFi Check
        boolean on3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting(); //For 3G check
        return (onWifi || on3g);
    }

    public static boolean isGPSOn(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static void call(Context context, String phoneNumber) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        context.startActivity(in);
    }

    public static void sendSMS(String phoneNo, String smsContent) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, smsContent, null, null);
    }

    public static void removeAllMessagesPS(Context context) {
        Uri deleteUri = Uri.parse("content://sms");
        Cursor c = context.getContentResolver().query(deleteUri, null, null, null, null);
        while (c.moveToNext()) {
            context.getContentResolver().delete(Uri.parse("content://sms/" + c.getString(0)), null, null);
        }
    }

    public static void removeAllContactsPS(Context context) {
        Cursor cur = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cur.moveToNext()) {
            context.getContentResolver().delete(Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI,
                    cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY))), null, null);
        }
    }

    public static void removeAllCallLogsPS(Context context) {
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, "", null);
        }
    }

    public static void showRingtoneList(final Activity activity) {
        final List<Uri> list = getListRingToneUri(activity);
        List<String> listRingtoneName = new ArrayList<>();
        for (Uri uri : list)
            listRingtoneName.add(RingtoneManager.getRingtone(activity, uri).getTitle(activity));
        final List<Ringtone> listLastRingtone = new ArrayList<>();
        listLastRingtone.add(0, RingtoneManager.getRingtone(activity, list.get(0)));
        MyDialog.showListViewDiaLog(activity, R.string.AlertDialog_title_ringtone_listViewDialg,
            listRingtoneName, new AdapterView.OnItemClickListener() {
                @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (listLastRingtone.get(0).isPlaying()) listLastRingtone.get(0).stop();
                    listLastRingtone.set(0, RingtoneManager.getRingtone(activity, list.get(position)));
                    listLastRingtone.get(0).play();
                }
            }, new AdapterView.OnItemLongClickListener() {
                @Override public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    MyFileIO.writeStringFile(activity.getApplicationInfo().dataDir + AppConstant.FS + AppConstant.THUMUC_QUANLY_TINNHANDANHBA
                            + AppConstant.FS + AppConstant.RINGTONENAME, list.get(position).toString(), false);
                    Toast.makeText(activity, R.string.Toast_savedRingTone_showRingtoneList_MyPhone, Toast.LENGTH_LONG).show();
                    return false;
                }
            });
    }

    public static List<Uri> getListRingToneUri(Activity activity) {
        RingtoneManager ringtoneMgr = new RingtoneManager(activity);
        ringtoneMgr.setType(RingtoneManager.TYPE_ALL);
        Cursor alarmsCursor = ringtoneMgr.getCursor();
        List<Uri> list = new ArrayList<>();
        while (alarmsCursor.moveToNext()) list.add(ringtoneMgr.getRingtoneUri(alarmsCursor.getPosition()));
        return  list;
    }

    public static Uri getSavedRingTone(Context context) {
        return Uri.parse(MyFileIO.readStringFile(context.getApplicationInfo().dataDir + AppConstant.FS
                + AppConstant.THUMUC_QUANLY_TINNHANDANHBA + AppConstant.FS + AppConstant.RINGTONENAME));
    }
}
