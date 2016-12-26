package com.pkiykov.foodcatalog.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.pkiykov.foodcatalog.data.model.OfferForXML;
import com.pkiykov.foodcatalog.ui.adapters.viewholder.ListViewHolderFactory;
import com.pkiykov.foodcatalog.ui.adapters.viewholder.OffersAbstractViewHolder;
import com.pkiykov.foodcatalog.ui.fragments.OffersFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OffersListAdapter extends RecyclerView.Adapter {
    private OffersFragment offersFragment;
    private Map<Integer, ListViewHolderFactory> viewHolderFactories;
    public static final int TYPE_OFFER = 0;

    private final List<OfferForXML> offerForXMLs = new ArrayList<>();

    public OffersListAdapter(OffersFragment offersFragment, Map<Integer, ListViewHolderFactory> viewHolderFactories) {
        this.offersFragment = offersFragment;
        this.viewHolderFactories = viewHolderFactories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder viewHolder = viewHolderFactories.get(viewType).createViewHolder(parent);
        viewHolder.itemView.setOnClickListener(view ->{
            int position = viewHolder.getAdapterPosition();
            int offerId = offerForXMLs.get(position).getId();
            offersFragment.onOfferClick(offerId);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((OffersAbstractViewHolder) holder).bind(offerForXMLs.get(position));
    }

    @Override
    public int getItemCount() {
        return offerForXMLs.size();
    }

    public void updateList(List<OfferForXML> offerForXMLs) {
        this.offerForXMLs.clear();
        this.offerForXMLs.addAll(offerForXMLs);
        notifyDataSetChanged();
    }

}
