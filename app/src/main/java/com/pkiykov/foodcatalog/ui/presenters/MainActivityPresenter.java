package com.pkiykov.foodcatalog.ui.presenters;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.pkiykov.foodcatalog.R;
import com.pkiykov.foodcatalog.data.api.FoodCatalogService;
import com.pkiykov.foodcatalog.data.database.DatabaseHelper;
import com.pkiykov.foodcatalog.ui.MainActivity;
import com.pkiykov.foodcatalog.utils.InternetConnection;

import javax.inject.Inject;

import icepick.State;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivityPresenter extends BasePresenter<MainActivity> {

    private static final int REQUEST_ITEMS = 1;
    private Subscription internetStatusSubscription;
    @State
    boolean isCatalogLoadedAtLeastOnce;

    @Inject
    DatabaseHelper databaseHelper;
    @Inject
    FoodCatalogService foodCatalogService;
    @Inject
    InternetConnection internetConnection;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
            restartableLatestCache(REQUEST_ITEMS,
                    () -> foodCatalogService.getCatalog()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()),
                    (activity, catalog) -> {
                        isCatalogLoadedAtLeastOnce = true;
                        new Thread(() -> {
                            databaseHelper.insertDataIntoDb(catalog);
                        }).start();
                        activity.showLoading(false);
                        activity.showCategories();
                    },
                    (mainActivity, throwable) -> {
                        mainActivity.onNetworkError();
                        if (!isCatalogLoadedAtLeastOnce) {
                            waitForInternetToComeBack();
                        }
                            mainActivity.showCategories();
                    });
    }

    private void waitForInternetToComeBack() {
        if (internetStatusSubscription == null || internetStatusSubscription.isUnsubscribed()) {
            internetStatusSubscription = internetConnection.getInternetStatusHotObservable()
                    .filter(internetConnectionStatus -> internetConnectionStatus)
                    .subscribe(internetConnectionStatus -> {
                        request();
                        stopWaitForInternetToComeBack();
                    });
        }
        internetConnection.registerBroadCastReceiver();
    }

    public void request() {
        start(REQUEST_ITEMS);
    }


    private void stopWaitForInternetToComeBack() {
        if (internetStatusSubscription != null && !internetStatusSubscription.isUnsubscribed()) {
            internetStatusSubscription.unsubscribe();
            internetConnection.unRegisterBroadCastReceiver();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopWaitForInternetToComeBack();
    }
}
