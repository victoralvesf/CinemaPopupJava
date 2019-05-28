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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Cadastro extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnCadastro, btnVoltar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        initComponentes();
        eventClick();
    }

    private void eventClick() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                String senha = editSenha.getText().toString().trim();
                criarUsuario(email, senha);
            }
        });
    }

    private void criarUsuario (String email, String senha) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    alerta("Cadastro efetuado com sucesso");
                    Intent intent = new Intent(Cadastro.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    alerta("Erro ao criar usuário");
                }
            }
        });
    }

    private void alerta(String msg){
        Toast.makeText(Cadastro.this,msg, Toast.LENGTH_SHORT).show();
    }

    private void initComponentes() {
        editEmail = (EditText) findViewById(R.id.editCadEmail);
        editSenha = (EditText) findViewById(R.id.editCadSenha);
        btnCadastro = (Button) findViewById(R.id.btnNewCadastro);
        btnVoltar = (Button) findViewById(R.id.btnVoltarLogin);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = ConexaoFirebase.getFirebaseAuth();
    }
}
