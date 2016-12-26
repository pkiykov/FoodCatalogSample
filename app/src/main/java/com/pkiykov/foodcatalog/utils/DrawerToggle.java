package com.pkiykov.foodcatalog.utils;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import com.pkiykov.foodcatalog.ui.MainActivity;

public class DrawerToggle extends ActionBarDrawerToggle {

    private MainActivity mainActivity;

    public DrawerToggle(MainActivity mainActivity, DrawerLayout drawerLayout, @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
        super(mainActivity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.mainActivity = mainActivity;
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        mainActivity.invalidateOptionsMenu();
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        mainActivity.invalidateOptionsMenu();
    }



}
