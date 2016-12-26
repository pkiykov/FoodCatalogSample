package com.pkiykov.foodcatalog.ui.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pkiykov.foodcatalog.data.model.CategoryForXML;

public abstract class CategoriesAbstractViewHolder extends RecyclerView.ViewHolder {

        public CategoriesAbstractViewHolder (View itemView) {
            super(itemView);
        }

        public abstract void bind(CategoryForXML categoryForXML);


}
