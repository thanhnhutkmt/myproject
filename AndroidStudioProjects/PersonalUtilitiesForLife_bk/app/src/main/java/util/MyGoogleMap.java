package util;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Nhut on 6/25/2016.
 */
public class MyGoogleMap {
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
        return addr.getAddressLine(0) + " P." + addr.getAddressLine(1)
                + " Q." + addr.getAddressLine(2).replace("District ", "") + " " + addr.getAddressLine(3);
    }

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
}
