package com.pkiykov.foodcatalog.data.model;

import org.simpleframework.xml.ElementList;

import java.util.Collection;

public class Shop {

    private Collection<CategoryForXML> categories;
    private Collection<OfferForXML> offers;

    @ElementList
    public void setCategories(Collection<CategoryForXML> categories) {
        if (categories.isEmpty()) {
            throw new IllegalArgumentException("Empty collection");
        }
        this.categories = categories;
    }

    @ElementList
    public void setOffers(Collection<OfferForXML> offers) {
        if (offers.isEmpty()) {
            throw new IllegalArgumentException("Empty collection");
        }
        this.offers = offers;
    }

    @ElementList
    public Collection<CategoryForXML> getCategories() {
        return categories;
    }

    @ElementList
    public Collection<OfferForXML> getOffers() {
        return offers;
    }
}
