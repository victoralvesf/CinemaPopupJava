package com.example.cinemapopupjava;

public interface OnGetMovieCallback {

    void onSuccess(Movie movie);

    void onError();
}
