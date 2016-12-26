package com.pkiykov.foodcatalog.data.api;

import android.app.Application;

import com.pkiykov.foodcatalog.utils.ImageHandler;
import com.pkiykov.foodcatalog.utils.InternetConnection;
import com.pkiykov.foodcatalog.utils.MapManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides
    @Singleton
    MapManager provideMapManager(Application application){
     return new MapManager(application);
    }

    @Provides
    @Singleton
    ImageHandler provideImageHandler(Application application){
        return new ImageHandler (application);
    }

    @Provides
    @Singleton
    InternetConnection provideInternetConnection(Application application){
        return new InternetConnection(application);
    }

}
