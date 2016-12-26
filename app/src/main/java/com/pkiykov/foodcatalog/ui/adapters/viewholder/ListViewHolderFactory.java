package com.pkiykov.foodcatalog.ui.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface ListViewHolderFactory {
    RecyclerView.ViewHolder createViewHolder(ViewGroup parent);
}
