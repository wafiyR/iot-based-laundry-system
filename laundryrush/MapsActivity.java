package com.example.laundryrush;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        LatLng firstShop = new LatLng(2.250282, 102.286233); // 2.250282, 102.286233
        mMap.addMarker(new MarkerOptions().position(firstShop).title("Bubble Rush Laundry Shop MH"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(firstShop));

        LatLng secondShop = new LatLng(2.251885, 102.289159); // 2.251885, 102.289159
        mMap.addMarker(new MarkerOptions().position(secondShop).title("Bubble Rush Laundry Shop Seri Rama"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(secondShop));

        // mathrandom or java util random

        LatLng home = new LatLng(2.251956,102.286497);
        float zoom = 15; // initial zoom level
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, zoom));

        setPoiClick(mMap);
    }

    private void setPoiClick(final GoogleMap map){

        map.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest pointOfInterest) {

                Marker poiMarker = mMap.addMarker(new MarkerOptions()
                        .position(pointOfInterest.latLng)
                        .title(pointOfInterest.name));

                poiMarker.showInfoWindow();

            }
        });
    }
}