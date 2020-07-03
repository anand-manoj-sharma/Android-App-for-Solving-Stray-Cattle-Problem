package com.example.stech20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.stech20.Page1.jjj;
import static com.example.stech20.Page1.photo;

public class Main2Activity extends AppCompatActivity {

    Button signal,view,done,show;
    TextView text;
    ImageView img;

    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageRef=storage.getReference();
   // StorageReference storageReference= storage.getReferenceFromUrl("gs://stech2020-13bf5.appspot.com").child("Photos").child("chegg.jpg");

    String add="";
    String sa="";
    long stat=0;
    double lat=0,lon=0;
    int bp=0;
    DatabaseReference reff_pro;

    DatabaseReference newRef;
    DatabaseReference mRef;
    String currentUserID;
    Type pp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        signal=findViewById(R.id.signal);
        view=findViewById(R.id.view);
        done=findViewById(R.id.done);
        img=findViewById(R.id.img);
        show=findViewById(R.id.show);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                img.setImageBitmap(photo);

            }
        });

         FirebaseDatabase database = FirebaseDatabase.getInstance();
          mRef = FirebaseDatabase.getInstance().getReference().child("Professional");


        text=findViewById(R.id.text);

        reff_pro = FirebaseDatabase.getInstance().getReference("Professional");

        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

         currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //DataSnapshot mRef;
        newRef = mRef.child(currentUserID);
        newRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                stat=(long)dataSnapshot.child("status").getValue();


                if(stat==0){

                    Toast.makeText(getApplicationContext(),"no request",Toast.LENGTH_LONG).show();
                }
                else if(stat==1)
                {
                    sa = String.valueOf(dataSnapshot.child("name").getValue());
                    lat = Double.parseDouble(String.valueOf(dataSnapshot.child("ulatitude").getValue()));
                    lon = Double.parseDouble(String.valueOf(dataSnapshot.child("ulongitude").getValue()));

                    Geocoder g = new Geocoder(getApplicationContext(), Locale.getDefault());
                    String mycity = "";
                    try {
                        List<Address> addresses = g.getFromLocation(lat,lon, 1);
                        add = addresses.get(0).getAddressLine(0);
                        mycity = addresses.get(0).getLocality();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    text.setText(add);
                    text.setTextColor(Color.RED);
                    text.setBackgroundColor(Color.YELLOW);
                    signal.setBackgroundColor(Color.RED);
                }
                currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                newRef = mRef.child(currentUserID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ss="CATTLE";
                Intent i = new Intent(getApplicationContext(), MapsActivity2.class);
                i.putExtra("latitude",lat);
                i.putExtra("longitude",lon);
                i.putExtra("ss",ss);
                startActivity(i);
                finish();
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jjj=1;
                MainActivity.stat=0;
                reff_pro.child(currentUserID).child("status").setValue(MainActivity.stat);
                reff_pro.child(currentUserID).child("ulatitude").setValue(0);
                reff_pro.child(currentUserID).child("ulongitude").setValue(0);
                signal.setBackgroundColor(Color.GREEN);
                Toast.makeText(getApplicationContext(),stat+" "+lat,Toast.LENGTH_LONG).show();
            }
        });



    }

    public void onBackPressed(){
        bp = (bp + 1);
        Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();

        if (bp>1) {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }
    }
}
