package com.example.cinemapopupjava.Auth;


import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConexaoFirebase {

    private static FirebaseAuth firebaseAuth;

    private static FirebaseAuth.AuthStateListener authStateListener;

    private static FirebaseUser firebaseUser;

    public ConexaoFirebase() {
    }

    public static FirebaseAuth getFirebaseAuth(){
        if (firebaseAuth == null){
            initFirebaseAuth();
        }
        return firebaseAuth;
    }

    private static void initFirebaseAuth() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    firebaseUser = user;
                }
            }
        };

        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public static FirebaseUser getFirebaseUser(){
        return firebaseUser;
    }

    public static void logOut(){
        firebaseAuth.signOut();
    }

}
