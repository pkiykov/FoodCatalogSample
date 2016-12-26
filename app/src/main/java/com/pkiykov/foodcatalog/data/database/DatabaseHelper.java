package com.pkiykov.foodcatalog.data.database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pkiykov.foodcatalog.CategoryModel;
import com.pkiykov.foodcatalog.OfferModel;
import com.pkiykov.foodcatalog.R;
import com.pkiykov.foodcatalog.data.database.dbmodel.Category;
import com.pkiykov.foodcatalog.data.database.dbmodel.Offer;
import com.pkiykov.foodcatalog.data.model.Catalog;
import com.pkiykov.foodcatalog.data.model.CategoryForXML;
import com.pkiykov.foodcatalog.data.model.OfferForXML;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, "food_catalog", null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Category.CREATE_TABLE);
        db.execSQL(Offer.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertDataIntoDb(Catalog catalog) {
        ArrayList<CategoryForXML> categories
                = (ArrayList<CategoryForXML>) catalog.getShop().getCategories();

        ArrayList<OfferForXML> offerForXMLs
                = (ArrayList<OfferForXML>) catalog.getShop().getOffers();

        Offer.Insert_offer insertOffer = new OfferModel.Insert_offer(getReadableDatabase());
        Category.Insert_category insert_category = new CategoryModel.Insert_category(getReadableDatabase());

        for (CategoryForXML categoryForXML : categories) {
            insert_category.bind(categoryForXML.getId(), categoryForXML.getText()
                    , resourceToUriString(context, categoryForXML.getIconId(categoryForXML.getId())));
            insert_category.program.executeInsert();
        }

        for (OfferForXML offerForXML : offerForXMLs) {

            insertOffer.bind(offerForXML.getId(),
                    offerForXML.getName(),
                    offerForXML.getDescription(),
                    offerForXML.getPictureUrl(),
                    offerForXML.getPrice(),
                    offerForXML.getCategoryId(),
                    offerForXML.getParameters() != null ? offerForXML.getParameters().get(context.getString(R.string.weight)) : null);
            insertOffer.program.executeInsert();
        }
    }

    private String resourceToUriString(Context context, int resID) {
        return ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.getResources().getResourcePackageName(resID) + '/' +
                context.getResources().getResourceTypeName(resID) + '/' +
                context.getResources().getResourceEntryName(resID);
    }
}
