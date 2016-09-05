package software.nhut.personalutilitiesforlife.data;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.DataOutput;
import java.io.Serializable;

/**
 * Created by Nhut on 7/4/2016.
 */
public class ViTri implements Serializable{
    private String latitude;
    private String longtitude;
    private String title;
    private String note;

    public ViTri(String latitude, String longtitude, String title, String note) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.title = title;
        this.note = note;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "ViTri " + title + "(" + longtitude + ", " + latitude +")" + "\n" + note;
    }

    public MarkerOptions copyInfoToMarker() {
        return new MarkerOptions().title(this.title).snippet(this.note)
                .position(new LatLng(Double.parseDouble(this.latitude),
                Double.parseDouble(this.longtitude)));
    }

    public void setInfoFromMarker(Marker marker) {
        LatLng l = marker.getPosition();
        this.latitude = Double.toString(l.latitude);
        this.longtitude = Double.toString(l.longitude);
        this.title = marker.getTitle();
        this.note = marker.getSnippet();
    }

    public LatLng getPosition() {
        return new LatLng(Double.parseDouble(this.latitude),
                Double.parseDouble(this.longtitude));
    }

    public String getPositionString() {
        return this.latitude + "," + this.longtitude;
    }

    public double getLongtitudeValue() {
        return Double.parseDouble(this.longtitude);
    }

    public double getLatitudeValue() {
        return Double.parseDouble(this.latitude);
    }
}
