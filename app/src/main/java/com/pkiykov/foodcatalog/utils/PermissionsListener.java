package com.pkiykov.foodcatalog.utils;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.pkiykov.foodcatalog.ui.MainActivity;

import java.util.List;

public class PermissionsListener implements MultiplePermissionsListener {

    private final MainActivity mainActivity;

    public PermissionsListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
       mainActivity.loadMapFragment();
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                   PermissionToken token) {
        mainActivity.showPermissionRationale(token);
    }
}

