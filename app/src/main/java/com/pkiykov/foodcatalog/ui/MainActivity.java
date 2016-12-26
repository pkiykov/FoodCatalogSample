package com.pkiykov.foodcatalog.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.pkiykov.foodcatalog.FoodCatalogApplication;
import com.pkiykov.foodcatalog.R;
import com.pkiykov.foodcatalog.ui.fragments.CategoriesFragment;
import com.pkiykov.foodcatalog.ui.fragments.MapFragment;
import com.pkiykov.foodcatalog.ui.presenters.MainActivityPresenter;
import com.pkiykov.foodcatalog.utils.DrawerToggle;
import com.pkiykov.foodcatalog.utils.PermissionsListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import nucleus.factory.PresenterFactory;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;


@RequiresPresenter(MainActivityPresenter.class)
public class MainActivity extends NucleusAppCompatActivity<MainActivityPresenter> {

    @Inject
    PermissionsListener permissionsListener;
    @Inject
    ArrayAdapter<String> drawerArrayAdapter;

    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;
    @BindView(R.id.left_drawer)
    ListView listView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private DrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent();
        setUpPresenter();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle(R.string.company_title);
        Dexter.continuePendingRequestsIfPossible(permissionsListener);
        setUpDrawer();
        if (getFragmentManager().getBackStackEntryCount() == 0)
            showLoading(true);
        if (savedInstanceState == null)
            getPresenter().request();
    }

    private void setUpPresenter() {
        final PresenterFactory<MainActivityPresenter> superFactory = super.getPresenterFactory();
        setPresenterFactory(() -> {
            MainActivityPresenter presenter = superFactory.createPresenter();
            FoodCatalogApplication.get(this).getAppComponent().inject(presenter);
            return presenter;
        });
    }

    private void setupComponent() {
        FoodCatalogApplication.get(this)
                .createMainActivityComponent(this)
                .inject(this);
    }

    private void setUpDrawer() {
        drawerToggle = new DrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        listView.setAdapter(drawerArrayAdapter);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        changeCatalogVisibility(false);
    }

    @OnItemClick(R.id.left_drawer)
    public void onItemClick(AdapterView<?> parent, int position) {
        switch (position) {
            case 0:
                startFragment(CategoriesFragment.getInstance());
                break;
            case 1:
                Dexter.checkPermissions(permissionsListener
                        , Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
                break;
        }
        drawerLayout.closeDrawer(listView);
    }

    public void changeCatalogVisibility(boolean visible) {
        listView.post(() -> {
            if (!visible) {
                listView.getChildAt(0).setAlpha(0.5F);
                listView.getChildAt(0).setClickable(true);
            } else if (pbLoading.getVisibility() == View.GONE) {
                listView.getChildAt(0).setAlpha(1);
                listView.getChildAt(0).setClickable(false);
            }
            drawerArrayAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            finish();
            System.exit(1);
        }
        super.onBackPressed();
    }

    public void showLoading(boolean loading) {
        pbLoading.setVisibility(loading ? View.VISIBLE : View.GONE);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void showPermissionRationale(PermissionToken token) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.permission_location_message)
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                    token.cancelPermissionRequest();
                })
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                    token.continuePermissionRequest();
                })
                .setOnDismissListener(dialog -> token.cancelPermissionRequest())
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FoodCatalogApplication.get(this).releaseMainActivityComponent();
    }

    public void showCategories() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            startFragment(CategoriesFragment.getInstance());
        } else {
            changeCatalogVisibility(true);
        }
    }

    public void startFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
    }

    public void loadMapFragment() {
        startFragment(MapFragment.getInstance());
    }

    public void onNetworkError() {
        showLoading(false);
        Toast.makeText(this, R.string.connection_problem, Toast.LENGTH_LONG).show();
    }
}
