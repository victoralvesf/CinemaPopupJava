package com.example.cinemapopupjava;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemapopupjava.Auth.ConexaoFirebase;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnLogin, btnCadastro;
    private TextView btnResetSenha;
    private LoginButton btnLoginFacebook;
    private CallbackManager newCallbackManager;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponentes();
        initFirebaseCallback();
        eventClick();
    }

    private void initFirebaseCallback() {
        auth = FirebaseAuth.getInstance();
        newCallbackManager = CallbackManager.Factory.create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        newCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void eventClick() {
        btnCadastro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Cadastro.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String empty = "";
                String email = editEmail.getText().toString().trim();
                String senha = editSenha.getText().toString().trim();
                if (((email.equals(empty) || senha.equals(empty)) || (email == null) || senha == null)) {
                    alert("É necessário informar e-mail e senha!");
                } else {
                    login(email, senha);
                }
            }
        });

        btnResetSenha.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , ResetPassword.class);
                startActivity(intent);
            }
        });

        btnLoginFacebook.registerCallback(newCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                firebaseLogin(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                alert("Operação cancelada!");
            }

            @Override
            public void onError(FacebookException error) {
                alert("Erro ao efetuar o login com o Facebook!");
            }
        });
    }

    private void firebaseLogin(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());

        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        alert("Erro de autenticação");
                    }
                }
        });
    }

    private void login(String email, String senha) {
        auth.signInWithEmailAndPassword(email,senha)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        SharedPreferences prefs = getSharedPreferences("preferenceFile", 0);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("isLogged", true);
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        alert("e-mail ou senha inválidos!");
                    }
                }
        });
    }

    private void alert(String s) {
        Toast.makeText(LoginActivity.this,s,Toast.LENGTH_SHORT).show();
    }

    private void initComponentes(){
        editEmail = (EditText) findViewById(R.id.editLoginEmail);
        editSenha = (EditText) findViewById(R.id.editLoginSenha);
        btnLogin = (Button) findViewById(R.id.btnLoginEntrar);
        btnCadastro = (Button) findViewById(R.id.btnLoginCadastro);
        btnResetSenha = (TextView) findViewById(R.id.txtResetSenha);
        btnLoginFacebook = (LoginButton) findViewById(R.id.login_facebook_button);
        btnLoginFacebook.setPermissions("email","public_profile");
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = ConexaoFirebase.getFirebaseAuth();
    }
}

