package com.pkiykov.foodcatalog.ui.presenters;

import android.app.Application;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.pkiykov.foodcatalog.R;
import com.pkiykov.foodcatalog.data.database.dbmodel.Offer;
import com.pkiykov.foodcatalog.data.model.OfferForXML;
import com.pkiykov.foodcatalog.ui.fragments.OfferDetailFragment;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OfferDetailFragmentPresenter extends BasePresenter<OfferDetailFragment> {

    private static final int GET_OFFER_DETAIL_REQUEST = 1;

    @Inject
    BriteDatabase db;
    @Inject
    Application application;

    private int offerId;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableFirst(GET_OFFER_DETAIL_REQUEST,
                () -> db.createQuery(Offer.OFFER_TABLE_NAME, Offer.FACTORY.select_offer_by_id(offerId).statement)
                        .mapToOne(OfferDetailFragmentPresenter.this::getOfferWithCursor).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                OfferDetailFragment::showDetailOffer,
                (offerDetailFragment, throwable) -> Toast.makeText(offerDetailFragment.getActivity(), R.string.failed_to_load_catalog, Toast.LENGTH_SHORT).show()
        );
    }

    public void request(int offerId) {
        this.offerId = offerId;
        start(GET_OFFER_DETAIL_REQUEST);
    }

    private OfferForXML getOfferWithCursor(Cursor cursor) {
        Map<String, String> weight = new HashMap<>();
        if (cursor.getString(6) != null) {
            weight.put(application.getString(R.string.weight), cursor.getString(6));
        }
        OfferForXML offerForXML = new OfferForXML();
        offerForXML.setId(cursor.getInt(0));
        offerForXML.setName(cursor.getString(1));
        offerForXML.setDescription(cursor.getString(2));
        offerForXML.setPictureUrl(cursor.getString(3));
        offerForXML.setPrice(cursor.getDouble(4));
        offerForXML.setCategoryId(cursor.getInt(5));
        offerForXML.setParameters(weight);
        return offerForXML;
    }
}
