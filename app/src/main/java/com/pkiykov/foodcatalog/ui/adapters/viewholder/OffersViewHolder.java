package com.pkiykov.foodcatalog.ui.adapters.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.auto.factory.AutoFactory;
import com.pkiykov.foodcatalog.FoodCatalogApplication;
import com.pkiykov.foodcatalog.R;
import com.pkiykov.foodcatalog.data.model.OfferForXML;
import com.pkiykov.foodcatalog.utils.ImageHandler;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

@AutoFactory(implementing = ListViewHolderFactory.class)
public class OffersViewHolder extends OffersAbstractViewHolder {

    @BindView(R.id.dish_image_view)
    ImageView dishImageView;
    @BindView(R.id.dish_title_tv)
    TextView dishTitleTv;
    @BindView(R.id.weight_tv)
    TextView weightTv;
    @BindView(R.id.price_tv)
    TextView priceTv;
    @BindString(R.string.weight)
    String key;

    @Inject
    ImageHandler imageHandler;
    private Context context;

    public OffersViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false));
        context = parent.getContext();
        FoodCatalogApplication.get(context).getAppComponent().inject(this);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void bind(OfferForXML offerForXML) {
        loadImage(offerForXML.getPictureUrl());
        String title = offerForXML.getName();
        String price = offerForXML.getPrice() + context.getString(R.string.currency);
        if (offerForXML.getParameters() != null) {
            String weight = offerForXML.getParameters().get(key);
            weightTv.setText(weight);
        }
        dishTitleTv.setText(title);
        priceTv.setText(price);
    }

    private void loadImage(String url) {
        imageHandler.get().load(url)
                .error(R.drawable.empty_dish)
                .into(dishImageView);
    }
}
