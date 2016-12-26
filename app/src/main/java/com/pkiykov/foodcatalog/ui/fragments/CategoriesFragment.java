package com.pkiykov.foodcatalog.ui.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pkiykov.foodcatalog.FoodCatalogApplication;
import com.pkiykov.foodcatalog.R;
import com.pkiykov.foodcatalog.data.model.CategoryForXML;
import com.pkiykov.foodcatalog.ui.MainActivity;
import com.pkiykov.foodcatalog.ui.adapters.CategoriesListAdapter;
import com.pkiykov.foodcatalog.ui.modules.CategoriesFragmentModule;
import com.pkiykov.foodcatalog.ui.presenters.CategoriesFragmentPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import icepick.Icepick;
import icepick.State;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(CategoriesFragmentPresenter.class)
public class CategoriesFragment extends BaseFragment<CategoriesFragmentPresenter> {

    @Inject
    CategoriesListAdapter categoriesListAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @State
    Parcelable state;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setUpComponent();
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    public void onError(){
        Toast.makeText(getActivity(), R.string.database_not_exist, Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        if (recyclerView != null) {
            super.onSaveInstanceState(bundle);
            state = recyclerView.getLayoutManager().onSaveInstanceState();
            Icepick.saveInstanceState(this, bundle);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setAdapter(categoriesListAdapter);
      //  if(savedInstanceState == null)
        getPresenter().request();
    }

    public static CategoriesFragment getInstance() {
        return new CategoriesFragment();
    }

    public void showCategories(ArrayList<CategoryForXML> categories) {
        categoriesListAdapter.updateList(categories);
        if (state != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(state);
        }
    }

    public void onCategoryClick(int categoryId) {
        ((MainActivity) getActivity()).startFragment(OffersFragment.getInstance(categoryId));
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).changeCatalogVisibility(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).changeCatalogVisibility(true);
    }

    protected void setUpComponent() {
        FoodCatalogApplication.get(getActivity())
                .getAppComponent()
                .plus(new CategoriesFragmentModule(this))
                .inject(this);
    }
}
