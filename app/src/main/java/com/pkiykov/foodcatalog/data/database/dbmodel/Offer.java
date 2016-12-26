package com.pkiykov.foodcatalog.data.database.dbmodel;

import com.google.auto.value.AutoValue;
import com.pkiykov.foodcatalog.OfferModel;

@AutoValue
public abstract class Offer implements OfferModel {
    public static final String OFFER_TABLE_NAME = "offer";

    public static final Factory<Offer> FACTORY = new Factory<>(AutoValue_Offer::new);

}
