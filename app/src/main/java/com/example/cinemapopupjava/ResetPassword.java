package com.example.cinemapopupjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cinemapopupjava.Auth.ConexaoFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText txtEmail;
    private Button btnEnviar, btnVoltar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        initComponentes();
        clickEvent();
    }

    private void clickEvent() {
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String empty = "";
                String email = txtEmail.getText().toString().trim();
                if (((email.equals(empty) || (email == null)))) {
                    alert("É necessário informar o e-mail!");
                } else {
                    resetSenha(email);
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ResetPassword.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void resetSenha(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(ResetPassword.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            alert("Um e-mail foi enviado para alterar sua senha!");
                            finish();
                        } else {
                            alert("O e-mail informado não foi encontrado!");
                        }
                    }
                });
    }

    private void alert(String s) {
        Toast.makeText(ResetPassword.this, s, Toast.LENGTH_SHORT).show();
    }

    private void initComponentes() {
        txtEmail = (EditText) findViewById(R.id.editResetEmail);
        btnEnviar = (Button) findViewById(R.id.btnResetPass);
        btnVoltar = (Button) findViewById(R.id.btnVoltarLogin);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = ConexaoFirebase.getFirebaseAuth();
    }
}
