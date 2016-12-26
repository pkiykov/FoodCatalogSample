package com.pkiykov.foodcatalog.ui.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pkiykov.foodcatalog.data.model.OfferForXML;

public abstract class OffersAbstractViewHolder extends RecyclerView.ViewHolder {

    public OffersAbstractViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(OfferForXML offerForXML);
}
