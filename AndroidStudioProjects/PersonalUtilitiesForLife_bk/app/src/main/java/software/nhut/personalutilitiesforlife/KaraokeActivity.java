package software.nhut.personalutilitiesforlife;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import software.nhut.personalutilitiesforlife.adapter.MusicAdapter;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.Music;
import util.MyAssetsAndPreferences;
import util.MySqlite;

public class KaraokeActivity extends AppCompatActivity {

    ListView lvDanhSachBaiHat, lvDanhSachYeuThich;
    MusicAdapter adapterMusicBaiHat, adapterMusicYeuThich;
    List<Music> listBaiHat, listYeuThich;
    TabHost tabHost;
    TextView txtThongTinApp;

    private static final String KARAOKELIST_DBNAME = "Arirang.sqlite";
    private static final String KARAOKELIST_DBNAMEWITHPATH = "dbsqlite" + System.getProperty("file.separator") + "Arirang.sqlite";
    private static final String KARAOKELIST_DBTABLENAME = "ArirangSongList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equals("Danh sach bai hat")) {
                    xuLyHienThiDanhSachBaiHat();
                } else if (tabId.equals("Danh sach yeu thich")) {
                    xuLyHienThiDanhSachYeuThich();
                } else if (tabId.equals("Thong tin app")) {
                    xuLyHienThiThongTinApp();
                }
            }
        });
    }

    private void xuLyHienThiThongTinApp() {

    }

    private void xuLyHienThiDanhSachYeuThich() {
        listYeuThich.clear();
        for (Music song : listBaiHat) {
            if(song.isYeuThich()) listYeuThich.add(song);
        }
        adapterMusicYeuThich.notifyDataSetChanged();
    }

    private void xuLyHienThiDanhSachBaiHat() {
        List<Music> tempYeuThich = new ArrayList<Music>();
        tempYeuThich.addAll(listYeuThich);
        for (Music song : listBaiHat) {
            if(song.isYeuThich()) {
                song.setYeuThich(false);
                for (Music songYeuThich : tempYeuThich) {
                    if (song.getMaSo().equals(songYeuThich.getMaSo())) {
                        song.setYeuThich(true);
                        tempYeuThich.remove(songYeuThich);
                        break;
                    }
                }
            }
        }
        adapterMusicBaiHat.notifyDataSetChanged();
    }

    private void addControls() {
        lvDanhSachBaiHat = (ListView) findViewById(R.id.lvDanhSachBaiHat);
        lvDanhSachYeuThich = (ListView) findViewById(R.id.lvDanhSachYeuThich);
        listBaiHat = new ArrayList<Music>();
        nhapDuLieu();
        adapterMusicBaiHat = new MusicAdapter(
                KaraokeActivity.this,
                R.layout.item_karaoke,
                listBaiHat
        );
        listYeuThich = new ArrayList<Music>();
        adapterMusicYeuThich = new MusicAdapter(
                KaraokeActivity.this,
                R.layout.item_karaoke,
                listYeuThich,
                true
        );
        lvDanhSachBaiHat.setAdapter(adapterMusicBaiHat);
        lvDanhSachYeuThich.setAdapter(adapterMusicYeuThich);

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabDanhSachBaiHat = tabHost.newTabSpec("Danh sach bai hat");
        tabDanhSachBaiHat.setIndicator("", getResources().getDrawable(R.drawable.listmusic));
        tabDanhSachBaiHat.setContent(R.id.danhsachbaihat);
        tabHost.addTab(tabDanhSachBaiHat);

        TabHost.TabSpec tabDanhSachYeuThich = tabHost.newTabSpec("Danh sach yeu thich");
        tabDanhSachYeuThich.setIndicator("", getResources().getDrawable(R.drawable.lovesong));
        tabDanhSachYeuThich.setContent(R.id.danhsachbaihatyeuthich);
        tabHost.addTab(tabDanhSachYeuThich);

        TabHost.TabSpec tabThongTinApp = tabHost.newTabSpec("Thong tin app");
        tabThongTinApp.setIndicator("", getResources().getDrawable(R.drawable.about));
        tabThongTinApp.setContent(R.id.thongtinapp);
        tabHost.addTab(tabThongTinApp);

        txtThongTinApp = (TextView) findViewById(R.id.txtThongTinApp);
    }

    private void nhapDuLieu() {
//        listBaiHat.add(new Music("55555", "Không yêu đừng nói lời cay đắng", "Ngọt ngào", false));
//        listBaiHat.add(new Music("33333", "Lòng mẹ", "Ngọc Sơn", true));
//        listBaiHat.add(new Music("12345", "Riêng một góc trời", "Tuấn Ngọc", false));
//        listBaiHat.add(new Music("67890", "Ly cà phê Ban Mê", "Siu Black", false));
        boolean result = MyAssetsAndPreferences.copyAssetsToPhone(this, "Arirang.sqlite",
                getApplicationInfo().dataDir + AppConstant.FS + "databases",
                KARAOKELIST_DBNAMEWITHPATH);
        if (!result) {
            return;
        }
        final String orderBy = null;//"TENBH ASC";
        List<String> db = MySqlite.read(this, KARAOKELIST_DBNAME,
                KARAOKELIST_DBTABLENAME, null, null, null, null, null, orderBy);
        for (String song : db) {
            String []songInfo = song.split(" ");
            Music m = new Music(songInfo[0], songInfo[1] + "\n" + songInfo[2],
                    songInfo[3], (songInfo[5].equals("0")) ? false : true);
            listBaiHat.add(m);
        }
    }
}
