package util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import software.nhut.personalutilitiesforlife.R;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.ViTri;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nhut on 6/25/2016.
 */
public class MyGoogleMap {

    public static void searchPlaceByName(Context context, String searchString, GoogleMap gMap) {
        // get address in string for used location for the map
        /* get latitude and longitude from the adderress */
        Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(searchString, 5);
            if (addresses.size() > 0) {
                Double lat = (double) (addresses.get(0).getLatitude());
                Double lon = (double) (addresses.get(0).getLongitude());

                Log.d("lat-long", "" + lat + ", " + lon);
                final LatLng searchPosition = new LatLng(lat, lon);
                        /*used marker for show the location */
                Marker searchPlace = gMap.addMarker(new MarkerOptions()
                        .position(searchPosition)
                        .title(searchString)
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.star1)));
                // Move the camera instantly to hamburg with a zoom of seekbar
                MyGoogleMap.zoomCameraTo(gMap, searchPosition, AppConstant.ZOOM_STREETLEVEL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAddressFromLocation(final double latitude,
                                              final double longitude,
                                              final Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    latitude, longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                return getInfo(address);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getInfo(Address addr) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < addr.getMaxAddressLineIndex(); i++) {
//            sb.append(i + ": " + addr.getAddressLine(i) + "\n");
//        }
//
//        return  "AdminArea : " + addr.getAdminArea() + "\n" +
//                "SubAdminArea : " + addr.getSubAdminArea() + "\n" +
//                "AddressLine : " + sb.toString() + "\n" +
//                "CountryCode : " + addr.getCountryCode() + "\n" +
//                "CountryName : " + addr.getCountryName() + "\n" +
//                "FeatureName : " + addr.getFeatureName() + "\n" +
//                "Locality : " + addr.getLocality() + "\n" +
//                "SubLocality : " + addr.getSubLocality() + "\n" +
//                "Phone : " + addr.getPhone() + "\n" +
//                "PostalCode : " + addr.getPostalCode() + "\n" +
//                "Premises : " + addr.getPremises() + "\n" +
//                "Thoroughfare : " + addr.getThoroughfare() + "\n" +
//                "SubThoroughfare : " + addr.getSubThoroughfare() + "\n" +
//                "Url : " + addr.getUrl();
        String street = addr.getAddressLine(0);
        String ward = (" P." + addr.getAddressLine(1).replace("Phường", "").replace("phường", "")).replace("P. ", "P.");
        String district = (" Q." + addr.getAddressLine(2).replace("District", "")
                .replace("District ", "").replace("Dist", "").replace("Dist.", "").replace("Quận", "").replace("Quận ", "")).replace("Q. ", "Q.");
        String city = " " + addr.getAddressLine(3);
        return (street + ward + district + city).trim();
    }

    /**
     *
     * @param pos latitude and longtitude
     * @param zoomLevel zoom level is from 0 to 21. outside this range means no zoom, zoom level is unchanged.
     */
    public static void zoomCameraTo(GoogleMap gMap, LatLng pos, int zoomLevel ) {
        if (gMap == null) return;
        if (pos != null) {
            gMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
//            gMap.addMarker(new MarkerOptions()
//                    .position(pos));
        }
        if (zoomLevel >= 0 && zoomLevel <= 21) {
            gMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel));
            if (zoomLevel == 1) gMap.animateCamera(CameraUpdateFactory.zoomOut());
            if (zoomLevel == 0) {
                gMap.animateCamera(CameraUpdateFactory.zoomOut());
                gMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        }
    }

    /**
     *
     * @param listViTriTimKiem
     * @return double array with 3 elements.
     *          array[0] is latitude of center point
     *          array[1] is longtitude of center point
     *          array[2] is zoomlevel for map to display all found marker at the same time
     */
    public static void showFrameWithAllMarker(List<ViTri> listViTriTimKiem, GoogleMap gMap) {
        if (listViTriTimKiem != null && listViTriTimKiem.size() == 0) return;
        double  maxLat = listViTriTimKiem.get(0).getLatitudeValue(),
                minLat = listViTriTimKiem.get(0).getLatitudeValue(),
                maxLon = listViTriTimKiem.get(0).getLongtitudeValue(),
                minLon = listViTriTimKiem.get(0).getLongtitudeValue(),
                centerLat = 0, centerLon = 0;
        float delta = 0;
        for (ViTri vt : listViTriTimKiem) {
            LatLng pos = vt.getPosition();
            if (pos.latitude > maxLat) maxLat = pos.latitude;
            if (pos.latitude < minLat) minLat = pos.latitude;
            if (pos.longitude > maxLon) maxLon = pos.longitude;
            if (pos.longitude < minLon) minLon = pos.longitude;
        }
        centerLat = (maxLat + minLat) / 2;
        centerLon = (maxLon + minLon) / 2;
        delta = (float) ((Math.abs(maxLat - centerLat) > Math.abs(maxLon - centerLon)) ?
                (maxLat - centerLat) * 1.1 : (maxLon - centerLon) * 1.1);
        delta = Math.abs(delta);
        zoomCameraTo(gMap, new LatLng(centerLat, centerLon), calculateZoomLevel(delta));
    }

    public static void showFrameWithAllMarker(GoogleMap gMap, List<LatLng> listViTriTimKiem) {
        if (listViTriTimKiem != null && listViTriTimKiem.size() == 0) return;
        double  maxLat = listViTriTimKiem.get(0).latitude,
                minLat = listViTriTimKiem.get(0).latitude,
                maxLon = listViTriTimKiem.get(0).longitude,
                minLon = listViTriTimKiem.get(0).longitude,
                centerLat = 0, centerLon = 0;
        float delta = 0;
        for (LatLng pos : listViTriTimKiem) {
            if (pos.latitude > maxLat) maxLat = pos.latitude;
            if (pos.latitude < minLat) minLat = pos.latitude;
            if (pos.longitude > maxLon) maxLon = pos.longitude;
            if (pos.longitude < minLon) minLon = pos.longitude;
        }
        centerLat = (maxLat + minLat) / 2;
        centerLon = (maxLon + minLon) / 2;
        delta = (float) ((Math.abs(maxLat - centerLat) > Math.abs(maxLon - centerLon)) ?
                (maxLat - centerLat) * 1.1 : (maxLon - centerLon) * 1.1);
        delta = Math.abs(delta);
        zoomCameraTo(gMap, new LatLng(centerLat, centerLon), calculateZoomLevel(delta));
    }

    private static int calculateZoomLevel(float delta) {
        if (delta > 24.7f) return 2;
        else if (delta > 12.55f) return 3;
        else if (delta > 6.505f) return 4;
        else if (delta > 3.2469f) return 5;
        else if (delta > 1.978f) return 6;
        else if (delta > 0.7519f) return 7;
        else if (delta > 0.4124f) return 8;
        else if (delta > 0.2068f) return 9;
        else if (delta > 0.1108f) return 10;
        else if (delta > 0.0628f) return 11;
        else if (delta > 0.0304f) return 12;
        else if (delta > 0.0151f) return 13;
        else if (delta > 0.0075f) return 14;
        else if (delta > 0.0037f) return 15;
        else if (delta > 0.0018f) return 16;
        else if (delta > 0.0009f) return 17;
        else if (delta > 0.0005f) return 18;
        else if (delta > 0.0002f) return 19;
        else if (delta > 0.0001f) return 20;
        else return 21;
    }

    public static void showMarker(GoogleMap gMap, List<ViTri> list, boolean clearAllMarkers) {
        if (clearAllMarkers) gMap.clear();
        for (ViTri vt : list) {
            gMap.addMarker(vt.copyInfoToMarker());
        }
    }

    public static List<ViTri> xuLyTimKiem(String markerName, List<ViTri> list) {
        String markerName_standardized = MyStringFormater.removeAccent(markerName.trim().toLowerCase());
        List<ViTri> listPos = new ArrayList<ViTri>();
        for (ViTri vt : list) {
            if (MyStringFormater.removeAccent(vt.getTitle().trim().toLowerCase())
                    .contains(markerName_standardized)) {
                listPos.add(vt);
            }
        }
        return listPos;
    }

    public static void showDirectionArrow(GoogleMap gMap, LatLng position, Location newLoc) {
        LatLng newLocation = getArrowHeadPoint(position, new LatLng(newLoc.getLatitude(), newLoc.getLongitude()));
        // draw arrow tail
        CircleOptions optionCircleStart = new CircleOptions();
        optionCircleStart.center(position).radius(2);
        Circle cirStart = gMap.addCircle(optionCircleStart);
        cirStart.setStrokeColor(Color.BLUE);
        cirStart.setFillColor(Color.RED);

        // draw arrow body
        PolylineOptions plOption = new PolylineOptions();
        plOption.add(position, newLocation);
        Polyline polyline= gMap.addPolyline(plOption);
        polyline.setColor(Color.BLUE);
        polyline.setWidth(5);
        polyline.setZIndex(1);

        // draw arrow head
        CircleOptions optionCircleEnd = new CircleOptions();
        optionCircleEnd.center(newLocation).radius(1);
        Circle cirEnd = gMap.addCircle(optionCircleEnd);
        cirEnd.setStrokeColor(Color.RED);
        cirEnd.setFillColor(Color.RED);
    }

    private static LatLng getArrowHeadPoint(LatLng start, LatLng end) {
        // chon x3 = 2*x2 - x1, y3 = 2*y2 - y1
        return new LatLng(2 * end.latitude - start.latitude, 2 * end.longitude - start.longitude);
    }

    /*
      origin: LatLng | String | google.maps.Place,
      destination: LatLng | String | google.maps.Place,
      mode: TravelMode,
      transitOptions: TransitOptions,
      drivingOptions: DrivingOptions,
      unitSystem: UnitSystem,
      waypoints[]: DirectionsWaypoint,
      optimizeWaypoints: Boolean,
      provideRouteAlternatives: Boolean,
      avoidHighways: Boolean,
      avoidTolls: Boolean,
      region: String
     */
    /*
     *    doinBG {find way lay du lieu}
     *    onPrUp [show data]
     *    postE [chay tiep cai ke]
     */
    private static boolean ENDLOCATION = true;
    private static boolean STARTLOCATION = false;
    private static String URLSTRING = "https://maps.googleapis.com/maps/api/directions/json?" +
                                            "mode=%s&origin=%s&destination=%s&key=" + AppConstant.GMAPKEY;
    public static class FindWayTask extends AsyncTask<ViTri, LatLng, Void> {
        private GoogleMap gMap;
        private List<String> listChangDuong;
        private ArrayAdapter adapterChangDuong;
        private List<LatLng> listCotMoc, listToaDoVeDuongDi;
        private List<ViTri> listCacDiaDiem;
        private Context context;
        private String mode;
        private String JsonData;
        public static final String DRIVING = "driving";
        public static final String TRANSIT = "transit";
        public static final String WALKING = "walking";

        public FindWayTask(List<String> listChangDuong, List<LatLng> listCotMoc,
                           List<LatLng> listToaDoVeDuongDi, ArrayAdapter adapterChangDuong,
                           GoogleMap gMap, String mode, List<ViTri> listCacDiaDiem, Context context) {
            this.listChangDuong = listChangDuong;
            if (listChangDuong != null) {
                this.listChangDuong.clear();
            }
            this.adapterChangDuong = adapterChangDuong;
            this.listToaDoVeDuongDi = listToaDoVeDuongDi;
            this.listCacDiaDiem = listCacDiaDiem;
            this.gMap = gMap;
            this.listCotMoc = listCotMoc;
            this.mode = mode;
            this.context = context;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            showFrameWithAllMarker(gMap, this.listCotMoc);
        }

        @Override
        protected void onProgressUpdate(LatLng... cotMoc) {
            if (cotMoc == null || cotMoc.length == 0) return;
            this.listCotMoc.addAll(Arrays.asList(cotMoc));
            showPoint(gMap, Arrays.asList(cotMoc));
            //            showWay(gMap, Arrays.asList(cotMoc));
            new ParserTask(gMap, listToaDoVeDuongDi).execute(JsonData);
            if (this.adapterChangDuong != null) this.adapterChangDuong.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(ViTri... pos) {
            if (listCacDiaDiem.size() < 2) return null;
            else if (listCacDiaDiem.size() == 2)
                startFindWay(listCacDiaDiem.get(0), listCacDiaDiem.get(1), new ViTri("-1", "", "", ""));
            else if (listCacDiaDiem.size() > 2) {
                for (int i = 0; i < listCacDiaDiem.size() - 1; i++)
                    startFindWay(listCacDiaDiem.get(i), listCacDiaDiem.get(i + 1), new ViTri("" + (i + 1), "", "", ""));
            }
            return null;
        }

        private void startFindWay(ViTri... pos) {
            List<String> listStep = new ArrayList<String>();
            listStep.addAll(findWay(pos[0].getPositionString(), pos[1].getPositionString()));
            if (listStep.size() == 0) {
                Log.i("MyTag", "There is no way!");
                return;
            }
            if (pos.length > 2) {
                if (this.listChangDuong != null) this.listChangDuong.addAll(formatSteps(listStep, (int)pos[2].getLatitudeValue(), context));
            }
            publishProgress(MyData.convertListToArray(getPosList(listStep)));
        }

        private List<String> formatSteps(List<String> listStep, int stepIndex, Context context) {
            List<String> standardList = new ArrayList<String>();
            String labelTotal, labelStage;
            if (stepIndex <= 0) {
                labelTotal = "Total ";
                labelStage = "Stage %d" + " : ";
            } else {
                labelTotal = "Total of step " + stepIndex + " : ";
                labelStage = "Stage %d" + " of " + stepIndex + " : ";
            }
            for (int i = 0; i < listStep.size(); i++) {
                String s = listStep.get(i);
                LatLng sl = getPos(s, STARTLOCATION);
                LatLng el = getPos(s, ENDLOCATION);
                if (i == 0) {
                    standardList.add(labelTotal + s.substring(0, s.indexOf(",end_location"))
                            + "\n" + "start_location (" + MyGoogleMap.getAddressFromLocation(sl.latitude, sl.longitude, context) + ")"
                            + "\n" + "end_location (" + MyGoogleMap.getAddressFromLocation(el.latitude, el.longitude, context) + ")"
                            + "\n" + s.substring(s.indexOf("travel_mode"), s.length()));
                } else {
                    standardList.add(String.format(labelStage, i) + s.substring(0, s.indexOf(",end_location"))
//                            + "\n" + "start_location (" + sl.latitude + "," + sl.longitude + ")"
//                            + "\n" + "end_location (" + el.latitude + "," + el.longitude + ")");
                            + "\n" + "location (" + MyGoogleMap.getAddressFromLocation(el.latitude, el.longitude, context) + ")");
                }
            }
            return standardList;
        }

        private void showWay(GoogleMap gMap, List<LatLng> listCotMoc) {
            CircleOptions optionCircleStart = new CircleOptions();
            optionCircleStart.center(listCotMoc.get(0)).radius(2);
            Circle cirStart = gMap.addCircle(optionCircleStart);
            cirStart.setStrokeColor(Color.BLUE);
            cirStart.setFillColor(Color.RED);
            if (listCotMoc.size() == 1) return;
            CircleOptions optionCircleEnd = new CircleOptions();
            optionCircleEnd.center(listCotMoc.get(listCotMoc.size() - 1)).radius(2);
            Circle cirEnd = gMap.addCircle(optionCircleEnd);
            cirEnd.setStrokeColor(Color.BLUE);
            cirEnd.setFillColor(Color.RED);

            PolylineOptions plOption = new PolylineOptions();
            plOption.add(listCotMoc.get(0));
            for (int i = 1; i < listCotMoc.size() - 1; i++) {
                plOption.add(listCotMoc.get(i));
                CircleOptions optionCircle = new CircleOptions();
                optionCircle.center(listCotMoc.get(i)).radius(1);
                Circle c = gMap.addCircle(optionCircle);
                c.setStrokeColor(Color.GRAY);
                c.setFillColor(Color.YELLOW);
            }
            plOption.add(listCotMoc.get(listCotMoc.size() - 1));
            Polyline polyline= gMap.addPolyline(plOption);
            polyline.setColor(Color.GREEN);
            polyline.setWidth(5);
            polyline.setZIndex(1);
//            showFrameWithAllMarker(gMap, listCotMoc);
        }

        private void showPoint(GoogleMap gMap, List<LatLng> listCotMoc) {
            CircleOptions optionCircleStart = new CircleOptions();
            optionCircleStart.center(listCotMoc.get(0)).radius(2);
            Circle cirStart = gMap.addCircle(optionCircleStart);
            cirStart.setStrokeColor(Color.BLUE);
            cirStart.setFillColor(Color.RED);
            if (listCotMoc.size() == 1) return;
            CircleOptions optionCircleEnd = new CircleOptions();
            optionCircleEnd.center(listCotMoc.get(listCotMoc.size() - 1)).radius(2);
            Circle cirEnd = gMap.addCircle(optionCircleEnd);
            cirEnd.setStrokeColor(Color.BLUE);
            cirEnd.setFillColor(Color.RED);

            PolylineOptions plOption = new PolylineOptions();
            plOption.add(listCotMoc.get(0));
            for (int i = 1; i < listCotMoc.size() - 1; i++) {
                plOption.add(listCotMoc.get(i));
                CircleOptions optionCircle = new CircleOptions();
                optionCircle.center(listCotMoc.get(i)).radius(1);
                Circle c = gMap.addCircle(optionCircle);
                c.setStrokeColor(Color.GRAY);
                c.setFillColor(Color.YELLOW);
            }
        }
/*
    listStep size = 4 :
            startlocaion    endlocation
    [0]        slo             elo
    [1]        slo             elo1
    [2]        elo1            elo2
    [3]        elo2            elo

    => Lay ra listCotMoc la :
    [0]        slo
    [1]        elo1
    [2]        elo2
    [3]        elo

    => lay o vi tri [1] [2] => start i = 1 va end i = 2
    => i = 1; i < 4 - 1

    => lay o vi tri [1] [2] [3] => start i = 1 va end i = 3
    => i = 1; i < 4

 */
        private List<LatLng> getPosList(List<String> listStep) {
            Log.i("MyTag", "listStep size " + listStep.size());
            List<LatLng> listCotMoc = new ArrayList<LatLng>();
            listCotMoc.add(0, getPos(listStep.get(0), STARTLOCATION));
            for (int i = 1; i < listStep.size(); i++) {
                if (listStep.get(i).startsWith("Total of step ")) continue;
                listCotMoc.add(i, getPos(listStep.get(i), ENDLOCATION));
                Log.i("MyTag", "listStep[" + i + "]" + "=" + listCotMoc.get(i));
            }
            return listCotMoc;
        }

        private LatLng getPos(String step, boolean endOrStartLocation) {
            String []pos = null;
            if(endOrStartLocation == ENDLOCATION) {
                int startEndLocationPos = step.indexOf("end_location:LatLng(") + 20;
                pos = step.substring(startEndLocationPos, step.indexOf("),", startEndLocationPos)).split(",");
            } else {
                int startStartLocationPos = step.indexOf("start_location:LatLng(") + 22;
                pos = step.substring(startStartLocationPos, step.indexOf("),", startStartLocationPos)).split(",");
            }
            return new LatLng(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]));
        }

        public List<String> findWay(String startPosition, String endPosiion) {
            try {
                URL urlGoogleDirService = new URL(String.format(URLSTRING, this.mode, startPosition, endPosiion));
                HttpURLConnection urlGoogleDirCon = (HttpURLConnection)urlGoogleDirService.openConnection();
                urlGoogleDirCon.setAllowUserInteraction(false);
                urlGoogleDirCon.setDoInput(true);
                urlGoogleDirCon.setDoOutput(false);
                urlGoogleDirCon.setUseCaches(true);
                urlGoogleDirCon.setRequestMethod("GET");
                urlGoogleDirCon.connect();
                InputStream is = urlGoogleDirCon.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
                String line = null;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                JsonData = sb.toString();
                return parseResponseJSON(JsonData);
            } catch(Exception e) {
                e.printStackTrace();
                return new ArrayList<String>();
            }
        }

        private List<String> parseResponseJSON(String responseJson) {
            List<String> mileStone = new ArrayList<String>();
            StringBuilder sb = new StringBuilder();
            int end = responseJson.lastIndexOf("\"distance\" :");
            int startTag = 0, temp, endTag = 0;
            if (end > -1) {
                do {
                    startTag = responseJson.indexOf("\"distance\" :", endTag);
                    temp = responseJson.indexOf("\"distance\" :", startTag + 12);
                    endTag = (temp == -1) ? responseJson.indexOf("}               ],", startTag + 12) : temp;
                    String []listStep = responseJson.substring(startTag, endTag).split("\"");
                    for (int i = 0; i < listStep.length; i++) {
                        if (listStep[i].contains(" km") || listStep[i].contains(" m") || listStep[i].contains("min")) {
                            sb.append(listStep[i] + ",");
                        } else if (listStep[i].contains("end_location") || listStep[i].contains("start_location")) {
                            sb.append(listStep[i] + ":");
                        } else if (listStep[i].contains("lat")) {
                            sb.append("LatLng(" + listStep[i + 1].replace(":", "").trim()
                                    + listStep[i + 3].replace("},", "").replace(":", "").trim() + "),");
                        } else if (listStep[i].contains("travel_mode")) {
                            sb.append(listStep[i] + ":" + listStep[i + 2]);
                        }
                    }
                    mileStone.add(sb.toString());
                    sb.delete(0, sb.length());
                } while (startTag < end);
                String tempString = mileStone.remove(0).concat(mileStone.get(1).substring(
                        mileStone.get(1).indexOf("travel_mode"), mileStone.get(1).length()));
                mileStone.add(0, tempString);
            }
            return mileStone;
        }
    }

    /*
    async task :
        doinBG() :  chuan bi thong so
                    set cameraChanged = false
                    publishprogress(thong so)
                    while(!cameraChanged){}
                    snapshot
        progressUpdate() :
                    move zoom camera
        oncamerachange() : set cameraChanged = true
        postExecute() : Toast message box
    */

    public static class SaveMapInstructionTask extends AsyncTask<Void, LatLng, String> {
        private boolean cameraChanged = false;
        public static final String SAVEMAPINSTRUCTION = AppConstant.FS + "storage" + AppConstant.FS + "sdcard0"
                + AppConstant.FS + "Download";
        public static final String PREFIX_MAP_INSTRUCTION_FOLDER = "Map_Instruction_";

        private String folderName = SAVEMAPINSTRUCTION + AppConstant.FS + PREFIX_MAP_INSTRUCTION_FOLDER;
        private GoogleMap mMap;
        private List<LatLng> listCotMoc;
        private List<String> listChangDuong;
        private String name;
        private Context context;
        private List<Bitmap> listSnapshot;
        private ProgressDialog pd;

        public SaveMapInstructionTask(GoogleMap mMap, List<LatLng> listCotMoc,
                                      List<String> listChangDuong, String folderName, Context context) {
            this.mMap = mMap;
            this.listCotMoc = listCotMoc;
            this.listChangDuong = listChangDuong;
            this.name = folderName;
            this.folderName += folderName;
            this.context = context;
            this.listSnapshot = new ArrayList<Bitmap>();

            Log.i("MyTag", "initial : listCotMoc size " + listCotMoc.size());
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(context);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setTitle(R.string.waitDialog_save_mapintruction_Bando_title);
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.setMax(100);
            pd.setProgress(0);
            pd.show();
        }
/*
    size = 5
    => 4 chang + 1 => (size - 1 + 1) = size
    i = 0 - 3 => i = 1 - 4 => i + 1
    4 chang / tong 5 chang = so % => (i + 1)/size = so %
*/
        @Override
        protected String doInBackground(Void... params) {
            Log.i("MyTag", "DoInBG : take snapshot of full map");
            LatLng step[] = new LatLng[4];
            step[2] = new LatLng(-1, 1);
            step[3] = new LatLng((100 * 1)/(listCotMoc.size()), 1);
            publishProgress(step);
            while(!cameraChanged) {};
            Log.i("MyTag", "DoInBG : take snapshot of full map done");
            for (int i = 0; i < listCotMoc.size() - 1; i++) {
                Log.i("MyTag", "DoInBG : prepare snapshot " + i);
                step[0] = listCotMoc.get(i);
                step[1] = listCotMoc.get(i + 1);
                step[2] = new LatLng(i, 1);
                step[3] = new LatLng((100 * (i + 1)) / listCotMoc.size(), 1);
                cameraChanged = false;
                publishProgress(step);
                while(!cameraChanged) {};
                Log.i("MyTag", "DoInBG : take snapshot " + i + " done");
            }
            while (listSnapshot.size() != listCotMoc.size()) {};
            return saveListAndSnapshots();
        }

        @Override
        protected void onProgressUpdate(LatLng... step) {
            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
                        @Override
                        public void onSnapshotReady(Bitmap bitmap) {
                            listSnapshot.add(bitmap);
                            Log.i("MyTag", "onMapLoaded : have taken snapshot " + bitmap.toString());
                            cameraChanged = true;
                        }
                    });
                }
            });
            int index = (int)step[2].latitude;
            if (index >= 0) {
                Log.i("MyTag", "2 points to move and zoom camera to " + step[0] + " " + step[1]);
                MyGoogleMap.showFrameWithAllMarker(mMap, Arrays.asList(new LatLng[]{step[0], step[1]}));//two_Point));
                Log.i("MyTag", "onProgressUpdate : show snapshot " + index);
            } else if (index < 0) {
                showFrameWithAllMarker(mMap, listCotMoc);
                Log.i("MyTag", "onProgressUpdate : show snapshot of full map");
            }
            pd.setProgress((int)step[3].latitude);
        }

        @Override
        protected void onPostExecute(String result) {
            mMap.setOnMapLoadedCallback(null);
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            pd.setProgress(100);
            showFrameWithAllMarker(mMap, listCotMoc);
            pd.dismiss();
        }

        private String saveListAndSnapshots() {
            String result = "";
            if (MyFileIO.saveData(listChangDuong, folderName, PREFIX_MAP_INSTRUCTION_FOLDER + name + ".list")) {
                result = "Save List OK";
                Log.i("MyTag", "save List OK");
            }

            FileOutputStream out = null;
            int index = 0;
            for (Bitmap snapshot : listSnapshot) {
                try {
                    out = new FileOutputStream(folderName + AppConstant.FS + index + ".jpeg");
                    if (index == 0)
                        MyImage.drawTextOnBitMap(snapshot, listChangDuong.get(0), 50, Color.BLACK).
                                compress(Bitmap.CompressFormat.JPEG, 90, out);
                    else snapshot.compress(Bitmap.CompressFormat.JPEG, 90, out);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        try {
                            out.flush();
                            out.close();
                            Log.i("MyTag", "Saved snapshot " + index);
                            index++;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (listSnapshot.size() == index) result += ", saved snapshots ok.";
            Log.i("MyTag", "done!");
            return result;
        }

        public String getFolderName() {
            return folderName;
        }
    }

    /*
        lay cac diem can tim duong di
        lay chi dan ve duong di toi cac diem
        lay ra danh sach cac diem
            step (info tong quat) lay toa do diem dau tien va cuoi
            step (o giua) lay toa do diem ket thuc
        ve len ban do :
            diem den, di bat dau : size 50, mau do, vien xanh
            diem o giua chang : size 25, mau vang, vien xam
        show thong tin cac chang len listview
     */

    class Place {
        private String name;
        private String address;
        private String description;
        private String phone;
        private double lat;
        private double lon;

        public Place(String name, String address, String description, double lat, double lon) {
            this.name = name;
            this.address = address;
            this.description = description;
            this.lat = lat;
            this.lon = lon;
        }

        public Place(String name, String address, String description, LatLng position) {
            this.name = name;
            this.address = address;
            this.description = description;
            this.lat = position.latitude;
            this.lon = position.longitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    /** A class to parse the Google Places in JSON format */
    private static class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
        private GoogleMap gMap;
        private List<LatLng> listToaDoVeDuongDi;
        private List<List<HashMap<String, String>>> listRoute;

        public ParserTask(GoogleMap gMap, List<LatLng> listToaDoVeDuongDi) {
            this.gMap = gMap;
            this.listToaDoVeDuongDi = listToaDoVeDuongDi;
        }

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            listRoute = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                listRoute = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            convertHashtoList();
            return listRoute;
        }

        private void convertHashtoList() {
            for (HashMap h : listRoute.get(0)) {
                listToaDoVeDuongDi.add(new LatLng(Double.parseDouble(h.get("lat").toString()),
                        Double.parseDouble(h.get("lng").toString())));
            }
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(15);
                lineOptions.color(Color.GREEN);
                lineOptions.zIndex(1);
            }
            // Drawing polyline in the Google Map for the i-th route
            gMap.addPolyline(lineOptions);
        }
    }

    private static class DirectionsJSONParser {

        /** Receives a JSONObject and returns a list of lists containing latitude and longitude */
        public List<List<HashMap<String,String>>> parse(JSONObject jObject){
            List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>() ;
            JSONArray jRoutes = null;
            JSONArray jLegs = null;
            JSONArray jSteps = null;

            try {

                jRoutes = jObject.getJSONArray("routes");

                /** Traversing all routes */
                for(int i=0;i<jRoutes.length();i++){
                    jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
                    List path = new ArrayList<HashMap<String, String>>();

                    /** Traversing all legs */
                    for(int j=0;j<jLegs.length();j++){
                        jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

                        /** Traversing all steps */
                        for(int k=0;k<jSteps.length();k++){
                            String polyline = "";
                            polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);

                            /** Traversing all points */
                            for(int l=0;l<list.size();l++){
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
                                hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
                                path.add(hm);
                            }
                        }
                        routes.add(path);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
            }

            return routes;
        }
        /**
         * Method to decode polyline points
         * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
         * */
        private List<LatLng> decodePoly(String encoded) {

            List<LatLng> poly = new ArrayList<LatLng>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }

            return poly;
        }
    }

    private static final double allowingDistance = 50; // allowing distance = 50 meters
    /**
     *
     * @param listViTriVeDuongDi
     * @param currentPos
     * @return true if go far away else false
     */
    public static boolean testGoSoFarAway(List<LatLng> listViTriVeDuongDi, LatLng currentPos) {
        return (minDeviation(listViTriVeDuongDi, currentPos) > allowingDistance) ?  true : false;
    }

    public static boolean testGoSoFarAway(List<LatLng> listViTriVeDuongDi, LatLng currentPos, int allowingDistance) {
        return (minDeviation(listViTriVeDuongDi, currentPos) > allowingDistance) ?  true : false;
    }

    private static double minDeviation(List<LatLng> listViTriVeDuongDi, LatLng currentPos) {
        int length = listViTriVeDuongDi.size();
        double minDistance = convertLatLngToDistance(listViTriVeDuongDi.get(0), currentPos);
        for (LatLng pos : listViTriVeDuongDi) {
            double tmp = convertLatLngToDistance(pos, currentPos);
            if (tmp < minDistance) minDistance = tmp;
        }
        Log.i("MyTag", "Min distance " + minDistance);
        return minDistance;
    }
    /*

    =======> latitude
    ||              (10, 10)        (11, 10)
    ||
    V
 longtitude         (10, 11)

                (10, 10) <---------> (11, 10)
                            111 km
                (10, 10)
                   ^
                   |
                   |   109 km
                   |
                   v
                (10, 11)
    */
    /**
     * distance = V(deltaLat^2 + deltaLong^2)
     * @param pos1
     * @param pos2
     * @return double : distance
     */
    private static double convertLatLngToDistance(LatLng pos1, LatLng pos2) {
        double lat = Math.pow(111*1000*(pos2.latitude - pos1.latitude), 2);
        double lon = Math.pow(109*1000*(pos2.longitude - pos1.longitude), 2);
        double R = Math.sqrt(lat + lon);
        Log.i("MyTag", "pos1(" + pos1.latitude + ", " + pos1.longitude
                + "), pos2(" + pos2.latitude + ", " + pos2.longitude
                + "), deltalat " + lat + ", deltalon " + lon + ", R " + R);
        return R;
    }
}