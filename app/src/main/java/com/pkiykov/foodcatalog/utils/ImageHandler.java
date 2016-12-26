package com.pkiykov.foodcatalog.utils;


import android.app.Application;

import com.squareup.picasso.Picasso;

import java.util.concurrent.Executors;

public class ImageHandler {

    private Picasso instance;

    public ImageHandler(Application application) {
        this.instance = new Picasso.Builder(application)
                .executor(Executors.newSingleThreadExecutor())
                .indicatorsEnabled(true)
                .build();
    }

    public Picasso get() {
        return instance;
    }
}
