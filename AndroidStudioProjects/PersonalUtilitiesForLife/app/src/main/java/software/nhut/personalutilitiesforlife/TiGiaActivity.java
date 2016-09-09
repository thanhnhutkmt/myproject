package software.nhut.personalutilitiesforlife;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import software.nhut.personalutilitiesforlife.adapter.AdapterGiaCa;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.TinNhanDanhBaCuocGoi;
import util.InputData;
import util.MyDateTime;
import util.MyDialog;
import util.MyFileIO;
import util.MyPhone;
import util.Mydownloader;

public class TiGiaActivity extends AppCompatActivity {
    private Spinner sp_nganhang_tigia;
    private ListView lvGiaCa_tigia;
    private String arrayTiGia[];
    private AdapterGiaCa giaCaAdapter;
    private List<String[]> listCacheTiGia;
    private ScrollView downloadLayout;
//    private String thisFeatureFolder;
    private String savePath;

    public static final String LIMITER = ",_,";
    public static final int[] ARRAYNGANHANG = {R.string.title_Agribank_tigiaactivity,
        R.string.title_BIDV_tigiaactivity,
        R.string.title_VietTinBank_tigiaactivity,
        R.string.title_Vietcombank_tigiaactivity,
        R.string.title_Techcombank_tigiaactivity,
        R.string.title_DongABank_tigiaactivity,
        R.string.title_Sacombank_tigiaactivity,
        R.string.title_ANZbank_tigiaactivity,
        R.string.title_VIDbank_tigiaactivity,
        R.string.title_banggia_tigiaactivity};
    public static final HashMap<Integer, String> SOTHONGTIN_NGANHANG = new HashMap<Integer, String>(){{
        put(ARRAYNGANHANG[0], "http://www.agribank.com.vn/default.aspx" + LIMITER + "text/html; charset=utf-8");
        put(ARRAYNGANHANG[1], "http://bidv.com.vn/Ty-gia-ngoai-te.aspx" + LIMITER + "text/html; charset=utf-8");
        put(ARRAYNGANHANG[2], "https://www.vietinbank.vn/web/home/vn/ty-gia/" + LIMITER + "text/html; charset=UTF-8");
        put(ARRAYNGANHANG[3], "http://vietcombank.com.vn/ExchangeRates/" + LIMITER + "text/html; charset=utf-8");
        put(ARRAYNGANHANG[4], "https://www.techcombank.com.vn/cong-cu-tien-ich/ti-gia/ti-gia-hoi-doai" + LIMITER + "text/html; charset=utf-8");
        put(ARRAYNGANHANG[5], "http://www.dongabank.com.vn/exchange/export" + LIMITER + "text/html");
        put(ARRAYNGANHANG[6], "http://www.sacombank.com.vn/Pages/default.aspx" + LIMITER + "text/html; charset=utf-8");
        put(ARRAYNGANHANG[7], "https://secure.anz.com/IntInetBank/ANZvietnamIB/English/fxRates/fxrates.asp" + LIMITER + "text/html");
        put(ARRAYNGANHANG[8], "http://publicbank.com.vn/Rates.aspx?cat=2" + LIMITER + "text/html; charset=utf-8");
        put(R.string.title_nova_tigiaactivity, "http://nova.com.vn/banggia/NOVA%20dd-MM-yy%20EXCEL%20NEW.pdf" + LIMITER + "NOVA_dd-MM-yyyy.pdf");
        put(R.string.title_tnc_tigiaactivity, "http://www.tnc.com.vn/vnt_upload/File/Banggia/BAOGIA_dd_MM.pdf" + LIMITER + "TNC_dd-MM-yyyy.pdf");
        put(R.string.title_phongvu_banggia_tigiaactivity, "http://phongvu.vn/gallery/avatar_upload/ads/storage/nnnnn_dd-88.swf" + LIMITER + "PhongVu_dd-MM-yyyy.swf");
        put(R.string.title_sangtao_tigiaactivity, "http://www.stcom.vn/FlexPaperViewer.swf" + LIMITER + "SangTao_dd-MM-yyyy.swf");
        put(R.string.title_nangdong_tigiaactivity, "http://maytinhnangdong.com/files/banner/4/4_01471331154.pdf" + LIMITER + "NangDong_dd-MM-yyyy.pdf");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ti_gia);
        addControl();
        addEvent();
    }

    private void addEvent() {
        sp_nganhang_tigia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if (position < 9) {
                    if (lvGiaCa_tigia.getVisibility() == View.GONE) {
                        downloadLayout.setVisibility(View.GONE);
                        lvGiaCa_tigia.setVisibility(View.VISIBLE);
                    }
                    String arrayTiGiaTemp[] = listCacheTiGia.get(position);
                    if (arrayTiGiaTemp != null) {
                        for (int i = 0; i < arrayTiGiaTemp.length; i++)
                            arrayTiGia[i] = arrayTiGiaTemp[i];
                        giaCaAdapter.notifyDataSetChanged();
                    }
                } else {
                    downloadLayout.setVisibility(View.VISIBLE);
                    lvGiaCa_tigia.setVisibility(View.GONE);
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void addControl() {
//        thisFeatureFolder = getApplicationInfo().dataDir + AppConstant.FS + AppConstant.THUMUC_BAOGIA;
//        MyFileIO.makeFolder(thisFeatureFolder);
        try {
            savePath = Environment.getExternalStorageDirectory().getCanonicalPath() + AppConstant.FS + AppConstant.THUMUC_BAOGIA;
        } catch (IOException e) {
            e.printStackTrace();
            savePath = getApplicationInfo().dataDir + AppConstant.FS + AppConstant.THUMUC_BAOGIA;
        }
        MyFileIO.makeFolder(savePath);
        sp_nganhang_tigia = (Spinner) findViewById(R.id.sp_nganhang_tigia);
        String arrayTenNganHang[] = new String[ARRAYNGANHANG.length];
        for (int i = 0; i < ARRAYNGANHANG.length; i++) arrayTenNganHang[i] = getResources().getString(ARRAYNGANHANG[i]);
        ArrayAdapter adapterNganHang = new ArrayAdapter<>(
                TiGiaActivity.this,
                android.R.layout.simple_list_item_1,
                arrayTenNganHang
        );
        adapterNganHang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_nganhang_tigia.setAdapter(adapterNganHang);
        listCacheTiGia = new ArrayList<>();
        loadData();
        createDownloadLayout();

        lvGiaCa_tigia = (ListView) findViewById(R.id.lvGiaCa_tigia);
        int []hinh = {R.drawable.flag_of_the_united_states_s, R.drawable.flag_of_europe_s, R.drawable.flag_of_the_united_kingdom_s
                , R.drawable.flag_of_hong_kong_s, R.drawable.flag_of_australia_s, R.drawable.flag_of_canada_s
                , R.drawable.flag_of_japan_s, R.drawable.flag_of_new_zealand_s, R.drawable.flag_of_singapore_s
                , R.drawable.flag_of_switzerland_s, R.drawable.flag_of_thailand_s, R.drawable.gold};
        arrayTiGia = new String[]{"1234,,,,4567,,,,9101,,,,2345", "1234,,,,4567,,,,9101,,,,2345", "1234,,,,4567,,,,9101,,,,2345", "1234,,,,4567,,,,9101,,,,2345",
                "1234,,,,4567,,,,9101,,,,2345", "1234,,,,4567,,,,9101,,,,2345", "1234,,,,4567,,,,9101,,,,2345", "1234,,,,4567,,,,9101,,,,2345",
                "1234,,,,4567,,,,9101,,,,2345", "1234,,,,4567,,,,9101,,,,2345", "1234,,,,4567,,,,9101,,,,2345", "1234,,,,4567,,,,9101,,,,2345"};
        giaCaAdapter = new AdapterGiaCa(this, R.layout.item_lvtigia, arrayTiGia, hinh);
        lvGiaCa_tigia.setAdapter(giaCaAdapter);
    }

    private void createDownloadLayout() {
        downloadLayout = (ScrollView) findViewById(R.id.srollView_download_tigiaActivity);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        downloadLayout.addView(layout);
        final EditText txtPath = new EditText(this);
        txtPath.setText(savePath);
        txtPath.setInputType(InputType.TYPE_CLASS_TEXT);
        Button btnBrowse = new Button(this);
        btnBrowse.setText(R.string.Button_chonThuMuc_selectFolderDialog_tigiaactivity);
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                MyDialog.selectFolderDialog(TiGiaActivity.this, new InputData() {
                    @Override public void inputData(String s) {} @Override public void inputData(List<String> s) {} @Override public void inputData(String s, int color) {}
                    @Override public void inputData(String... s) {
                        txtPath.setText(s[0]);
                    }
                });
            }
        });
        LinearLayout browseLayout = new LinearLayout(this);
        browseLayout.setOrientation(LinearLayout.HORIZONTAL);
        browseLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        browseLayout.addView(btnBrowse);
        browseLayout.addView(txtPath);
        // show date layout
        final TextView txtDate = new TextView(this);
        txtDate.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDate.setText(MyDateTime.getDateString(System.currentTimeMillis(), AppConstant.DATEFORMAT));
        final Calendar calendar = Calendar.getInstance();
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener callBack1 = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        txtDate.setText(MyDateTime.getDateString(calendar.getTime().getTime(), AppConstant.DATEFORMAT));
                    }
                };
                DatePickerDialog dpd = new DatePickerDialog(
                        TiGiaActivity.this,
                        callBack1,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show();
            }
        });

        layout.addView(browseLayout);
        layout.addView(txtDate);
        layout.setGravity(Gravity.RIGHT);
        // add button and progress bar for each company
        List<Integer> listComputerStoreName = new ArrayList<>();
        listComputerStoreName.add(R.string.title_nova_tigiaactivity);
        listComputerStoreName.add(R.string.title_tnc_tigiaactivity);
        listComputerStoreName.add(R.string.title_phongvu_banggia_tigiaactivity);
        listComputerStoreName.add(R.string.title_sangtao_tigiaactivity);
        listComputerStoreName.add(R.string.title_nangdong_tigiaactivity);
        for (final int nameID : listComputerStoreName) {
            final Button btn = new Button(this);
            btn.setText(getResources().getString(nameID));
            btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            final ProgressBar pb = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            pb.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    savePath = txtPath.getText().toString().trim();
                    MyFileIO.makeFolder(savePath);
                    String info[] = SOTHONGTIN_NGANHANG.get(nameID).split(LIMITER);
                    Mydownloader.DownloadFileTask dft = new Mydownloader.DownloadFileTask(pb, addDateToLink(savePath + AppConstant.FS + info[1], calendar.getTime().getTime())[0], btn);
                    dft.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, addDateToLink(info[0], calendar.getTime().getTime()));
                }
            });
            layout.addView(btn);
            layout.addView(pb);
        }
    }

    private String[] addDateToLink(String downloadLink, long time) {
        if (downloadLink.contains("dd-MM-yy%20")) { // nova computer
            String temp = downloadLink.replace("dd-MM-yy", MyDateTime.getDateString(time, "dd-MM-yy"));
            return new String[] {temp, temp.replace("NEW.pdf", "NEW%20.pdf"), temp.replace("NEW.pdf", "NEW..pdf")};
        } else if (downloadLink.contains("dd_MM.")) { // tnc computer
            return new String[] {downloadLink.replace("dd_MM", MyDateTime.getDateString(time, "dd_MM"))};
        } else if (downloadLink.contains("dd-MM-yyyy.")) { // file name
            return new String[]{downloadLink.replace("dd-MM-yyyy", MyDateTime.getDateString(time, "dd-MM-yyyy"))};
        } else if (downloadLink.contains("nnnnn_dd-88")) { //phong vu
            String arrayDownloadLink[] = new String[7001];
            String day = MyDateTime.getDateString(time, "dd");
            for (int i = 12000; i <= 19000; i++) {
                arrayDownloadLink[i-12000] = downloadLink.replace("nnnnn_dd", Integer.toString(i) + "_" + day);
            }
            return arrayDownloadLink;
        } else return new String[] {downloadLink};
    }

    private void loadData() {
        for (int i = 0; i < 9; i++) listCacheTiGia.add(null);
        if (!MyPhone.isInternetOn(TiGiaActivity.this)) {
            Toast.makeText(TiGiaActivity.this, R.string.Toast_theodoiduongdi_bando_wifi3goff, Toast.LENGTH_LONG).show();
            return;
        }
        for (int i = 1; i < 9; i++) {
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    listCacheTiGia.set(index, Mydownloader.getExchangeRate(ARRAYNGANHANG[index], SOTHONGTIN_NGANHANG.get(ARRAYNGANHANG[index])));
                }
            }).start();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean oneIsNull = true;
                while (oneIsNull)
                    for (int i = 0; i < listCacheTiGia.size(); i++) oneIsNull &= (listCacheTiGia.get(i) == null);
                TiGiaActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(TiGiaActivity.this, R.string.Toast_loaddone_tigia, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            loadData();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
