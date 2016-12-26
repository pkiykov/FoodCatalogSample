package com.pkiykov.foodcatalog.ui.presenters;

import android.app.Application;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.pkiykov.foodcatalog.R;
import com.pkiykov.foodcatalog.data.database.dbmodel.Offer;
import com.pkiykov.foodcatalog.data.model.OfferForXML;
import com.pkiykov.foodcatalog.ui.fragments.OffersFragment;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OffersFragmentPresenter extends BasePresenter<OffersFragment> {

    private static final int GET_OFFERS_REQUEST = 1;

    @Inject
    BriteDatabase db;
    @Inject
    Application application;

    private int category;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableReplay(GET_OFFERS_REQUEST,
                () -> db.createQuery(Offer.OFFER_TABLE_NAME, Offer.FACTORY.select_offer_by_category(category).statement)
                        .mapToOne(cursor -> {
                            ArrayList<OfferForXML> offerForXMLArrayList = new ArrayList<>();
                            do {
                                offerForXMLArrayList.add(OffersFragmentPresenter.this.getOfferWithCursor(cursor));
                            } while (cursor.moveToNext());
                            return offerForXMLArrayList;
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                OffersFragment::showOffers,
                (categoriesFragment, throwable) -> Toast.makeText(categoriesFragment.getActivity(), R.string.failed_to_load_catalog, Toast.LENGTH_SHORT).show()
        );
    }

    public void request(int category) {
        this.category = category;
        start(GET_OFFERS_REQUEST);
    }

    private OfferForXML getOfferWithCursor(Cursor cursor) {
        Map<String, String> weight = new HashMap<>();
        if (cursor.getString(6) != null) {
            weight.put(application.getString(R.string.weight), cursor.getString(6));
        }
        OfferForXML offerForXML = new OfferForXML();
        offerForXML.setId(cursor.getInt(0));
        offerForXML.setName(cursor.getString(1));
        offerForXML.setPictureUrl(cursor.getString(3));
        offerForXML.setPrice(cursor.getDouble(4));
        offerForXML.setCategoryId(cursor.getInt(5));
        offerForXML.setParameters(weight);
        return offerForXML;
    }
}
