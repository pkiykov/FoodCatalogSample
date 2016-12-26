package com.pkiykov.foodcatalog;

import android.app.Application;
import android.content.Context;

import com.karumi.dexter.Dexter;
import com.pkiykov.foodcatalog.data.api.FoodCatalogModule;
import com.pkiykov.foodcatalog.data.api.UtilsModule;
import com.pkiykov.foodcatalog.ui.MainActivity;
import com.pkiykov.foodcatalog.ui.components.MainActivityComponent;
import com.pkiykov.foodcatalog.ui.modules.MainActivityModule;
import com.pkiykov.foodcatalog.utils.ComponentReflectionInjector;
import com.pkiykov.foodcatalog.utils.Injector;


public class FoodCatalogApplication extends Application implements Injector {

    private ComponentReflectionInjector<AppComponent> injector;
    private MainActivityComponent mainActivityComponent;
    private AppComponent appComponent;

    public static FoodCatalogApplication get(Context context) {
        return (FoodCatalogApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Dexter.initialize(this);
        initAppComponent();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .foodCatalogModule(new FoodCatalogModule())
                .utilsModule(new UtilsModule())
                .build();
        injector = new ComponentReflectionInjector<>(AppComponent.class, appComponent);
    }

    public MainActivityComponent createMainActivityComponent(MainActivity mainActivity) {
        mainActivityComponent = appComponent.plus(new MainActivityModule(mainActivity));
        return mainActivityComponent;
    }

    public void releaseMainActivityComponent(){
        mainActivityComponent = null;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void inject(Object target) {
        injector.inject(target);
    }
}
