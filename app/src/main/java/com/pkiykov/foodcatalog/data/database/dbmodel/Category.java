package com.pkiykov.foodcatalog.data.database.dbmodel;

import com.google.auto.value.AutoValue;
import com.pkiykov.foodcatalog.CategoryModel;

@AutoValue
public abstract class Category implements CategoryModel {
    public static final String CATEGORY_TABLE_NAME = "category";

    public static final Factory<Category> FACTORY = new Factory<>(AutoValue_Category::new);

}
