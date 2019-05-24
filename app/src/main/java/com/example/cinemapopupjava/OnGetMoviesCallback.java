package com.example.cinemapopupjava;

import java.util.List;

public interface OnGetMoviesCallback {

    void onSuccess(List<Movie> movies);

    void onError();
}
