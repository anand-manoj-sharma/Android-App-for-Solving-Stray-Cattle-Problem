package com.example.stech20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Page1 extends AppCompatActivity {

    static Bitmap photo;
    static int jjj;
    Button pic, send, view;
    TextView lc;
    private static final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    TextView display;
    double lat = 0, lon = 0, str_lat = 0, str_log = 0;
    String add = "", name = "", cont = "";
    int PERMISSION_ID = 44, bp = 0;
    String key="";
    double la,ld;

    int flag = 0;
    private StorageReference sr;

    double min = Double.MAX_VALUE;
    DatabaseReference reff_pro;
    private FirebaseAuth mAuth;

    FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);

        pic = findViewById(R.id.pic);
        send = findViewById(R.id.send);
        view = findViewById(R.id.view);
        display = findViewById(R.id.display);
        sr = FirebaseStorage.getInstance().getReference();
        imageView = findViewById(R.id.imageView1);
        lc=findViewById(R.id.lc);
        reff_pro = FirebaseDatabase.getInstance().getReference("Professional");

        mAuth = FirebaseAuth.getInstance();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                getLastLocation();

                reff_pro.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Type proInfo = data.getValue(Type.class);

                             la = proInfo.getLatitude();
                             ld = proInfo.getLongitude();

                            //double res=distance(lat,lon,la,ld);

//                            key=data.getKey().toString();
                            float[] res = new float[1];
                            Location.distanceBetween(lat, lon, la, ld, res);

                            if (min > res[0]){// && proInfo.getStatus()!=1) {
                                min = res[0];
                                str_lat = la;
                                str_log = ld;
                                name = proInfo.getName();
                                cont = proInfo.getContact();
                                key=data.getKey().toString();

                                flag=1;
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        lc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Main3Activity.class);
                startActivity(i);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    long stat = MainActivity.stat = 1;
                    reff_pro.child(key).child("ulatitude").setValue(lat);
                    reff_pro.child(key).child("ulongitude").setValue(lon);
                    reff_pro.child(key).child("status").setValue(1);
                    Geocoder g = new Geocoder(getApplicationContext(), Locale.getDefault());
                    String mycity = "";
                    try {
                        List<Address> addresses = g.getFromLocation(str_lat, str_log, 1);
                        add = addresses.get(0).getAddressLine(0);
                        mycity = addresses.get(0).getLocality();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), str_lat + " " + str_log + " " + add + "   " + key, Toast.LENGTH_LONG).show();

                    display.setText("Send To:-\n" + "Name: " + name + "\nAddress: " + add + "\nContact: " + cont);
                    jjj=1;

                }
           // }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ss="PROFESSIONAL";
                Intent i = new Intent(getApplicationContext(), MapsActivity2.class);
                i.putExtra("latitude", str_lat);
                i.putExtra("longitude", str_log);
                i.putExtra("ss",ss);
                startActivity(i);
                finish();
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CAMERA_REQUEST) {
            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            Uri uri=data.getData();
            StorageReference filepath=sr.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    //lat.setText("Latitude:-"+location.getLatitude()+"");
                                    //lon.setText("Longitude:-"+location.getLongitude()+"");
                                    lat=location.getLatitude();
                                    lon=location.getLongitude();
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            //lat.setText("Latitude:-"+mLastLocation.getLatitude());
            //lon.setText("Longitude:-"+mLastLocation.getLongitude());
            lat=mLastLocation.getLatitude();
            lon=mLastLocation.getLongitude();
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
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
