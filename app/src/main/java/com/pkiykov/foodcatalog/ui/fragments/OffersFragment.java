package com.pkiykov.foodcatalog.ui.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pkiykov.foodcatalog.FoodCatalogApplication;
import com.pkiykov.foodcatalog.R;
import com.pkiykov.foodcatalog.data.model.OfferForXML;
import com.pkiykov.foodcatalog.ui.MainActivity;
import com.pkiykov.foodcatalog.ui.adapters.OffersListAdapter;
import com.pkiykov.foodcatalog.ui.modules.OffersFragmentModule;
import com.pkiykov.foodcatalog.ui.presenters.OffersFragmentPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import icepick.Icepick;
import icepick.State;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(OffersFragmentPresenter.class)
public class OffersFragment extends BaseFragment<OffersFragmentPresenter> {

    public static final String SELECTED_POSITION = "selected_position";

    @Inject
    OffersListAdapter offersListAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @State
    Parcelable state;

    public static OffersFragment getInstance(int position) {
        OffersFragment myFragment = new OffersFragment();
        Bundle args = new Bundle();
        args.putInt(SELECTED_POSITION, position);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        FoodCatalogApplication.get(getActivity())
                .getAppComponent()
                .plus(new OffersFragmentModule(this))
                .inject(this);
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setAdapter(offersListAdapter);
        getPresenter().request(getArguments().getInt(SELECTED_POSITION));
    }

    public void onOfferClick(int offerId) {
        ((MainActivity) getActivity()).startFragment(OfferDetailFragment.getInstance(offerId));
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        if (recyclerView != null) {
            super.onSaveInstanceState(bundle);
            state = recyclerView.getLayoutManager().onSaveInstanceState();
            Icepick.saveInstanceState(this, bundle);
        }
    }

    public void showOffers(ArrayList<OfferForXML> selectedOfferForXMLs) {
        offersListAdapter.updateList(selectedOfferForXMLs);
        if (state != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(state);
        }
    }

}
