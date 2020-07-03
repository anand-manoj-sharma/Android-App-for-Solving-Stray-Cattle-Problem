package com.example.stech20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.stech20.Page1.jjj;

public class Main3Activity extends AppCompatActivity {

    TextView tv;
    int bp=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tv=findViewById(R.id.lc);

        if(jjj==0)
        {
            tv.setText("No Complaint Registered");
        }
        else if(jjj==1)
        {
            tv.setText("Complaint in Process");
        }
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
