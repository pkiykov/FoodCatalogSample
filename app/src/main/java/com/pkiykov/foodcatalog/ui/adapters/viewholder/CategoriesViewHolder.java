package com.pkiykov.foodcatalog.ui.adapters.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.auto.factory.AutoFactory;
import com.pkiykov.foodcatalog.R;
import com.pkiykov.foodcatalog.data.model.CategoryForXML;

import butterknife.BindView;
import butterknife.ButterKnife;

@AutoFactory(implementing = ListViewHolderFactory.class)
public class CategoriesViewHolder extends CategoriesAbstractViewHolder {

    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.title_tv)
    TextView titleTv;

    public CategoriesViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false));
        ButterKnife.bind(this, itemView);

    }

    @Override
    public void bind(CategoryForXML categoryForXML) {
        int id = categoryForXML.getId();
        String title = categoryForXML.getText();
        titleTv.setText(title);
        imageView.setImageResource(categoryForXML.getIconId(id));
    }
}
