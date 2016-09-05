package software.nhut.personalutilitiesforlife;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import software.nhut.personalutilitiesforlife.adapter.AdapterCuocHen;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.CuocHen;
import util.MyData;
import util.MyDateTime;
import util.MyFileIO;

public class QuanLyCuocHenActivity extends AppCompatActivity {
    ListView lvCuocHen;
    List<CuocHen> listCuocHen;
    AdapterCuocHen adapterCuocHen;
    int lastListViewItemSelected;
    View lastListViewItem;
    boolean editCuocHen;
    public String thisFeatureFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_cuoc_hen);

        addControls();
        addEvents();
        checkStatus();
    }

    private void checkStatus() {
        MyFileIO.makeFolder(thisFeatureFolder);
        if ((new File(thisFeatureFolder)).listFiles().length > 0)
            docTuFile(AppConstant.TEMPNAME);
    }

    private void addEvents() {

    }

    public void addControls() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        thisFeatureFolder = getApplicationInfo().dataDir
                + System.getProperty("file.separator")
                + AppConstant.THUMUC_QUANLY_CUOCHEN;
        lvCuocHen = (ListView) findViewById(R.id.lvCuocHen);
        listCuocHen = new ArrayList<CuocHen>();
//        testlvCuocHen();
        adapterCuocHen = new AdapterCuocHen(
            QuanLyCuocHenActivity.this,
            R.layout.itemlvcuochen,
            listCuocHen
        );
        lvCuocHen.setAdapter(adapterCuocHen);
    }

    public void setLastListViewItemSelected(int lastListViewItemSelected) {
        this.lastListViewItemSelected = lastListViewItemSelected;
    }

    public void setLastListViewItem(View lastListViewItem) {
        this.lastListViewItem = lastListViewItem;
    }

    private static final int CLEAR_LISTVIEW = 0;
    private static final int REMOVE = 1;
    private static final int ADD = 2;
    private static final int SORT = 3;
    private static final int EDIT = 4;
    private CuocHen updateListView(int operation, List<CuocHen> list, int position) {
        CuocHen ch = null;
        switch(operation) {
            case CLEAR_LISTVIEW:
                listCuocHen.clear();
                Toast.makeText(QuanLyCuocHenActivity.this, R.string.Toast_XoaHet, Toast.LENGTH_LONG).show();
                break;
            case REMOVE:
                ch = listCuocHen.remove(position);
                if (!editCuocHen) Toast.makeText(QuanLyCuocHenActivity.this, R.string.Toast_Xoa, Toast.LENGTH_LONG).show();
                break;
            case EDIT:
                CuocHen c = listCuocHen.get(position);
                if (MyDateTime.setTimeToZero(c.getTime()) < MyDateTime.setTimeToZero(new Date(System.currentTimeMillis()))) {
                    Toast.makeText(QuanLyCuocHenActivity.this, R.string.Toast_TatMoQuaNgay, Toast.LENGTH_LONG).show();
                    return c;
                }
                boolean tat = c.isTat();
                c.setTat(!tat);
                adapterCuocHen.setMyAnimation(lastListViewItem, c);
                Toast.makeText(QuanLyCuocHenActivity.this, (c.isTat()) ? R.string.Toast_TatMo_Tat : R.string.Toast_TatMo_Mo, Toast.LENGTH_LONG).show();
                Collections.sort(listCuocHen);
                return c;
            case ADD:
                if (list != null && list.size() > 0) {
                    MyData.addAllWithoutDuplication(listCuocHen, list, false);
                    Toast.makeText(QuanLyCuocHenActivity.this, R.string.Toast_Them, Toast.LENGTH_LONG).show();
                } else
                    return ch;
                Collections.sort(listCuocHen);
                break;
            default:
                break;
        }
        adapterCuocHen.notifyDataSetChanged();
        lvCuocHen = null;
        LinearLayout ll = (LinearLayout) findViewById(R.id.linearlayout_quanlycuochen);
        ll.removeAllViews();
        ListView lv = new ListView(this);
        lv.setAdapter(adapterCuocHen);
        lvCuocHen = lv;
        ll.addView(lvCuocHen);
        return ch;
    }

    private void testlvCuocHen() {
        String language = getResources().getConfiguration().locale.getDisplayLanguage();
        if (language.equals("English")) {
            listCuocHen.add(new CuocHen());
            listCuocHen.add(new CuocHen("Cuộc hẹn 1",
                    "skldjflskdjflskdjflksdjfksdjflsdjflsd" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "djflskdjflskdjffksldjfkldsjflsdjkfsdj" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "djflskdjflskdjffksldjfkldsjflsdjkfsdj" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "djflskdjflskdjffksldjfkldsjflsdjkfsdj" +
                            "lfkjsdklfjsdlkasdasdasdasfdsafdsfsdfs",
                    "09:00 Sat 26 Jun 2016", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), false));
            listCuocHen.add(new CuocHen("Cuộc hẹn 2",
                    "skldjflskdjflskdjflksdjfksdjflsdjflsd" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "djflskdjflskdjffksldjfkldsjflsdjkfsdj" +
                            "lfkjsdklfjsdlkasdasdasdasfdsafdsfsdfs",
                    "10:00 Mon 13 Jun 2017", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), false));
            listCuocHen.add(new CuocHen("Cuộc hẹn 3",
                    "skldjflskdjflskdjflksdjfksdjflsdjflsd" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "djflskdjflskdjffksldjfkldsjflsdjkfsdj" +
                            "lfkjsdklfjsdlkasdasdasdasfdsafdsfsdfs",
                    "09:30 Mon 13 Jun 2017", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), true));
            listCuocHen.add(new CuocHen("Cuộc hẹn 4",
                    "skldjflskdjflskdjflksdjfksdjflsdjflsd" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "djflskdjflskdjffksldjfkldsjflsdjkfsdj" +
                            "lfkjsdklfjsdlkasdasdasdasfdsafdsfsdfs",
                    "11:00 Mon " +
                            "13 Jun 2017", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), false));
            listCuocHen.add(new CuocHen("Cuộc hẹn 5",
                    "skldjflskdjflskdjflksdjfksdjflsdjflsd" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "djflskdjflskdjffksldjfkldsjflsdjkfsdj" +
                            "lfkjsdklfjsdlkasdasdasdasfdsafdsfsdfs",
                    "12:00 Mon " +
                            "13 Jun 2017", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), false));
        } else {
            listCuocHen.add(new CuocHen());
            listCuocHen.add(new CuocHen("Cuộc hẹn 1",
                    "skldjflskdjflskdjflksdjfksdjflsdjflsd" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "djflskdjflskdjffksldjfkldsjflsdjkfsdj" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "djflskdjflskdjffksldjfkldsjflsdjkfsdj" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "djflskdjflskdjffksldjfkldsjflsdjkfsdj" +
                            "lfkjsdklfjsdlkasdasdasdasfdsafdsfsdfs",
                    "09:00 Th 7 25 thg 6 2017", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), false));
            listCuocHen.add(new CuocHen("Cuộc hẹn 2",
                    "skldjflskdjflskdjflksdjfksdjflsdjflsd" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "djflskdjflskdjffksldjfkldsjflsdjkfsdj" +
                            "lfkjsdklfjsdlkasdasdasdasfdsafdsfsdfs",
                    "10:00 Th 2 13 thg 6 2017", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), false));
            listCuocHen.add(new CuocHen());
            listCuocHen.add(new CuocHen("Cuộc hẹn 3",
                    "skldjflskdjflskdjflksdjfksdjflsdjflsd" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "djflskdjflskdjffksldjfkldsjflsdjkfsdj" +
                            "lfkjsdklfjsdlkasdasdasdasfdsafdsfsdfs",
                    "09:30 Th 2 13 thg 6 2017", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), true));
            listCuocHen.add(new CuocHen("Cuộc hẹn 4",
                    "skldjflskdjflskdjflksdjfksdjflsdjflsd" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "djflskdjflskdjffksldjfkldsjflsdjkfsdj" +
                            "lfkjsdklfjsdlkasdasdasdasfdsafdsfsdfs",
                    "11:00 Th 2 " +
                            "13 thg 6 2017", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), false));
            listCuocHen.add(new CuocHen("Cuộc hẹn 5",
                    "skldjflskdjflskdjflksdjfksdjflsdjflsd" +
                            "jflsdjflsdkjflskjflskdfjlskjflsdjflsk" +
                            "djflskdjflskdjffksldjfkldsjflsdjkfsdj" +
                            "lfkjsdklfjsdlkasdasdasdasfdsafdsfsdfs",
                    "12:00 Th 2 " +
                            "13 thg 6 2017", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), false));
        }

        Collections.sort(listCuocHen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_quanlycuochen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.mnuThem) {
            themCuocHen();
        } else if (i == R.id.mnuXoaHet) {
            xoaHetCuocHen();
        } else if (i == R.id.mnuDocTuFile) {
            createAlertDialog(R.string.AlertDialog_Title_ChonFileDoc_QuanLyCuocHen, MyFileIO.getListDataFileName(thisFeatureFolder));
        } else if (i == R.id.mnuGhiRaFile) {
            createAlertDialog(R.string.AlertDialog_Title_TenFileLuu_QuanLyCuocHen, null);
        }
        return super.onOptionsItemSelected(item);
    }

    private void ghiRaFile(String fileName) {
        readWriteTask writeTask = new readWriteTask(this, listCuocHen, readWriteTask.WRITE, fileName);
        writeTask.execute();
    }

    private void docTuFile(String fileName) {
        readWriteTask readTask = new readWriteTask(this, null, readWriteTask.READ, fileName);
        readTask.execute();
    }

    private void xoaHetCuocHen() {
        updateListView(this.CLEAR_LISTVIEW, listCuocHen, -1);
    }

    private void themCuocHen() {
        Intent i = new Intent(QuanLyCuocHenActivity.this, ChiTietCuocHenActivity.class);
        startActivityForResult(i, AppConstant.QUANLYCUOCHEN_NEW_REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == AppConstant.QUANLYCUOCHEN_NEW_REQUESTCODE
                || requestCode == AppConstant.QUANLYCUOCHEN_EDIT_REQUESTCODE)
                && resultCode == AppConstant.CHITIETCUOCHEN_RESULTCODE) {
            if (requestCode == AppConstant.QUANLYCUOCHEN_EDIT_REQUESTCODE) editCuocHen = false;
            CuocHen c = (CuocHen) data.getSerializableExtra(AppConstant.CHITIETCUOCHEN_CHUOIGUI_CUOCHEN);
            if (c != null) {
                List<CuocHen> list = new ArrayList<CuocHen>();
                list.add(c);
                updateListView(this.ADD, list, -1);
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucontext_quanlycuochen, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.mnuXoa) {
            updateListView(this.REMOVE, listCuocHen, lastListViewItemSelected);
        } else if (i == R.id.mnuChinhSua) {
            chinhSuaCuocHen();
        } else if (i == R.id.mnuTatMo) {
            tatMoCuocHen();
        }
        return super.onContextItemSelected(item);
    }

    private void tatMoCuocHen() {
        updateListView(this.EDIT, listCuocHen, lastListViewItemSelected);
    }

    private void chinhSuaCuocHen() {
        moCuocHen();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!editCuocHen) {
            if (listCuocHen.size() > 0) ghiRaFile(AppConstant.TEMPNAME);
        }
    }

    class readWriteTask extends AsyncTask<Integer, Integer, Void> {
        private ProgressDialog pd;
        private List<?> list;
        private boolean writeTask;
        private String fileName;
        public static final boolean WRITE = true;
        public static final boolean READ = false;

        readWriteTask(Activity a, List<?> list, boolean writeTask, String fileName) {
            pd = new ProgressDialog(a);
            pd.setMessage((writeTask) ?
                    getResources().getString(R.string.ProgressDialog_GhiFile) :
                    getResources().getString(R.string.ProgressDialog_DocFile));
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setProgress(0);
            this.list = list;
            this.writeTask = writeTask;
            this.fileName = fileName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setProgress(0);
            pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.setProgress(100);
            pd.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            if (writeTask) {
                pd.setProgress(values[0]);
                if (values[1] == 1)
                    Toast.makeText(QuanLyCuocHenActivity.this,
                            R.string.Toast_GhiRaFile_BiLoi, Toast.LENGTH_LONG).show();
            } else {
                if (this.list != null) updateListView(QuanLyCuocHenActivity.ADD, (List<CuocHen>) this.list, -1);
                pd.setProgress(values[0]);
            }
        }

        @Override
        protected Void doInBackground(Integer... params) {
            if (writeTask) {
                boolean result = MyFileIO.saveData(this.list,
                        getApplicationInfo().dataDir + System.getProperty("file.separator")
                                + AppConstant.THUMUC_QUANLY_CUOCHEN, fileName);
                publishProgress(100, (result) ? 0 : 1);
                SystemClock.sleep(200);
            } else {
                String path = getApplicationInfo().dataDir + System.getProperty("file.separator")
                        + AppConstant.THUMUC_QUANLY_CUOCHEN;
                this.list = MyFileIO.loadData(path, fileName);
                publishProgress(100);
                SystemClock.sleep(200);
            }
            return null;
        }
    }

    public void moCuocHen() {
        Intent i = new Intent(QuanLyCuocHenActivity.this, ChiTietCuocHenActivity.class);
        // Chay onPause() o day vi sau khi set editCuocHen = true thi
        // onPause() se khong thuc thi cau lenh if
        // cuocHen se duoc chinh sua va cac cuoc hen khac (neu co)
        // se duoc luu xuong dia truoc khi chinh sua phong khi bi loi
        ghiRaFile(AppConstant.TEMPNAME);
        editCuocHen = true;
        i.putExtra(AppConstant.QUANLYCUOCHEN_CHUOIGUI_CUOCHEN,
                updateListView(this.REMOVE, listCuocHen, lastListViewItemSelected));
        startActivityForResult(i, AppConstant.QUANLYCUOCHEN_EDIT_REQUESTCODE);
    }

    private void createAlertDialog(int idTitle, final String[] list) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(idTitle);
        if (list != null) {
            // Set up the listview to show listContact
            ListView lvTinNhan = new ListView(QuanLyCuocHenActivity.this);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
            lvTinNhan.setAdapter(adapter);
            lvTinNhan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    docTuFile(list[position]);
                    return false;
                }
            });
            builder.setView(lvTinNhan);
        } else {
            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);// | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton(R.string.txtInput_NutLuu_ChiTietCuocHen, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ghiRaFile(input.getText().toString());
                }
            });
        }
        builder.setNegativeButton(R.string.txtInput_NutHuy_ChiTietCuocHen, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}