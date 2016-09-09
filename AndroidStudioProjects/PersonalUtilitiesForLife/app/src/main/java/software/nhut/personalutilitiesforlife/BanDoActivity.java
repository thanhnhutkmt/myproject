package software.nhut.personalutilitiesforlife;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.ViTri;
import util.MyClipboard;
import util.MyData;
import util.MyFileIO;
import util.MyGoogleMap;
import util.MyMedia;
import util.MyPhone;

public class BanDoActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    private GoogleMap mMap;
    private TextView txtSearch_TabTimDiaDiem, txtTemp, txtDiaChiHienTai_theodoi_duongdi;
    private EditText txtAllowingDistance;
    private ImageButton btnSearch_TabTimDiaDiem;
    private SeekBar sbrZoom_TabTimDiaDiem;
    private LatLng currentPosition, position;
    private String info;
    private LocationManager locationManager;
    private String psType = LocationManager.GPS_PROVIDER;
    private List<ViTri> listViTri, listCacDiemTimDuongDi;
    private List<String> listChangDuong;
    private String thisFeatureFolder;
    private ListView lvChangDuong;
    private ArrayAdapter adapterChangDuong;
    private TabHost tabHost;
    private String currentTabId;
    private String travelMode;
    private List<LatLng> listCotMoc, listToaDoVeDuongDi;
    private boolean lockBackButton;
    private BroadcastReceiver gpsWifi3GReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!MyPhone.isGPSOn(context))
                Toast.makeText(BanDoActivity.this, R.string.Toast_theodoiduongdi_bando_gpsoff, Toast.LENGTH_SHORT).show();
            if (!MyPhone.isInternetOn(context))
                Toast.makeText(BanDoActivity.this, R.string.Toast_theodoiduongdi_bando_wifi3goff, Toast.LENGTH_SHORT).show();
        }
    };
    private int allowingDistance = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_do);

        addControls();
        addEvents();
        checkStatus();
    }

    private void checkStatus() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MyFileIO.makeFolder(thisFeatureFolder);
        if ((new File(thisFeatureFolder)).listFiles().length > 0)
            listViTri.addAll((List<ViTri>) MyFileIO.loadData(thisFeatureFolder,
                    AppConstant.DANHSACH_VITRI_BANDO));
        sbrZoom_TabTimDiaDiem.setProgress(0);
    }

    private void addEvents() {
        txtSearch_TabTimDiaDiem.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)
                    searchPlaceAndBringKeyBoardDown();
                return false;
            }
        });
        btnSearch_TabTimDiaDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPlaceAndBringKeyBoardDown();
            }
        });
        sbrZoom_TabTimDiaDiem.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MyGoogleMap.zoomCameraTo(mMap, null, progress + 2);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                currentTabId = tabId;
                invalidateOptionsMenu();
                int mapId = 0;
                if (currentTabId.equals(getResources().getString(R.string.TabHost_tabtitle_timdiadiem_bando))) {
                    mapId = R.id.mapCoiBanDo;
                } else if (currentTabId.equals(getResources().getString(R.string.TabHost_tabtitle_timduongdi_bando))) {
                    mapId = R.id.mapTimDuongDiBanDo;
                } else if (currentTabId.equals(getResources().getString(R.string.TabHost_tabtitle_theodoiduongdi_bando))) {
                    mapId = R.id.mapCoiBanDo_theodoiduongdi;
                }
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(mapId);
                mapFragment.getMapAsync(BanDoActivity.this);
            }
        });

        txtAllowingDistance.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    try {
                        allowingDistance = Integer.parseInt(txtAllowingDistance.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }

    private void searchPlaceAndBringKeyBoardDown() {
        MyGoogleMap.searchPlaceByName(BanDoActivity.this, txtSearch_TabTimDiaDiem.getText().toString(), mMap);
        MyPhone.bringKeyboardDown(BanDoActivity.this, txtSearch_TabTimDiaDiem);
    }

    private void addControls() {
        travelMode = MyGoogleMap.FindWayTask.DRIVING;
        currentTabId = getResources().getString(R.string.TabHost_tabtitle_timdiadiem_bando);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapCoiBanDo);
        mapFragment.getMapAsync(BanDoActivity.this);
        thisFeatureFolder = getApplicationInfo().dataDir + AppConstant.FS + AppConstant.THUMUC_BANDO;
        txtSearch_TabTimDiaDiem = (TextView) findViewById(R.id.txtSearch_TabTimDiaDiem);
        txtAllowingDistance = (EditText) findViewById(R.id.txtAllowingDistance);
        btnSearch_TabTimDiaDiem = (ImageButton) findViewById(R.id.btnSearch_TabTimDiaDiem);
        sbrZoom_TabTimDiaDiem = (SeekBar) findViewById(R.id.sbrZoom_TabTimDiaDiem);
        sbrZoom_TabTimDiaDiem.setProgress(AppConstant.ZOOM_CITYLEVEL - 2);
        txtTemp = (TextView) findViewById(R.id.txttemp);
        txtDiaChiHienTai_theodoi_duongdi = (TextView) findViewById(R.id.txtDiaChiHienTai_theodoi_duongdi);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listViTri = new ArrayList<ViTri>();

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabTimDiaDiem = tabHost.newTabSpec(getResources().getString(R.string.TabHost_tabtitle_timdiadiem_bando));
        tabTimDiaDiem.setIndicator("", getResources().getDrawable(R.drawable.marker));
        tabTimDiaDiem.setContent(R.id.tabCoiBanDo);
        tabHost.addTab(tabTimDiaDiem);

        TabHost.TabSpec tabTimDuongDi = tabHost.newTabSpec(getResources().getString(R.string.TabHost_tabtitle_timduongdi_bando));
        tabTimDuongDi.setIndicator("", getResources().getDrawable(R.drawable.road));
        tabTimDuongDi.setContent(R.id.tabTimDuongDi);
        tabHost.addTab(tabTimDuongDi);
        lvChangDuong = (ListView) findViewById(R.id.lvChangDuong_timduongdi);
        listChangDuong = new ArrayList<String>();
        adapterChangDuong = new ArrayAdapter<>(BanDoActivity.this, android.R.layout.simple_list_item_1, listChangDuong);
        lvChangDuong.setAdapter(adapterChangDuong);

        TabHost.TabSpec tabTheoDoiDuongDi = tabHost.newTabSpec(getResources().getString(R.string.TabHost_tabtitle_theodoiduongdi_bando));
        tabTheoDoiDuongDi.setIndicator("", getResources().getDrawable(R.drawable.onroad));
        tabTheoDoiDuongDi.setContent(R.id.tabTheoDoiDiDuong);
        tabHost.addTab(tabTheoDoiDuongDi);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    private AlertDialog ad;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (currentTabId.equals((getResources().getString(R.string.TabHost_tabtitle_timdiadiem_bando)))) {
            khoiTaoBanDo_TabTimDiaDiem(googleMap);
        } else if (currentTabId.equals((getResources().getString(R.string.TabHost_tabtitle_timduongdi_bando)))) {
            khoiTaoBanDo_TabTimDuongDi(googleMap);
        } else if (currentTabId.equals((getResources().getString(R.string.TabHost_tabtitle_theodoiduongdi_bando)))) {
            khoiTaoBanDo_TheoDoiDuongDi(googleMap);
        }
    }

    private void khoiTaoBanDo_TabTimDiaDiem(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in HCM city and move the camera
        mMap.addMarker(new MarkerOptions().position(AppConstant.HCMLOCATION).title(getResources().getString(R.string.Location_title_HCM)));
        MyGoogleMap.zoomCameraTo(mMap, AppConstant.HCMLOCATION, AppConstant.ZOOM_CITYLEVEL);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                position = latLng;
                info = "";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        info = MyGoogleMap.getAddressFromLocation(position.latitude, position.longitude, BanDoActivity.this);
                    }
                }).start();
                txtTemp.postDelayed(new Runnable() {
                    @Override public void run() {
                        if (info.length() > 0) txtTemp.setText(info);
                        else txtTemp.setText(R.string.txtTemp_timdiadiem_bando_lowconnection);
                        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("simple text", info));
                    }
                }, 500);
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                final LatLng pos = latLng;
                final View view = BanDoActivity.this.getLayoutInflater().inflate(R.layout.alertdialog_vitri, null);
                final EditText txtViDo_alertdialog, txtKinhDo_alertdialog, txtTen_alertdialog, txtGhiChu_alertdialog;
                txtViDo_alertdialog = (EditText) view.findViewById(R.id.txtViDo_alertdialog);
                txtKinhDo_alertdialog = (EditText) view.findViewById(R.id.txtKinhDo_alertdialog);
                txtTen_alertdialog = (EditText) view.findViewById(R.id.txtTen_alertdialog);
                txtGhiChu_alertdialog = (EditText) view.findViewById(R.id.txtGhiChu_alertdialog);
                txtViDo_alertdialog.setText(Double.toString(pos.latitude));
                txtKinhDo_alertdialog.setText(Double.toString(pos.longitude));
                txtGhiChu_alertdialog.postDelayed(new Runnable() {
                    @Override public void run() {
                        txtGhiChu_alertdialog.setText(MyGoogleMap.getAddressFromLocation(pos.latitude, pos.longitude, BanDoActivity.this));
                    }
                }, 1000);
                AlertDialog.Builder builder = new AlertDialog.Builder(BanDoActivity.this);
                builder.setTitle(R.string.AlertDialog_title_vitri)
                    .setView(view)
                    .setPositiveButton(R.string.AlertDialog_button_save_vitri, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ViTri vt = new ViTri(txtViDo_alertdialog.getText().toString(), txtKinhDo_alertdialog.getText().toString(),
                                    txtTen_alertdialog.getText().toString(), txtGhiChu_alertdialog.getText().toString());
                            if (listViTri != null) {
                                listViTri.add(vt);
                                boolean result = MyFileIO.saveData(listViTri, thisFeatureFolder, AppConstant.DANHSACH_VITRI_BANDO);
                                if (result) {
                                    Toast.makeText(BanDoActivity.this, R.string.AlertDialog_Toast_addok_vitri, Toast.LENGTH_SHORT).show();
                                    Marker m = mMap.addMarker(new MarkerOptions().position(pos).title(txtTen_alertdialog.getText()
                                            .toString()).snippet(txtGhiChu_alertdialog.getText().toString()));
                                    MyGoogleMap.zoomCameraTo(mMap, pos, -1);
                                }
                            }
                        }
                    })
                    .setNegativeButton(R.string.AlertDialog_button_cancel_vitri, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}
                    })
                    .show();
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final Marker m = marker;
                TextView txtDelete = new TextView(BanDoActivity.this);
                txtDelete.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                txtDelete.setText(R.string.AlertDialog_textview_delete_vitri);
                txtDelete.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                txtDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m.remove();
                        timViTriTuMarker(m, true);
                        ad.dismiss();
                    }
                });
                TextView txtShowInfoWindow = new TextView(BanDoActivity.this);
                txtShowInfoWindow.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                txtShowInfoWindow.setText(R.string.AlertDialog_textview_showinfowindow_vitri);
                txtShowInfoWindow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                txtShowInfoWindow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m.showInfoWindow();
                        ad.dismiss();
                    }
                });
                TextView lineBetween1 = new TextView(BanDoActivity.this);
                lineBetween1.setHeight(1);
                lineBetween1.setBackgroundColor(Color.BLACK);
                lineBetween1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                LinearLayout ll = new LinearLayout(BanDoActivity.this);
                ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setGravity(Gravity.CENTER);
                ll.addView(txtDelete);
                ll.addView(lineBetween1);
                ll.addView(txtShowInfoWindow);
                ad = new AlertDialog.Builder(BanDoActivity.this).setView(ll).setCancelable(true).show();
                return false;
            }
        });
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(BanDoActivity.this, R.string.btnMyLocationGM_UpdatingPosition, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                sbrZoom_TabTimDiaDiem.setProgress((int) (cameraPosition.zoom - 2));
            }
        });
    }

    private void khoiTaoBanDo_TabTimDuongDi(GoogleMap googleMap) {
        mMap = googleMap;
        MyGoogleMap.zoomCameraTo(mMap, AppConstant.HCMLOCATION, AppConstant.ZOOM_STREETLEVEL);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(BanDoActivity.this, R.string.btnMyLocationGM_UpdatingPosition, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void khoiTaoBanDo_TheoDoiDuongDi(GoogleMap googleMap) {
        mMap = googleMap;
        MyGoogleMap.zoomCameraTo(mMap, null, AppConstant.ZOOM_STREETLEVEL);
        mMap.setMyLocationEnabled(true);
        locationManager.requestLocationUpdates(psType, 500, 0.5f, BanDoActivity.this);
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(BanDoActivity.this, R.string.btnMyLocationGM_UpdatingPosition, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        if (currentPosition != null) MyGoogleMap.showDirectionArrow(mMap, currentPosition, location);
        currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        if (currentTabId.equals((getResources().getString(R.string.TabHost_tabtitle_timdiadiem_bando)))) {
            MyGoogleMap.zoomCameraTo(mMap, currentPosition, sbrZoom_TabTimDiaDiem.getProgress() + 2);
            String info = MyGoogleMap.getAddressFromLocation(currentPosition.latitude, currentPosition.longitude, BanDoActivity.this);
            txtTemp.setText(info);
            MyClipboard.sendTextToClipboard(BanDoActivity.this, info);
        } else if (currentTabId.equals((getResources().getString(R.string.TabHost_tabtitle_timduongdi_bando)))) {

        } else if (currentTabId.equals((getResources().getString(R.string.TabHost_tabtitle_theodoiduongdi_bando)))) {
            if (lockBackButton) {
                MyGoogleMap.zoomCameraTo(mMap, currentPosition, -1);
                String info = MyGoogleMap.getAddressFromLocation(currentPosition.latitude, currentPosition.longitude, BanDoActivity.this);
                txtDiaChiHienTai_theodoi_duongdi.setText(info);
                MyClipboard.sendTextToClipboard(BanDoActivity.this, info);
                if (MyGoogleMap.testGoSoFarAway(listToaDoVeDuongDi, currentPosition, allowingDistance)) {
                    Toast.makeText(BanDoActivity.this, R.string.Toast_theodoiduongdi_bando_gosofaraway, Toast.LENGTH_SHORT).show();
                    Vibrator v = (Vibrator) BanDoActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);// Vibrate for 500 milliseconds
                    MyMedia.playSound(BanDoActivity.this, 5);
                }
            }
        }
    }

    @Override public void onStatusChanged(String provider, int status, Bundle extras) {Log.i("MyTag", "onStatusChanged");}
    @Override public void onProviderEnabled(String provider) {Log.i("MyTag", "onProviderEnabled");}
    @Override public void onProviderDisabled(String provider) {Log.i("MyTag", "onProviderDisabled");}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (currentTabId.equals(getResources().getString(R.string.TabHost_tabtitle_timdiadiem_bando))) {
            inflater.inflate(R.menu.menu_bando, menu);
        } else if (currentTabId.equals(getResources().getString(R.string.TabHost_tabtitle_timduongdi_bando))) {
            inflater.inflate(R.menu.menu_timduongdi_bando, menu);
        } else if (currentTabId.equals(getResources().getString(R.string.TabHost_tabtitle_theodoiduongdi_bando))) {
            inflater.inflate(R.menu.menu_theodoiduongdi_bando, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    /*
            luong chinh(UI) move zoom camera
            oncamerachange :  chup hinh

        lap lai {
            move zoom camera    UI
            cho move zoom xong  UI
            chup hinh           UI/background
        }
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.mnuBanDo_Normal) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } else if (i == R.id.mnuBanDo_Satellite) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else if (i == R.id.mnuBanDo_Terrain) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        } else if (i == R.id.mnuBanDo_Hybrid) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        } else if (i == R.id.mnuBanDo_CheDoGPS) {
            psType = LocationManager.GPS_PROVIDER;
        } else if (i == R.id.mnuBanDo_CheDoWifi3G) {
            psType = LocationManager.NETWORK_PROVIDER;
        } else if (i == R.id.mnuBanDo_XoaHetMarker) {
            mMap.clear();
        } else if (i == R.id.mnuBanDo_LuuMarker) {
            xuLyLuuDanhDau();
        } else if (i == R.id.mnuBanDo_DocMarker) {
            listViTri.clear();
            MyData.addAllWithoutDuplication(listViTri, (List<ViTri>)MyFileIO.loadData(thisFeatureFolder,
                    AppConstant.DANHSACH_VITRI_BANDO), false);
            ListView lv = new ListView(this);
            lv.setAdapter(new ArrayAdapter<ViTri>(this, android.R.layout.simple_list_item_1, listViTri));
            new AlertDialog.Builder(this).setView(lv).show();
        } else if (i == R.id.mnuBanDo_showSavedMarker) {
            showSavedMarker();
        } else if (i == R.id.mnuBanDo_XoaSavedMarker) {
            MyFileIO.clearFolderContent(thisFeatureFolder);
            listViTri.clear();
        } else if (i == R.id.mnuBanDo_findplace_byMarkerName) {
            final EditText txtMarkerName = new EditText(this);
            new AlertDialog.Builder(this).setTitle(R.string.AlertDialog_title_findplace_byMarkerName)
                .setView(txtMarkerName)
                .setPositiveButton(R.string.AlertDialog_button_timkiem_findplace_byMarkerName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<ViTri> listTimKiem = MyGoogleMap.xuLyTimKiem(txtMarkerName.getText().toString(), listViTri);
                        if (listTimKiem != null && listTimKiem.size() == 0) {
                            Toast.makeText(BanDoActivity.this, R.string.AlertDialog_Toast_markernotfound_vitri,
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        MyGoogleMap.showMarker(mMap, listTimKiem, true);
                        MyGoogleMap.showFrameWithAllMarker(listTimKiem, mMap);
                        dialog.dismiss();
                    }
                })
                .show();
        } else if (i == R.id.mnuBanDo_findplace_byPosition) {
            final View v = getLayoutInflater().inflate(R.layout.alertdialog_vitri, null);
            v.findViewById(R.id.row_locationname_alertdialog_vitri).setVisibility(View.GONE);
            v.findViewById(R.id.row_locationname_content_alertdialog_vitri).setVisibility(View.GONE);
            v.findViewById(R.id.row_note_alertdialog_vitri).setVisibility(View.GONE);
            v.findViewById(R.id.row_note_content_alertdialog_vitri).setVisibility(View.GONE);
            new AlertDialog.Builder(this).setView(v).setPositiveButton(R.string.AlertDialog_button_timkiem_findplace_byPosition,
                            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    double lat = Double.parseDouble(((EditText) v.findViewById(R.id.txtViDo_alertdialog)).getText().toString());
                    double lon = Double.parseDouble(((EditText) v.findViewById(R.id.txtKinhDo_alertdialog)).getText().toString());
                    LatLng pos = new LatLng(lat, lon);
                    mMap.addMarker(new MarkerOptions()
                            .position(pos)
                            .title(BanDoActivity.this.getResources().getString(R.string.btnMyLocationGM_title_marker)));
                    MyGoogleMap.zoomCameraTo(mMap, pos, AppConstant.ZOOM_BUILDINGLEVEL);
                    dialog.dismiss();
                }
            }).show();
        } else if (i == R.id.OptionMenu_TimDuongDi_Bando_Walking) {
            travelMode = MyGoogleMap.FindWayTask.WALKING;
        } else if (i == R.id.OptionMenu_TimDuongDi_Bando_Transit) {
            travelMode = MyGoogleMap.FindWayTask.TRANSIT;
        } else if (i == R.id.OptionMenu_TimDuongDi_Bando_Driving) {
            travelMode = MyGoogleMap.FindWayTask.DRIVING;
        }  else if (i == R.id.OptionMenu_TimDuongDi_Bando_SaveMapInstruction) {
            final EditText txtName = new EditText(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(BanDoActivity.this);
            builder.setTitle(R.string.AlertDialog_title_SaveMapInstruction_TimDuongDi).setView(txtName)
                    .setPositiveButton(R.string.AlertDialog_buttonSave_SaveMapInstruction_TimDuongDi,
                            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final MyGoogleMap.SaveMapInstructionTask saveTask = new MyGoogleMap.SaveMapInstructionTask(mMap, listCotMoc,
                            listChangDuong, txtName.getText().toString(), BanDoActivity.this);

                    if(MyFileIO.makeFolder(saveTask.getFolderName())) saveTask.execute();

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(txtName.getWindowToken(), 0);
                }
            }).show();
        } else if (i == R.id.OptionMenu_TimDuongDi_Bando_ClearOnMap) {
            mMap.clear();
            listChangDuong.clear();
            adapterChangDuong.notifyDataSetChanged();
        } else if (i == R.id.OptionMenu_TimDuongDi_Bando_ClearOnDisk) {
            MyFileIO.clearFolderStartWith(MyGoogleMap.SaveMapInstructionTask.SAVEMAPINSTRUCTION,
                    MyGoogleMap.SaveMapInstructionTask.PREFIX_MAP_INSTRUCTION_FOLDER);
        } else if (i == R.id.mnuBanDo_theodoiduongdi_lockway) {
            BanDoActivity.this.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
            lockBackButton = true;
            Toast.makeText(BanDoActivity.this, R.string.Toast_theodoiduongdi_bando_lock, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
        mo danh sach cac diem da luu
        click chon 2 diem tro len
        bam nut tim duong
        show len    ban do
                    listview
     */
    private void showSavedMarker() {
        ListView lv = new ListView(this);
        if (currentTabId.equals(getResources().getString(R.string.TabHost_tabtitle_timdiadiem_bando))) {
            lv.setAdapter(new ArrayAdapter<ViTri>(this, android.R.layout.simple_list_item_1, listViTri));
            MyGoogleMap.showMarker(mMap, listViTri, true);
            new AlertDialog.Builder(this).setView(lv).show();
        } else if (currentTabId.equals(getResources().getString(R.string.TabHost_tabtitle_timduongdi_bando))) {
            listChangDuong.clear();
            adapterChangDuong.notifyDataSetChanged();
            showFindWayDialog(lv, listChangDuong, adapterChangDuong);
        } else if (currentTabId.equals(getResources().getString(R.string.TabHost_tabtitle_theodoiduongdi_bando))) {
            showFindWayDialog(lv, null, null);
        }
    }

    private void showFindWayDialog(ListView lv, final List<String> listChangDuong, final ArrayAdapter adapterChangDuong) {
        mMap.clear();
        listCacDiemTimDuongDi = new ArrayList<ViTri>();
        lv.setAdapter(new ArrayAdapter<ViTri>(this, android.R.layout.simple_list_item_1, listViTri));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.getAlpha() != 0.5f) {
                    listCacDiemTimDuongDi.add(listViTri.get(position));
                    view.setAlpha(0.5f);
                }
            }
        });
        new AlertDialog.Builder(this).setView(lv).setPositiveButton(R.string.AlertDialog_button_timkiem_findway, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listCotMoc = new ArrayList<LatLng>();
                listToaDoVeDuongDi = new ArrayList<LatLng>();
                MyGoogleMap.FindWayTask fw = new MyGoogleMap.FindWayTask(listChangDuong, listCotMoc, listToaDoVeDuongDi,
                        adapterChangDuong, mMap, travelMode, listCacDiemTimDuongDi, BanDoActivity.this);
                fw.execute();
            }
        }).show();
    }

    private ViTri timViTriTuMarker(Marker marker, boolean remove) {
        ViTri vt = null;
        for (int i = 0; i < listViTri.size(); i++) {
            if (listViTri.get(i).toString().equals(getThongTinMarker(marker))) {
                vt = (remove) ? listViTri.remove(i) : listViTri.get(i);
                break;
            }
        }
        return vt;
    }

    private String getThongTinMarker(Marker m) {
        return "ViTri " + m.getTitle() + "(" + m.getPosition().longitude
                + ", " + m.getPosition().latitude +")" + "\n" + m.getSnippet();
    }

    private void xuLyLuuDanhDau() {
        if (MyFileIO.saveData(listViTri, thisFeatureFolder, AppConstant.DANHSACH_VITRI_BANDO))
            Toast.makeText(BanDoActivity.this, R.string.AlertDialog_Toast_addok_vitri, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (lockBackButton) {
            BanDoActivity.this.getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
            lockBackButton = false;
            txtDiaChiHienTai_theodoi_duongdi.setText("");
            Toast.makeText(BanDoActivity.this, R.string.Toast_theodoiduongdi_bando_unlock, Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lockBackButton) locationManager.requestLocationUpdates(psType, 500, 0.5f, BanDoActivity.this);
        IntentFilter filterWifi3GGPS = new IntentFilter();
        filterWifi3GGPS.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filterWifi3GGPS.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        registerReceiver(gpsWifi3GReceiver, filterWifi3GGPS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(gpsWifi3GReceiver != null)
            unregisterReceiver(gpsWifi3GReceiver);
    }
}