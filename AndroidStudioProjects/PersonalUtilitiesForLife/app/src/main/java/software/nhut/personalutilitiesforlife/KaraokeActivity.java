package software.nhut.personalutilitiesforlife;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TabHost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import software.nhut.personalutilitiesforlife.adapter.MusicAdapter;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.Music;
import util.MyAssetsAndPreferences;
import util.MyFileIO;
import util.MySqlite;

public class KaraokeActivity extends AppCompatActivity {
    ListView lvDanhSachBaiHat, lvDanhSachYeuThich;
    MusicAdapter adapterMusicBaiHat, adapterMusicYeuThich, adapterMusic;
    List<Music> listBaiHat, listYeuThich, listHienTai;
    TabHost tabHost;
    boolean finishSearching = false;

    private static final String KARAOKELIST_DBNAME = "Arirang.sqlite";
    private static final String KARAOKELIST_DBNAMEWITHPATH = "dbsqlite" + AppConstant.FS + KARAOKELIST_DBNAME;
    private static final String KARAOKELIST_DBTABLENAME = "ArirangSongList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karaoke);

        addControls();
        addEvents();
    }

    private void addEvents() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equals(getResources().getString(R.string.TabHost_tabtitle_danhsachbaihat_karaoke))) {
                    xuLyHienThiDanhSachBaiHat();
                } else if (tabId.equals(getResources().getString(R.string.TabHost_tabtitle_danhsachyeuthich_karaoke))) {
                    xuLyHienThiDanhSachYeuThich();
                }
            }
        });
    }

    private void xuLyHienThiDanhSachYeuThich() {
        listYeuThich.clear();
        listHienTai = listYeuThich;
        adapterMusic = adapterMusicYeuThich;
        for (Music song : listBaiHat) {
            if(song.isYeuThich()) listYeuThich.add(song);
        }
        adapterMusicYeuThich.notifyDataSetChanged();
    }

    private void xuLyHienThiDanhSachBaiHat() {
        listHienTai = listBaiHat;
        adapterMusic = adapterMusicBaiHat;
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
        nhapDuLieu();
        listHienTai = listBaiHat;
        adapterMusic = adapterMusicBaiHat;

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabDanhSachBaiHat = tabHost.newTabSpec(getResources().getString(R.string.TabHost_tabtitle_danhsachbaihat_karaoke));
        tabDanhSachBaiHat.setIndicator("", getResources().getDrawable(R.drawable.listmusic));
        tabDanhSachBaiHat.setContent(R.id.danhsachbaihat);
        tabHost.addTab(tabDanhSachBaiHat);

        TabHost.TabSpec tabDanhSachYeuThich = tabHost.newTabSpec(getResources().getString(R.string.TabHost_tabtitle_danhsachyeuthich_karaoke));
        tabDanhSachYeuThich.setIndicator("", getResources().getDrawable(R.drawable.lovesong));
        tabDanhSachYeuThich.setContent(R.id.danhsachbaihatyeuthich);
        tabHost.addTab(tabDanhSachYeuThich);
    }

    private void nhapDuLieu() {
        MyFileIO.showConfirmBox(KaraokeActivity.this, KARAOKELIST_DBNAME, KARAOKELIST_DBNAMEWITHPATH, KARAOKELIST_DBTABLENAME, listBaiHat, adapterMusicBaiHat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_karaoke, menu);
        MenuItem mnuSearch=menu.findItem(R.id.search_menu_karaoke);
        android.widget.SearchView searchView= (android.widget.SearchView) mnuSearch.getActionView();
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                final ProgressDialog pd = new ProgressDialog(KaraokeActivity.this);
                final Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        if (msg.arg1 == 1) {
                            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            pd.setIndeterminate(true); // chi so % khong thay doi
                            pd.setMessage(KaraokeActivity.this.getResources().getString(R.string.waitDialog_search_karaoke_title));
                            pd.setCancelable(false);
                            pd.show(); //Log.i("My Tag", System.currentTimeMillis() + " : Show pd");

                        } else if (msg.arg1 == 2) {
                            pd.dismiss();
                            //Log.i("My Tag", System.currentTimeMillis() + " : Close pd");
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
                //Log.i("My Tag", System.currentTimeMillis() + " : set false finishsearching");
                finishSearching = false;
                new Thread() {
                    public void run() {
                        Message msg = new Message();
                        msg.arg1 = 2; // close progress dialog
                        while (!finishSearching) {};
                        handler.sendMessage(msg);
                    }}.start();

                adapterMusic.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() == 0) adapterMusic.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.OptionMenu_Karaoke_MoDanhSachBaiHat) {
            nhapDuLieu();
            return super.onOptionsItemSelected(item);
        }
        if (i == R.id.OptionMenu_Karaoke_SapXepTheoMa) {
            Music.SORTBY = Music.SORTBY_MA;
        } else if (i == R.id.OptionMenu_Karaoke_SapXepTheoTenBaiHat) {
            Music.SORTBY = Music.SORTBY_NAME;
        } else if (i == R.id.OptionMenu_Karaoke_SapXepTheoTenTacGia) {
            Music.SORTBY = Music.SORTBY_AUTHOR;
        }
        Collections.sort(listHienTai);
        adapterMusic.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }

    public void setFinishSearching(boolean finishSearching) {
        this.finishSearching = finishSearching;
    }
}
