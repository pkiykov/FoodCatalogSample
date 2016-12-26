package com.pkiykov.foodcatalog.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.pkiykov.foodcatalog.data.model.CategoryForXML;
import com.pkiykov.foodcatalog.ui.adapters.viewholder.CategoriesAbstractViewHolder;
import com.pkiykov.foodcatalog.ui.adapters.viewholder.ListViewHolderFactory;
import com.pkiykov.foodcatalog.ui.fragments.CategoriesFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CategoriesListAdapter extends RecyclerView.Adapter{

    private CategoriesFragment categoriesFragment;
    private Map<Integer, ListViewHolderFactory> viewHolderFactories;
    public static final int TYPE_CATEGORY = 1;

    private final List<CategoryForXML> categories = new ArrayList<>();

    public CategoriesListAdapter(CategoriesFragment categoriesFragment, Map<Integer, ListViewHolderFactory> viewHolderFactories) {
        this.categoriesFragment = categoriesFragment;
        this.viewHolderFactories = viewHolderFactories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder viewHolder = viewHolderFactories.get(viewType).createViewHolder(parent);
        viewHolder.itemView.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            categoriesFragment.onCategoryClick(categories.get(position).getId());
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CategoriesAbstractViewHolder) holder).bind(categories.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_CATEGORY;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void updateList(List<CategoryForXML> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }
}
