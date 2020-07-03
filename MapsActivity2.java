package com.example.stech20;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lat=0,lon=0;
    int bp=0;
    String loc="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Intent i=getIntent();
        lat=i.getDoubleExtra("latitude",0);
        lon=i.getDoubleExtra("longitude",0);
        loc=i.getStringExtra("ss");

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
        LatLng sydney = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title(loc+" LOCATION"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void onBackPressed(){
        bp = (bp + 1);
//        Intent i=new Intent(getApplicationContext(),MainActivity.class);
//        startActivity(i);
        Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();

        if (bp>1) {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            //this.finish();
        }
    }
}
