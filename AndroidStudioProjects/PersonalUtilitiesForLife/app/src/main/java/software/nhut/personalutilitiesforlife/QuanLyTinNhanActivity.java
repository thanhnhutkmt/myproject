package software.nhut.personalutilitiesforlife;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.CallLog;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.textservice.TextInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import software.nhut.personalutilitiesforlife.adapter.AdapterTextViewMauNen;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.TinNhanDanhBaCuocGoi;
import util.InputData;
import util.MyData;
import util.MyDateTime;
import util.MyDialog;
import util.MyFileIO;
import util.MyPhone;
import util.MyStringFormater;

import static util.MyPhone.*;

public class QuanLyTinNhanActivity extends AppCompatActivity {
   /*
    *  listAdapter
    *           (0) listGroup_spinner
    *           (1) list
    *           (2) listGroupFilteredContact
    *           (3) listGroupFilteredMessage
    *           (4) listGroupFilteredCall
   /*  loadedlist*  (0) listGroup
    *               (1) listcontact
    *               (2) listMessage
    *               (3) listCall
    */
    private List<AdapterTextViewMauNen> listAdapter = new ArrayList<>();
    private int id[];
    private int index = 1;
    private int itemIndex = 0;
    private Menu myOptionMenu;
    public final int []GROUPIDSTRING = {
            R.string.groupname_quanlytinnhan_Allcontacts, R.string.groupname_quanlytinnhan_Allmessages,
            R.string.groupname_quanlytinnhan_Allcalllogs, R.string.groupname_quanlytinnhan_Love,
            R.string.groupname_quanlytinnhan_Relative, R.string.groupname_quanlytinnhan_Friend,
            R.string.groupname_quanlytinnhan_Teacher, R.string.groupname_quanlytinnhan_Partner,
            R.string.groupname_quanlytinnhan_Temp, R.string.groupname_quanlytinnhan_Other,
            R.string.groupname_quanlytinnhan_Spam
    };
    public final int []COLORID = {Color.BLACK, Color.BLACK, Color.BLACK, Color.RED, Color.YELLOW,
            Color.BLUE, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.DKGRAY, Color.GRAY};
    private String thisFeatureFolder;
    private boolean finishSearching;
    private boolean searchResult = false;

    public static final int MMS = 1;
    public static final int SMS = 2;
    public static final int HeadlessMS = 3;
    BroadcastReceiver broadcastReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int messageType = intent.getIntExtra("MessageType", 0);
            switch(messageType) {
                case SMS : nhanSMS(intent); break;
                case MMS : nhanMMS(intent); break;
                case HeadlessMS : nhanHeadlessSMS(intent); break;
                default:
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_quan_ly_tin_nhan);
        Log.i("MyTag", "onCreate()");
        initialize();
        addControl();
        hienThiDuLieu();
    }

    private void initialize() {
        Log.i("MyTag", "initialize()");
        int notificationID = getIntent().getIntExtra("NotificationID", -1);
        if (notificationID != -1)
            ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).cancel(notificationID);
        thisFeatureFolder = getApplicationInfo().dataDir
                + AppConstant.FS + AppConstant.THUMUC_QUANLY_TINNHANDANHBA;
        MyFileIO.makeFolder(thisFeatureFolder);
        List<List<TinNhanDanhBaCuocGoi>> l = new ArrayList<>();
        if ((new File(thisFeatureFolder)).listFiles().length > 0)
            MyPhone.loadDataFromApp(thisFeatureFolder, l);
        if (l.size() != 4) {
            MyPhone.loadDataFromPhoneAndSim(this, l);
            MyPhone.saveDataApp(thisFeatureFolder, l);
        }
        listAdapter.add(0, new AdapterTextViewMauNen(this, R.layout.item_textviewmaunen, l.get(0), false));
        for (int i = 1; i < 3; i++) listAdapter.add(i, new AdapterTextViewMauNen(
                this, R.layout.item_textviewmaunen, new ArrayList<TinNhanDanhBaCuocGoi>()));
        for (int i = 3; i < 5; i++) listAdapter.add(i, new AdapterTextViewMauNen(
                this, R.layout.item_textviewmaunen, new ArrayList<TinNhanDanhBaCuocGoi>(), false));
    }

    private void addControl() {
        Log.i("MyTag", "addControl()");
        //find Controls
        id = new int[5];
        id[0] = R.id.spinner_danhsachnhom_quanlytinnhan;
        id[1] = R.id.listView_quanlytinnhan;
        id[2] = R.id.listView_groupfiltered_contact;
        id[3] = R.id.listView_groupfiltered_message;
        id[4] = R.id.listView_groupfiltered_call;

        ((Spinner)findViewById(id[0])).setAdapter(listAdapter.get(0));
        for (int i = 1; i < 5; i++) ((ListView)findViewById(id[i])).setAdapter(listAdapter.get(i));

        ((Spinner)findViewById(id[0])).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MyTag", "spinner onItemSelected()");
                searchResult = false;
                index = position;
                hienThiDuLieu_GroupChon();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void hienThiDuLieu() {
        Log.i("MyTag", "hienThiDuLieu()");
        ((Spinner)findViewById(id[0])).setSelection(index, true);
    }

    private void hienThiDuLieu_GroupChon() {
        Log.i("MyTag", "hienThiDuLieu_GroupChon()");
        if (myOptionMenu != null) {
            myOptionMenu.clear();
            if (index > GROUPIDSTRING.length - 1) setMenu_addedgroup();
            else if (index == 0) setMenu_allcontact();
            else if (index == 10) setMenu_spam();
            else setMenu_other();
        }
        List<List<TinNhanDanhBaCuocGoi>> l = new ArrayList<>();
        MyPhone.loadDataFromApp(thisFeatureFolder, l);
        if (index < 3) {
            for (int i = 2; i < 5; i++) ((ListView)findViewById(id[i])).setVisibility(View.GONE);
            ((ListView)findViewById(id[1])).setVisibility(View.VISIBLE);
            listAdapter.get(1).setListContent(l.get(index + 1));
            MyDialog.scaleListView((ListView)findViewById(id[1]));
        } else if (index == 10) {
            for (int i = 2; i < 5; i++) ((ListView)findViewById(id[i])).setVisibility(View.VISIBLE);
            ((ListView)findViewById(id[1])).setVisibility(View.GONE);
            List<String> listSpam = (List<String>) MyFileIO.loadData(thisFeatureFolder, AppConstant.SPAM_LIST);
            if (listSpam != null && listSpam.size() > 0) {
                List<TinNhanDanhBaCuocGoi> listSpamAdapted = new ArrayList<>();
                for (String s : listSpam) listSpamAdapted.add(new TinNhanDanhBaCuocGoi(s));
                l.set(1, listSpamAdapted);
                for (int k = 2; k < 4; k++) {
                    for (int i = 0; i < l.get(k).size(); i++) {
                        boolean isInGroup = false;
                        for (int j = 0; j < listSpam.size(); j++)
                            if (l.get(k).get(i).getContent().contains(listSpam.get(j))) {
                                isInGroup = true;
                                break;
                            }
                        if (!isInGroup) {
                            l.get(k).remove(i);
                            i -= 1;
                        }
                    }
                }
            } else for (int i = 1; i < 4; i++) l.set(i, new ArrayList<TinNhanDanhBaCuocGoi>());
            for (int i = 0; i < 3; i++) listAdapter.get(i + 2).setListContent(l.get(i + 1));
            for (int i = 2; i < 5; i++) MyDialog.scaleListView((ListView) findViewById(id[i]));
        } else {
            for (int i = 2; i < 5; i++) ((ListView)findViewById(id[i])).setVisibility(View.VISIBLE);
            ((ListView)findViewById(id[1])).setVisibility(View.GONE);
            for (int i = 0; i < l.get(1).size(); i++) {
                boolean isInGroup = false;
                for (int j = 0; j < l.get(1).get(i).getGroupNumber().size(); j++)
                    if (l.get(1).get(i).getGroupNumber().get(j) == index) {
                        isInGroup = true;
                        break;
                    }
                if (!isInGroup) {
                    l.get(1).remove(i);
                    i -= 1;
                }
            }
            for (int k = 2; k < 4; k++) {
                for (int i = 0; i < l.get(k).size(); i++) {
                    boolean isInGroup = false;
                    for (int j = 0; j < l.get(1).size(); j++)
                        if (l.get(k).get(i).getPhoneNumber().equals(l.get(1).get(j).getPhoneNumber())) {
                            isInGroup = true;
                            break;
                        }
                    if (!isInGroup) {
                        l.get(k).remove(i);
                        i -= 1;
                    }
                }
            }

            for (int i = 0; i < 3; i++) listAdapter.get(i + 2).setListContent(l.get(i + 1));
            for (int i = 2; i < 5; i++) MyDialog.scaleListView((ListView) findViewById(id[i]));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        myOptionMenu = menu;
        setMenu_other();
        return super.onCreateOptionsMenu(menu);
    }

    private void setMenu_allcontact() {
        getMenuInflater().inflate(R.menu.menu_quanlytinnhan_allcontact, myOptionMenu);
    }

    private void setMenu_other() {
        getMenuInflater().inflate(R.menu.menu_quanlytinnhan, myOptionMenu);
    }

    private void setMenu_addedgroup() {
        getMenuInflater().inflate(R.menu.menu_quanlytinnhan_addedgroup, myOptionMenu);
    }

    private void setMenu_spam() {
        getMenuInflater().inflate(R.menu.menu_quanlytinnhan_spam, myOptionMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setMenuItemEvent(item);
        return super.onOptionsItemSelected(item);
    }

    private void setMenuItemEvent(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnuTaoNhomMoi_quanlytinnhan) taoNhomMoi();
        else if (id == R.id.mnuThietDatNhom_quanlytinnhan) thietDatNhom();
        else if (id == R.id.mnuXoaNhom_quanlytinnhan) xoaNhomHienTai();
        else if (id == R.id.mnuTaoContactMoi_quanlytinnhan) taoContactMoi();
        else if (id == R.id.mnuXoaHetTinNhan_quanlytinnhan || id == R.id.mnuXoaHetContact_quanlytinnhan) xoaHetTinNhan();
        else if (id == R.id.mnuXoaNhieuTinNhan_quanlytinnhan || id == R.id.mnuXoaNhieuContact_quanlytinnhan) xoaNhieuTinNhan();
        else if (id == R.id.mnuSapXepTheoThoiGian_quanlytinnhan) sapXepTheo(TinNhanDanhBaCuocGoi.SORTBYTIME);
        else if (id == R.id.mnuSapXepTheoContact_quanlytinnhan) sapXepTheo(TinNhanDanhBaCuocGoi.SORTBYCONTACT);
        else if (id == R.id.mnuSapXepTheoGroup_quanlytinnhan) sapXepTheo(TinNhanDanhBaCuocGoi.SORTBYGROUP);
        else if (id == R.id.mnuSapXepTheoLoai_quanlytinnhan) sapXepTheo(TinNhanDanhBaCuocGoi.SORTBYTYPE);
        else if (id == R.id.mnuDaoSapXep_quanlytinnhan) sapXepDao();
        else if (id == R.id.mnuTimKiem_quanlytinnhan) timKiem();
        else if (id == R.id.mnuGuiTinNhan_quanlytinnhan) guiTinNhan(null);
        else if (id == R.id.mnuXoaDia_quanlytinnhan) xoaDia();
        else if (id == R.id.mnuXoaPhoneSim_quanlytinnhan) xoaPhoneSim();
        else if (id == R.id.mnuLayDataPhone_quanlytinnhan) ghepVoiDuLieuPhoneVaSim();
        else if (id == R.id.mnuLayDataFile_quanlytinnhan) ghepVoiFile();
        else if (id == R.id.mnuThietDatNhacChuongTinNhan_quanlytinnhan) chonNhacChuong();
        else if (id == R.id.mnuImportContactFromFile_quanlytinnhan) importContactFromFile(this);
        else if (id == R.id.mnuAddNumberKeyword_quanlytinnhan) addSpamNumberAndKeyword();
        else if (id == R.id.mnuDeleteNumberKeyword_quanlytinnhan) deleteSpamNumberKeywords();
        else if (id == R.id.mnuDeleteMessageCalllog_quanlytinnhan) deleteSpam();
    }

    private void deleteSpam() {
        List<List<TinNhanDanhBaCuocGoi>> l = new ArrayList<>();
        MyPhone.loadDataFromApp(thisFeatureFolder, l);
        List<TinNhanDanhBaCuocGoi> listSpamContent;
        for (int i = 2; i < 4; i++) {
            listSpamContent = listAdapter.get(i + 1).getListContent();
            for (int j = 0; j < l.get(i).size(); j++)
                for (int k = 0; k < listSpamContent.size(); k++)
                    if (l.get(i).get(j).getContent().equals(listSpamContent.get(k).getContent())) {
                        listSpamContent.remove(k);
                        k--;
                        l.get(i).remove(j);
                        j--;
                    }
        }
        MyPhone.saveDataApp(thisFeatureFolder, l);
        hienThiDuLieu_GroupChon();
    }

    private void deleteSpamNumberKeywords() {
        List<TinNhanDanhBaCuocGoi> list = listAdapter.get(2).getListContent();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()) {
                list.remove(i);
                i--;
            }
        }
        List<String> listSpam = new ArrayList<>();
        for (TinNhanDanhBaCuocGoi t : list) listSpam.add(t.getContent());
        MyFileIO.saveData(listSpam, thisFeatureFolder, AppConstant.SPAM_LIST);
        hienThiDuLieu_GroupChon();
    }

    private void addSpamNumberAndKeyword() {
        final List<List<TinNhanDanhBaCuocGoi>> l = new ArrayList<>();
        MyPhone.loadDataFromApp(thisFeatureFolder, l);
        MyDialog.showSpamDialog(this, l.get(1), new InputData() {
            @Override public void inputData(String s) {}
            @Override public void inputData(String... s) {}
            @Override public void inputData(String s, int color) {}
            @Override public void inputData(List<String> s) {
                List<String> spamList = (List<String>) MyFileIO.loadData(thisFeatureFolder, AppConstant.SPAM_LIST);
                if (spamList == null) {
                    spamList = new ArrayList<>();
                    spamList.addAll(s);
                } else if (spamList.size() == 0) spamList.addAll(s);
                else MyData.addAllWithoutDuplication(spamList, s, false);
                MyFileIO.saveData(spamList, thisFeatureFolder, AppConstant.SPAM_LIST);
                hienThiDuLieu_GroupChon();
            }
        }, null);
    }

    private void importContactFromFile(Context context) {
        final List<AlertDialog> listDialog = new ArrayList<>();
        listDialog.add(MyDialog.selectFileDialog(context, new InputData() {
            @Override public void inputData(String s) {} @Override public void inputData(List<String> s) {} @Override public void inputData(String s, int color) {}
            @Override public void inputData(String... s) {
                String fileContent;
                if ((new File(s[0])).length() < 10001 && (fileContent = MyFileIO.readStringFile(s[0])).contains("#_#")) {
                    List<List<TinNhanDanhBaCuocGoi>> list = new ArrayList<>();
                    MyPhone.loadDataFromApp(thisFeatureFolder, list);
                    for (String contact : fileContent.split("#_#")) {
                        List<String> l = new ArrayList<>();
                        for (String field : contact.split(";")) l.add(field);
                        while (l.size() < MyPhone.HINTID.length) l.add("");
                        String content = contact.replaceFirst(";", " - ").replace(";", System.getProperty("line.separator"));
                        TinNhanDanhBaCuocGoi t = new TinNhanDanhBaCuocGoi(content, l);
                        list.get(1).add(t);
                    }
                    MyPhone.saveDataApp(thisFeatureFolder, list);
                    listDialog.get(0).dismiss();
                    hienThiDuLieu_GroupChon();
                } else Toast.makeText(QuanLyTinNhanActivity.this, R.string.AlertDialog_notdatafile_selectFileDialog, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void chonNhacChuong() {
        MyPhone.showRingtoneList(this);
    }

    private void xoaPhoneSim() {
        MyPhone.removeAllCallLogsPS(this);
        MyPhone.removeAllContactsPS(this);
        MyPhone.removeAllMessagesPS(this);
    }

    private void ghepVoiDuLieuPhoneVaSim() {
        List<List<TinNhanDanhBaCuocGoi>> lps = new ArrayList<>();
        MyPhone.loadDataFromPhoneAndSim(this, lps);
        ghepVoiDuLieu(lps);
    }

    private void ghepVoiFile() {
        List<List<TinNhanDanhBaCuocGoi>> lps = new ArrayList<>();
        final List<AlertDialog> list = new ArrayList<>();
        list.add(MyDialog.selectFileDialog(QuanLyTinNhanActivity.this, new InputData() {
            @Override public void inputData(String... s) {
                List<List<TinNhanDanhBaCuocGoi>> lps = new ArrayList<>();
                MyPhone.loadDataFromApp(s[1], lps);
                if (lps.size() > 0) {
                    ghepVoiDuLieu(lps);
                    list.get(0).dismiss();
                } else Toast.makeText(QuanLyTinNhanActivity.this, R.string.AlertDialog_notdatafile_selectFileDialog, Toast.LENGTH_SHORT).show();
            }
            @Override public void inputData(String s){} @Override public void inputData(List<String> s){} @Override public void inputData(String s, int color){}
        }));
    }

    private void ghepVoiDuLieu(List<List<TinNhanDanhBaCuocGoi>> lps) {
        List<List<TinNhanDanhBaCuocGoi>> la = new ArrayList<>();
        MyPhone.loadDataFromApp(thisFeatureFolder, la);
        //merge list contact, list message & list call log
        for (int h = 1; h < 4; h++) {
            for (int i = 0; i < lps.get(h).size(); i++) {
                boolean isExist = false;
                for (int j = 0; j < la.get(h).size(); j++) {
                    if (lps.get(h).get(i).getContent().equals(la.get(h).get(j).getContent())) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) la.get(h).add(lps.get(h).get(i));
            }
        }
//        la.set(0, lps.get(0)); // uncomment this and run merge with phone and sim to add new fixed group in Spinner
        MyPhone.saveDataApp(thisFeatureFolder, la);
    }

    private void xoaDia() {
        MyFileIO.clearFolderContent(thisFeatureFolder);
    }

    public void guiTinNhan(String phoneNo) {
        final List<List<TinNhanDanhBaCuocGoi>> l = new ArrayList<>();
        MyPhone.loadDataFromApp(thisFeatureFolder, l);
        AlertDialog ad = MyDialog.showSendMsgDialog(QuanLyTinNhanActivity.this, l.get(1), new InputData() {
            @Override public void inputData(String s) {} @Override public void inputData(String s, int color) {}
            @Override public void inputData(String... s) {}
            @Override public void inputData(List<String> s) {
                MyPhone.sendSMS(QuanLyTinNhanActivity.this, s.get(0), s.get(1).split(";"));
                String prefix = TinNhanDanhBaCuocGoi.geMessageTypeName(TinNhanDanhBaCuocGoi.SENT, QuanLyTinNhanActivity.this);
                Long time = System.currentTimeMillis();
                for (int i = 2; i < s.size(); i++) l.get(2).add(0, new TinNhanDanhBaCuocGoi(TinNhanDanhBaCuocGoi.SENT, prefix + " " + s.get(i) + " - "
                            + MyDateTime.getDateString(time, AppConstant.FULLTIMEFORMAT_WITHOUTNEWLINE) + "\n" + s.get(0), s.get(i), time));
                MyPhone.saveDataApp(thisFeatureFolder, l);
                hienThiDuLieu_GroupChon();
            }
        }, phoneNo);
    }

    public void goiDienThoai(final String phoneNo) {
        final List<List<TinNhanDanhBaCuocGoi>> l = new ArrayList<>();
        MyPhone.loadDataFromApp(thisFeatureFolder, l);
        AlertDialog ad = MyDialog.showCallDialog(QuanLyTinNhanActivity.this, l.get(1), null, phoneNo);
    }

    public void xoaNhieuTinNhan() {
        List<List<TinNhanDanhBaCuocGoi>> l = new ArrayList<>();
        MyPhone.loadDataFromApp(thisFeatureFolder, l);
        if (index < 3) {
            List<TinNhanDanhBaCuocGoi> list = listAdapter.get(1).getListContent();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isSelected()) {
                    list.remove(i);
                    i -= 1;
                }
            }
            l.set(index + 1, list);
        } else {
            List<TinNhanDanhBaCuocGoi> list = listAdapter.get(2).getListContent();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isSelected()) {
                    for (int j = 0; j < l.get(1).size(); j++) {
                        if (l.get(1).get(j).getContent().equals(list.get(i).getContent()))
                            for (int k = 0; k < l.get(1).get(j).getGroupNumber().size(); k++)
                                if (l.get(1).get(j).getGroupNumber().get(k) == index)
                                    l.get(1).get(j).getGroupNumber().remove(k);
                    }
                }
            }
        }
        MyPhone.saveDataApp(thisFeatureFolder, l);
        hienThiDuLieu_GroupChon();
    }

    private void timKiem() {
        MyDialog.showSearchDialog(QuanLyTinNhanActivity.this, R.string.AlertDialog_Title_TenFileTimKiem_QuanLyTinNhan, null, new InputData() {
            @Override public void inputData(String s) {
                search(s, R.string.waitDialog_search_karaoke_title);
                searchResult = true;
            }
            @Override public void inputData(String s, int color) {}
            @Override public void inputData(List<String> s) {}
            @Override public void inputData(String... s) {}
        });
    }

    private void search(String searchString, final int titleID) {
        final ProgressDialog pd = new ProgressDialog(QuanLyTinNhanActivity.this);
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == 1) {
                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pd.setIndeterminate(true); // chi so % khong thay doi
                    pd.setMessage(QuanLyTinNhanActivity.this.getResources().getString(titleID));
                    pd.setCancelable(false);
                    pd.show();
                } else if (msg.arg1 == 2) {
                    if (index < 3) MyDialog.scaleListView((ListView)findViewById(id[1]));
                    else for (int i = 2; i < 5; i++) MyDialog.scaleListView((ListView)findViewById(id[i]));
                    pd.dismiss();
                }
                return false;
            }
        });
        new Thread() {
            public void run() {
                Message msg = new Message();
                msg.arg1 = 1; // show progress dialog
                handler.sendMessage(msg);
            }}.start();
        finishSearching = false;
        new Thread() {
            public void run() {
                Message msg = new Message();
                msg.arg1 = 2; // close progress dialog
                while (!finishSearching) {};
                handler.sendMessage(msg);
            }}.start();
        if (index < 3) listAdapter.get(1).getFilter().filter(searchString);
        else for (int i = 2; i < 5; i++) listAdapter.get(i).getFilter().filter(searchString);
    }

    private void sapXepTheo(int sortBy) {
        TinNhanDanhBaCuocGoi.sortBy = sortBy;
        sapXep();
    }

    private void sapXepDao() {
        boolean r = TinNhanDanhBaCuocGoi.reverseSort;
        TinNhanDanhBaCuocGoi.reverseSort = !r;
        sapXep();
    }

    private void sapXep() {
        if (index < 3) {
            Collections.sort(listAdapter.get(1).getListContent());
            listAdapter.get(1).notifyDataSetChanged();
        } else {
            int i = (TinNhanDanhBaCuocGoi.sortBy == TinNhanDanhBaCuocGoi.SORTBYCONTACT) ? 3 : 2;
            for (; i < 5; i++) {
                Collections.sort(listAdapter.get(i).getListContent());
                listAdapter.get(i).notifyDataSetChanged();
            }
        }
    }

    private void xoaHetTinNhan() {
        List<List<TinNhanDanhBaCuocGoi>> list = new ArrayList<>();
        MyPhone.loadDataFromApp(thisFeatureFolder, list);
        if (index < 3) list.get(index + 1).clear();
        else {
            for (TinNhanDanhBaCuocGoi t : list.get(1)) {
                for (int i = 0; i < t.getGroupNumber().size(); i++)
                    if (t.getGroupNumber().get(i) == index)
                        t.getGroupNumber().remove(i--);
            }
        }
        MyPhone.saveDataApp(thisFeatureFolder, list);
        hienThiDuLieu_GroupChon();
    }

    public void luuVaNapLaiDuLieu() {
        Log.i("MyTag", "luuVaNapLaiDuLieu()");
        List<List<TinNhanDanhBaCuocGoi>> l = new ArrayList<>();
        //get current data on screen
        MyPhone.loadDataFromApp(thisFeatureFolder, l);
        l.set(0, listAdapter.get(0).getListContent());
        if (index < 3) l.set(index + 1, listAdapter.get(1).getListContent());
        else {
            //Remove contact in current group of loaded data
            List<String> phoneNumberOfGroup = new ArrayList<>();
            for (int i = 0; i < l.get(1).size(); i++)
                for (int j = 0; j < l.get(1).get(i).getGroupNumber().size(); j++)
                    if (l.get(1).get(i).getGroupNumber().get(j) == index) {
                        phoneNumberOfGroup.add(l.get(1).get(i).getPhoneNumber());
                        l.get(1).remove(i);
                        i = -1;
                    }
            //Remove message in current group of loaded data
            for (int i = 0; i < l.get(2).size(); i++) {
                boolean isInGroup = false;
                for (String pn : phoneNumberOfGroup)
                    if (l.get(2).get(i).getPhoneNumber().equals(pn)) {
                        isInGroup = true;
                        break;
                    }
                if (isInGroup) {
                    l.get(2).remove(i);
                    i = -1;
                }
            }
            //Remove call logs in current group of loaded data
            for (int i = 0; i < l.get(3).size(); i++) {
                boolean isInGroup = false;
                for (String pn : phoneNumberOfGroup)
                    if (l.get(3).get(i).getPhoneNumber().equals(pn)) {
                        isInGroup = true;
                        break;
                    }
                if (isInGroup) {
                    l.get(3).remove(i);
                    i = -1;
                }
            }

            for (int i = 1; i < 4; i++) l.get(i).addAll(listAdapter.get(i + 1).getListContent());
        }
        //save to disk
        MyPhone.saveDataApp(thisFeatureFolder, l);
        hienThiDuLieu_GroupChon();
    }

    public static final String LIMITERCONTACTINFO = "\n";
    private void taoContactMoi() {
        if (index == 0)
            MyDialog.showContactDialog(QuanLyTinNhanActivity.this, R.string.AlertDialog_title_quanlytinnhan_editcontact, null, new InputData() {
                @Override public void inputData(String s) {}
                @Override public void inputData(String s, int color) {}
                @Override public void inputData(String... s) {}
                @Override
                public void inputData(List<String> s) {
                    StringBuilder info = new StringBuilder();
                    s.set(1, MyStringFormater.standardizePhoneNumber(s.get(1)));
                    info.append(s.get(0) + " - " + s.get(1));
                    int gn = Integer.parseInt(s.remove(s.size() - 1));
                    List<Integer> groupNumber = new ArrayList<>();
                    if (gn != 0) groupNumber.add(gn);
                    for (int i = 2; i < s.size(); i++) if (s.get(i).trim().length() > 0) info.append("\n" + s.get(i));
                    TinNhanDanhBaCuocGoi t = new TinNhanDanhBaCuocGoi(info.toString(), s, groupNumber);
                    List<List<TinNhanDanhBaCuocGoi>> list = new ArrayList<>();
                    MyPhone.loadDataFromApp(thisFeatureFolder, list);
                    list.get(1).add(t);
                    MyPhone.saveDataApp(thisFeatureFolder, list);
                    hienThiDuLieu_GroupChon();
                }
            });
    }

    private void thietDatNhom() {
        if (index == 0) {
            final Spinner spinner_SelectGroup = new Spinner(QuanLyTinNhanActivity.this);
            List<TinNhanDanhBaCuocGoi> list = new ArrayList<>();
            for (int i = 3; i < listAdapter.get(0).getListContent().size(); i++) list.add(listAdapter.get(0).getListContent().get(i));
            AdapterTextViewMauNen adapter = new AdapterTextViewMauNen(this, R.layout.item_textviewmaunen, list, false);
            spinner_SelectGroup.setAdapter(adapter);
            MyDialog.showMyDiaLog(QuanLyTinNhanActivity.this, R.string.AlertDialog_thietdatnhom_quanlytinnhan,
                new View[]{spinner_SelectGroup}, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        List<TinNhanDanhBaCuocGoi> list = listAdapter.get(1).getListContent();
                        List<Integer> groupNumber = new ArrayList<Integer>();
                        groupNumber.add(spinner_SelectGroup.getSelectedItemPosition() + 3);
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).isSelected()) {
                                list.get(i).setGroupNumber(groupNumber);
                                list.get(i).setSelected(false);
                            }
                        }
                        List<List<TinNhanDanhBaCuocGoi>> l = new ArrayList<>();
                        MyPhone.loadDataFromApp(thisFeatureFolder, l);
                        l.set(1, list);
                        MyPhone.saveDataApp(thisFeatureFolder, l);
                        hienThiDuLieu_GroupChon();
                    }
                });
        }
    }

    private void xoaNhomHienTai() {
        if (index > GROUPIDSTRING.length - 1) {
            List<List<TinNhanDanhBaCuocGoi>> list = new ArrayList<>();
            MyPhone.loadDataFromApp(thisFeatureFolder, list);
            list.get(0).remove(index);
            for (TinNhanDanhBaCuocGoi t : list.get(1)) {
                for (int i = 0; i < t.getGroupNumber().size(); i++) if (t.getGroupNumber().get(i) == index) t.getGroupNumber().remove(i--);
            }
            MyPhone.saveDataApp(thisFeatureFolder, list);
            listAdapter.get(0).setListContent(list.get(0));
            ((Spinner)findViewById(id[0])).setAdapter(listAdapter.get(0));
            ((Spinner)findViewById(id[0])).setSelection(--index);
        }
    }

    private void taoNhomMoi() {
        MyDialog.showGroupNameAndColorDialog(QuanLyTinNhanActivity.this, R.string.AlertDialog_title_quanlytinnhan, new InputData() {
            @Override public void inputData(String s) {}
            @Override public void inputData(List<String> s) {}
            @Override public void inputData(String... s) {}
            @Override public void inputData(String s, int color) {
                List<List<TinNhanDanhBaCuocGoi>> list = new ArrayList<>();
                MyPhone.loadDataFromApp(thisFeatureFolder, list);
                list.get(0).add(new TinNhanDanhBaCuocGoi(s, color));
                MyPhone.saveDataApp(thisFeatureFolder, list);
                listAdapter.get(0).setListContent(list.get(0));
                ((Spinner)findViewById(id[0])).setAdapter(listAdapter.get(0));
                ((Spinner)findViewById(id[0])).setSelection(list.get(0).size() - 1);
            }
        });
    }

    public void setFinishSearching(boolean finishSearching) {
        this.finishSearching = finishSearching;
    }

    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    public int getIndex() {
        return index;
    }

    public List<AdapterTextViewMauNen> getListAdapter() {
        return listAdapter;
    }

    public List<TinNhanDanhBaCuocGoi> getListNhom_spinner() {
        return listAdapter.get(0).getListContent();
    }

    @Override
    public void onBackPressed() {
        Log.i("MyTag", "onBackPressed()");
        if (searchResult) {
            search("", R.string.waitDialog_unsearch_karaoke_title);
            searchResult = false;
        } else super.onBackPressed();
    }

    @Override
    protected void onResume() {
        Log.i("MyTag", "onResume()");
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter("broadCastMessage"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!Telephony.Sms.getDefaultSmsPackage(this).equals(getPackageName())) {
                Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, getPackageName());
                startActivity(intent);
            }
        }
    }

    private void nhanHeadlessSMS(Intent intent) {

    }

    private void nhanMMS(Intent intent) {

    }

    private void nhanSMS(Intent intent) {
        hienThiDuLieu_GroupChon();
    }

    @Override
    protected void onPause() {
        Log.i("MyTag", "onPause()");
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        finish();
    }

    @Override
    protected void onDestroy() {
        Log.i("MyTag", "onDestroy()");
        super.onDestroy();
    }

//    @Override
//    public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
//        return super.onCreateThumbnail(outBitmap, canvas);
//    }

    Bundle currentState = new Bundle();
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("MyTag", "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        currentState.putInt("index", index);
        outState.putBundle("currentState", currentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i("MyTag", "onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);
        index = savedInstanceState.getBundle("currentState").getInt("index", -1);
        index = (index == -1) ? 1 : index;
    }


    public String getThisFeatureFolder() {
        return thisFeatureFolder;
    }
}

//        Log.i("MyTag", "nhanSMS()");
//        String msg = intent.getStringExtra("SMS");
//        int type = intent.getIntExtra("SMSType", TinNhanDanhBaCuocGoi.UNKNOWN);
//        String phoneNumber = intent.getStringExtra("SMSFromTo");
//        if (msg != null) themMessage(new TinNhanDanhBaCuocGoi(type, msg, phoneNumber));

//    private void themMessage(TinNhanDanhBaCuocGoi msg) {
//        Log.i("MyTag", "themMessage()");
//        List<List<TinNhanDanhBaCuocGoi>> l = new ArrayList<>();
//        MyPhone.loadDataFromApp(thisFeatureFolder, l);
//        l.get(2).add(0, msg);
//        MyPhone.saveDataApp(thisFeatureFolder, l);
//        if (index == 1) {
//            ((Spinner) findViewById(id[0])).setSelection(0, true);
//            ((Spinner) findViewById(id[0])).setSelection(1, true);
////        } else if (index == 0 || index == 2) {
//
//        } else {
//            boolean reloadNeeded = false;
//            for (TinNhanDanhBaCuocGoi t : listAdapter.get(2).getListContent()) {
//                if (t.getPhoneNumber().equals(msg.getPhoneNumber())) {
//                    reloadNeeded = true;
//                    break;
//                }
//            }
//            if (reloadNeeded) ((Spinner)findViewById(id[0])).setSelection(index);
//        }
//    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        Log.i("MyTag", "onConfigurationChanged()");
//        super.onConfigurationChanged(newConfig);
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            onSaveInstanceState(currentState);
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            onSaveInstanceState(currentState);
//        }
//    }