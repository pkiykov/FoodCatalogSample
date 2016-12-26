package com.pkiykov.foodcatalog.ui.modules;

import com.pkiykov.foodcatalog.ui.adapters.OffersListAdapter;
import com.pkiykov.foodcatalog.ui.adapters.viewholder.ListViewHolderFactory;
import com.pkiykov.foodcatalog.ui.adapters.viewholder.OffersViewHolderFactory;
import com.pkiykov.foodcatalog.ui.fragments.FragmentsScope;
import com.pkiykov.foodcatalog.ui.fragments.OffersFragment;

import java.util.Map;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;

@Module
public class OffersFragmentModule {

    private OffersFragment offersFragment;

    public OffersFragmentModule(OffersFragment offersFragment) {
        this.offersFragment = offersFragment;
    }

    @Provides
    @FragmentsScope
    OffersListAdapter provideOffersListAdapter(Map<Integer, ListViewHolderFactory> viewHolderFactories) {
        return new OffersListAdapter(offersFragment, viewHolderFactories);
    }

    @Provides
    @IntoMap
    @IntKey(OffersListAdapter.TYPE_OFFER)
    ListViewHolderFactory provideViewHolderOffer() {
        return new OffersViewHolderFactory();
    }

}
