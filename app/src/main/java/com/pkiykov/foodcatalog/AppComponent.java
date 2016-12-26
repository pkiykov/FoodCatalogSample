package com.pkiykov.foodcatalog;

import com.pkiykov.foodcatalog.data.api.FoodCatalogModule;
import com.pkiykov.foodcatalog.data.api.UtilsModule;
import com.pkiykov.foodcatalog.data.database.DbModule;
import com.pkiykov.foodcatalog.ui.adapters.viewholder.OffersViewHolder;
import com.pkiykov.foodcatalog.ui.components.CategoriesFragmentComponent;
import com.pkiykov.foodcatalog.ui.components.MainActivityComponent;
import com.pkiykov.foodcatalog.ui.components.OffersFragmentComponent;
import com.pkiykov.foodcatalog.ui.fragments.MapFragment;
import com.pkiykov.foodcatalog.ui.fragments.OfferDetailFragment;
import com.pkiykov.foodcatalog.ui.modules.CategoriesFragmentModule;
import com.pkiykov.foodcatalog.ui.modules.MainActivityModule;
import com.pkiykov.foodcatalog.ui.modules.OffersFragmentModule;
import com.pkiykov.foodcatalog.ui.presenters.CategoriesFragmentPresenter;
import com.pkiykov.foodcatalog.ui.presenters.MainActivityPresenter;
import com.pkiykov.foodcatalog.ui.presenters.OfferDetailFragmentPresenter;
import com.pkiykov.foodcatalog.ui.presenters.OffersFragmentPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,
        FoodCatalogModule.class,
        UtilsModule.class,
        DbModule.class})

public interface AppComponent {

    void inject(MainActivityPresenter mainActivityPresenter);

    void inject(CategoriesFragmentPresenter categoriesFragmentPresenter);

    void inject(OfferDetailFragmentPresenter OfferDetailFragmentPresenter);

    void inject(OffersFragmentPresenter offersFragmentPresenter);

    void inject(OffersViewHolder offersViewHolder);

    void inject(OfferDetailFragment offerDetailFragment);

    void inject(MapFragment mapFragment);

    OffersFragmentComponent plus(OffersFragmentModule module);

    CategoriesFragmentComponent plus(CategoriesFragmentModule module);

    MainActivityComponent plus(MainActivityModule mainActivityModule);

}
