package com.pkiykov.foodcatalog.ui;

import android.os.Bundle;

import com.pkiykov.foodcatalog.FoodCatalogApplication;
import com.pkiykov.foodcatalog.ui.presenters.MainActivityPresenter;

import nucleus.factory.PresenterFactory;
import nucleus.view.NucleusAppCompatActivity;

public class BaseActivity extends NucleusAppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final PresenterFactory<MainActivityPresenter> superFactory = getPresenterFactory();
        setPresenterFactory(superFactory == null ? null : (PresenterFactory<MainActivityPresenter>) () -> {
            MainActivityPresenter presenter = superFactory.createPresenter();
            FoodCatalogApplication.get(this).getAppComponent().inject(presenter);
            return presenter;
        });
    }
}
