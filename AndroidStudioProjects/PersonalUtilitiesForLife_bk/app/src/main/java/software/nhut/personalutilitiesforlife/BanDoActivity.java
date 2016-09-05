package software.nhut.personalutilitiesforlife;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import util.MyFileIO;
import util.MyGoogleMap;

public class BanDoActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    private GoogleMap mMap;
    private TextView txtSearch_TabTimDiaDiem, txtTemp;
    private ImageButton btnSearch_TabTimDiaDiem;
    private ListView lvDiaDiem_TabTimDiaDIem;
    private SeekBar sbrZoom_TabTimDiaDiem;
    private LatLng currentPosition, position;
    private String info;
    private LocationManager locationManager;
    private String psType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_do);

        addControls();
        addEvents();
    }

    private void addEvents() {
        txtSearch_TabTimDiaDiem.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    btnSearch_TabTimDiaDiem.callOnClick();
                }
                return false;
            }
        });
        btnSearch_TabTimDiaDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String place = txtSearch_TabTimDiaDiem.getText().toString();
                // get address in string for used location for the map
                /* get latitude and longitude from the adderress */
                Geocoder geoCoder = new Geocoder(BanDoActivity.this, Locale.getDefault());
                try
                {
                    List<Address> addresses = geoCoder.getFromLocationName(place, 5);
                    if (addresses.size() > 0)
                    {
                        Double lat = (double) (addresses.get(0).getLatitude());
                        Double lon = (double) (addresses.get(0).getLongitude());

                        Log.d("lat-long", "" + lat + ", " + lon);
                        final LatLng user = new LatLng(lat, lon);
                        /*used marker for show the location */
                        Marker hamburg = mMap.addMarker(new MarkerOptions()
                                .position(user)
                                .title(place)
                                .icon(BitmapDescriptorFactory
                                        .fromResource(R.drawable.star1)));
                        // Move the camera instantly to hamburg with a zoom of seekbar
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, sbrZoom_TabTimDiaDiem.getProgress() + 11));
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        sbrZoom_TabTimDiaDiem.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mMap.animateCamera(CameraUpdateFactory.zoomTo(progress + 11), 2000, null);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void addControls() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapCoiBanDo);
        mapFragment.getMapAsync(this);
        txtSearch_TabTimDiaDiem = (TextView) findViewById(R.id.txtSearch_TabTimDiaDiem);
        btnSearch_TabTimDiaDiem = (ImageButton) findViewById(R.id.btnSearch_TabTimDiaDiem);
        lvDiaDiem_TabTimDiaDIem = (ListView) findViewById(R.id.lvDiaDiem_TabTimDiaDIem);
        sbrZoom_TabTimDiaDiem = (SeekBar) findViewById(R.id.sbrZoom_TabTimDiaDiem);

        txtTemp = (TextView) findViewById(R.id.txttemp);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng HCM = new LatLng(10.8230989, 106.6296638);
        mMap.addMarker(new MarkerOptions().position(HCM).title("HCM"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(HCM));
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                position = latLng;
                Log.d("lat-long", "" + position.latitude + ", " + position.longitude);
                info = MyGoogleMap.getAddressFromLocation(position.latitude,
                        position.longitude, BanDoActivity.this);
                txtTemp.setText(info);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", info);
                clipboard.setPrimaryClip(clip);
            }
        });
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                locationManager.requestLocationUpdates(psType, 5000, 10, BanDoActivity.this);
                if (currentPosition != null) {
                    mMap.getMyLocation();
                    mMap.addMarker(new MarkerOptions()
                            .position(currentPosition)
                            .title(BanDoActivity.this.getResources().getString(R.string.btnMyLocationGM_title_marker)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(sbrZoom_TabTimDiaDiem.getProgress() + 11), 2000, null);
                } else {
                    Toast.makeText(BanDoActivity.this,
                            R.string.btnMyLocationGM_NoCurrentPosition, Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bando, menu);
        return super.onCreateOptionsMenu(menu);
    }

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
        }
        return super.onOptionsItemSelected(item);
    }
}