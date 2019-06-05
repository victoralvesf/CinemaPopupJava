package com.example.cinemapopupjava;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cinemapopupjava.Auth.ConexaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtEmail;
    private Button btnLogout, btnVoltar;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initComponents();
        eventClick();
    }

    private void eventClick() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexaoFirebase.logOut();
                SharedPreferences prefs = getSharedPreferences("preferenceFile", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isLogged", false);
                editor.commit();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initComponents() {
        txtEmail = (TextView) findViewById(R.id.userEmail);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = ConexaoFirebase.getFirebaseAuth();
        user = ConexaoFirebase.getFirebaseUser();
        verifyUser();
    }

    private void verifyUser() {
        if (user == null) {
            finish();
        } else {
            txtEmail.setText(user.getEmail());
        }
    }
}
