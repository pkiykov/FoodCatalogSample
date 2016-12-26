package com.pkiykov.foodcatalog.ui.components;

import com.pkiykov.foodcatalog.ui.ActivityScope;
import com.pkiykov.foodcatalog.ui.MainActivity;
import com.pkiykov.foodcatalog.ui.modules.MainActivityModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = MainActivityModule.class
)
public interface MainActivityComponent {

   MainActivity inject(MainActivity mainActivity);

}
