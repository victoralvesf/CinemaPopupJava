package com.example.cinemapopupjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnLogin, btnCadastro;
    private TextView txtResetSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponentes();
        eventClick();
    }

    private void eventClick() {
        btnCadastro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Cadastro.class);
                startActivity(intent);
            }
        });
    }

    private void initComponentes(){
        editEmail = (EditText) findViewById(R.id.editLoginEmail);
        editSenha = (EditText) findViewById(R.id.editLoginSenha);
        btnLogin = (Button) findViewById(R.id.btnLoginEntrar);
        btnCadastro = (Button) findViewById(R.id.btnLoginCadastro);
    }
}

