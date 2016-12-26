package com.pkiykov.foodcatalog.ui.modules;

import com.pkiykov.foodcatalog.ui.adapters.CategoriesListAdapter;
import com.pkiykov.foodcatalog.ui.adapters.viewholder.CategoriesViewHolderFactory;
import com.pkiykov.foodcatalog.ui.adapters.viewholder.ListViewHolderFactory;
import com.pkiykov.foodcatalog.ui.fragments.CategoriesFragment;
import com.pkiykov.foodcatalog.ui.fragments.FragmentsScope;

import java.util.Map;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;

@Module
public class CategoriesFragmentModule {

    private CategoriesFragment categoriesFragment;

    public CategoriesFragmentModule(CategoriesFragment categoriesFragment) {
        this.categoriesFragment = categoriesFragment;
    }

    @Provides
    @FragmentsScope
    CategoriesListAdapter provideCategoriesListAdapter(Map<Integer, ListViewHolderFactory> viewHolderFactories) {
        return new CategoriesListAdapter(categoriesFragment, viewHolderFactories);
    }

    @Provides
    @IntoMap
    @IntKey(CategoriesListAdapter.TYPE_CATEGORY)
    ListViewHolderFactory provideViewHolderCategory() {
        return new CategoriesViewHolderFactory();
    }

}
