package com.pkiykov.foodcatalog.ui.presenters;

import android.os.Bundle;

import com.pkiykov.foodcatalog.data.database.dbmodel.Category;
import com.pkiykov.foodcatalog.data.model.CategoryForXML;
import com.pkiykov.foodcatalog.ui.fragments.CategoriesFragment;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action2;
import rx.schedulers.Schedulers;

public class CategoriesFragmentPresenter extends BasePresenter<CategoriesFragment> {

    private static final int GET_CATEGORIES_REQUEST = 1;

    @Inject
    BriteDatabase db;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableReplay(GET_CATEGORIES_REQUEST,
                () -> db.createQuery(Category.CATEGORY_TABLE_NAME, Category.SELECT_ALL)
                        .mapToOne(cursor -> {
                            ArrayList<CategoryForXML> categoryForXMLArrayList = new ArrayList<>();
                            do {
                                CategoryForXML categoryForXML = new CategoryForXML();
                                categoryForXML.setId(cursor.getInt(0));
                                categoryForXML.setText(cursor.getString(1));
                                categoryForXMLArrayList.add(categoryForXML);
                            } while (cursor.moveToNext());
                            return categoryForXMLArrayList;
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                CategoriesFragment::showCategories
        );
    }

    public void request() {
        start(GET_CATEGORIES_REQUEST);
    }

}
