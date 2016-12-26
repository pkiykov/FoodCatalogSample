package com.pkiykov.foodcatalog.data.api;

import com.pkiykov.foodcatalog.data.model.Catalog;

import retrofit2.http.GET;
import rx.Observable;


public interface FoodCatalogService {

    @GET("getyml/?key=ukAXxeJYZN")
    Observable<Catalog> getCatalog();
}
