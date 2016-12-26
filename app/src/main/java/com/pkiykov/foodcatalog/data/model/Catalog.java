package com.pkiykov.foodcatalog.data.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.Date;
import java.util.Map;

@Root(name="yml_catalog")
public class Catalog {

    @Attribute
    private String date;

    @Element
    private Shop shop;

    public Shop getShop() {
        return shop;
    }

    public String getDate() {
        return date;
    }
}
