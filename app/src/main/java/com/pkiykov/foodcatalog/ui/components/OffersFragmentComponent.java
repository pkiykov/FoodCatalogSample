package com.pkiykov.foodcatalog.ui.components;

import com.pkiykov.foodcatalog.ui.fragments.OffersFragment;
import com.pkiykov.foodcatalog.ui.fragments.FragmentsScope;
import com.pkiykov.foodcatalog.ui.modules.OffersFragmentModule;

import dagger.Subcomponent;

@FragmentsScope
@Subcomponent(
        modules = OffersFragmentModule.class
)
public interface OffersFragmentComponent {
   OffersFragment inject(OffersFragment offersFragment);
}
