package com.pkiykov.foodcatalog.ui.modules;


import android.widget.ArrayAdapter;

import com.pkiykov.foodcatalog.R;
import com.pkiykov.foodcatalog.ui.ActivityScope;
import com.pkiykov.foodcatalog.ui.MainActivity;
import com.pkiykov.foodcatalog.utils.PermissionsListener;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    private MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    @ActivityScope
    PermissionsListener providePermissionListener() {
        return new PermissionsListener(mainActivity);
    }

    @Provides
    @ActivityScope
    ArrayAdapter<String> provideArrayAdapter() {
        return new ArrayAdapter<>(mainActivity,
                R.layout.drawer_list_item,
                mainActivity.getResources().getStringArray(R.array.fragments_drawer));
    }

}
