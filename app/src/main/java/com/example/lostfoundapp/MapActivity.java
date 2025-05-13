package com.example.lostfoundapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        dbHelper = new DatabaseHelper(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<HashMap<String, String>> items = dbHelper.getAllAdverts();
        LatLng firstMarker = null;

        for (HashMap<String, String> item : items) {
            String location = item.get("location");
            String title = item.get("type") + ": " + item.get("description");

            LatLng latLng = null;

            if (location != null) {

                if (location.contains("Lat:") && location.contains("Lng:")) {
                    try {
                        String[] parts = location.replace("Lat:", "").replace("Lng:", "").split(",");
                        double lat = Double.parseDouble(parts[0].trim());
                        double lng = Double.parseDouble(parts[1].trim());
                        latLng = new LatLng(lat, lng);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocationName(location, 1);
                        if (addresses != null && !addresses.isEmpty()) {
                            Address address = addresses.get(0);
                            latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (latLng != null) {
                    mMap.addMarker(new MarkerOptions().position(latLng).title(title));
                    if (firstMarker == null) firstMarker = latLng;
                }
            }
        }

        // Move camera to first marker
        if (firstMarker != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstMarker, 12f));
        }
    }
}
