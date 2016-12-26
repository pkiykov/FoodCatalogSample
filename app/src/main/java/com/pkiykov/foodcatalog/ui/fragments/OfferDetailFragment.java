package com.pkiykov.foodcatalog.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkiykov.foodcatalog.FoodCatalogApplication;
import com.pkiykov.foodcatalog.R;
import com.pkiykov.foodcatalog.data.model.OfferForXML;
import com.pkiykov.foodcatalog.ui.presenters.OfferDetailFragmentPresenter;
import com.pkiykov.foodcatalog.utils.ImageHandler;

import javax.inject.Inject;

import butterknife.BindView;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(OfferDetailFragmentPresenter.class)
public class OfferDetailFragment extends BaseFragment<OfferDetailFragmentPresenter> {

    public static final String SELECTED_OFFER_ID = "selected_offer_id";

    @BindView(R.id.dish_image_view)
    ImageView dishImage;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.weight_tv)
    TextView weightTv;
    @BindView(R.id.description_tv)
    TextView descriptionTv;
    @BindView(R.id.price_tv)
    TextView priceTv;

    @Inject
    ImageHandler imageHandler;


    public static OfferDetailFragment getInstance(int offerId) {
        OfferDetailFragment myFragment = new OfferDetailFragment();
        Bundle args = new Bundle();
        args.putInt(SELECTED_OFFER_ID, offerId);
        myFragment.setArguments(args);
        return myFragment;
    }


    @Override
    public void onCreate(Bundle bundle) {
        FoodCatalogApplication.get(getActivity()).getAppComponent().inject(this);
        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dish_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPresenter().request(getArguments().getInt(SELECTED_OFFER_ID));
    }

    public void showDetailOffer(OfferForXML offer) {
        titleTv.setText(offer.getName());
        descriptionTv.setText(offer.getDescription());
        String price = offer.getPrice() + getResources().getString(R.string.currency);
        priceTv.setText(price);
        imageHandler.get()
                .load(offer.getPictureUrl())
                .error(R.drawable.empty_dish)
                .into(dishImage);
    }
}
