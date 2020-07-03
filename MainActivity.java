package com.example.stech20;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static long stat=0;
    EditText username,password;
    Button login;
    TextView signtext;
    String email,pass;
    String admin_email="anand@gmail.com";
    String admin_pass="123456";
    DatabaseReference reff_pro,reff_nor;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.log);
        signtext=findViewById(R.id.signtext);

        //reff_admin=FirebaseDatabase.getInstance().getReference("Admin");
        reff_pro= FirebaseDatabase.getInstance().getReference("Professional");
        reff_nor= FirebaseDatabase.getInstance().getReference("Normal");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogin();

                }
        });
        signtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Signup.class);
                startActivity(i);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();

    }

    private void userlogin()
    {
         email=username.getText().toString().trim();
         pass=password.getText().toString().trim();

        if(email.isEmpty())
        {
            username.setError("Email is Required");
            username.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            username.setError("Please enter a valid Email");
            username.requestFocus();
            return;
        }

        if(pass.isEmpty())
        {
            password.setError("Password is Required");
            password.requestFocus();
            return;
        }

        if(pass.length()<6)
        {
            password.setError("Minimum length of password shaould be 6");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    if(email.equals(admin_email) && pass.equals(admin_pass))
                    {
                        Intent i=new Intent(getApplicationContext(),Admin.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        reff_nor.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int flag = 0;
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    Type norInfo = data.getValue(Type.class);
                                    if (email.equals(norInfo.getEmail())) {
                                        flag = 1;
                                        break;
                                    }
                                }
                                if (flag == 1) {
                                    Intent i = new Intent(getApplicationContext(), Page1.class);
                                    startActivity(i);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        reff_pro.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int flag = 0;
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    Type proInfo = data.getValue(Type.class);
                                    if (email.equals(proInfo.getEmail())) {
                                        flag = 1;
                                        break;
                                    }
                                }
                                if (flag == 1) {
                                    Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

 }

