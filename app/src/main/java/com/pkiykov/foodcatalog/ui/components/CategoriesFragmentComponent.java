package com.pkiykov.foodcatalog.ui.components;

import com.pkiykov.foodcatalog.ui.fragments.CategoriesFragment;
import com.pkiykov.foodcatalog.ui.fragments.FragmentsScope;
import com.pkiykov.foodcatalog.ui.modules.CategoriesFragmentModule;


import dagger.Subcomponent;

@FragmentsScope
@Subcomponent(
        modules = CategoriesFragmentModule.class
)

public interface CategoriesFragmentComponent {
    CategoriesFragment inject(CategoriesFragment categoriesFragment);

}
