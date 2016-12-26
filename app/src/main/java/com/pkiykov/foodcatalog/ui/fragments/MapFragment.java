package com.pkiykov.foodcatalog.ui.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pkiykov.foodcatalog.FoodCatalogApplication;
import com.pkiykov.foodcatalog.R;
import com.pkiykov.foodcatalog.ui.views.MapScrollView;
import com.pkiykov.foodcatalog.utils.MapManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import icepick.Icepick;
import icepick.State;

public class MapFragment extends Fragment {

    public static final double RESTAURANT_LATITUDE = 53.841652;
    public static final double RESTAURANT_LONGITUDE = 27.700196;

    public static final double MINSK_LATITUDE = 53.903752;
    public static final double MINSK_LONGITUDE = 27.564201;

    public static final int ZOOM = 10;

    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.scroll_view)
    MapScrollView mapScrollView;
    @Inject
    MapManager mapStateManager;

    private GoogleMap googleMap;
    private Unbinder unbinder;
    @State
    int[] scrollState;

    public static MapFragment getInstance() {
        return new MapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.map_fragment, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FoodCatalogApplication.get(getActivity()).getAppComponent().inject(this);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        if(scrollState != null) {
            mapScrollView.scrollTo(scrollState[0], scrollState[1]);
        }
        mapView.onCreate(savedInstanceState);
        initializeMap();
    }

    private void initializeMap() {
        mapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            LatLng restaurant = new LatLng(RESTAURANT_LATITUDE, RESTAURANT_LONGITUDE);
            String title = getResources().getString(R.string.we_are_here);
            googleMap.addMarker(new MarkerOptions().position(restaurant).title(title));
            CameraPosition position = mapStateManager.getSavedCameraPosition();
            if (position != null) {
                CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
                googleMap.moveCamera(update);
                googleMap.setMapType(mapStateManager.getSavedMapType());
            } else {
                LatLng minsk = new LatLng(MINSK_LATITUDE, MINSK_LONGITUDE);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(minsk, ZOOM));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(restaurant));
            }

            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                    getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null)
            mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null)
            mapStateManager.saveMapState(googleMap);
        mapStateManager.getSavedCameraPosition();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null)
            mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null)
            mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        if(mapScrollView!=null) {
            super.onSaveInstanceState(bundle);
            scrollState = new int[]{mapScrollView.getScrollX(), mapScrollView.getScrollY()};
            Icepick.saveInstanceState(this, bundle);
        }
    }
}
