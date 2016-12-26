package com.pkiykov.foodcatalog.data.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.pkiykov.foodcatalog.R;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
@Root (name = "offer", strict = false)
public class OfferForXML {


    @Attribute(name = "id")
    private int id;

    @Element(name = "url", required = false)
    private String url;

    @Element(name = "name")
    private String name;

    @Element(name = "price", required = false)
    private double price;

    @Element(name = "description", required = false)
    private String description;

    @Element(name = "picture", required = false)
    private String pictureUrl;

    @Element(name = "categoryId")
    private int categoryId;

    @ElementMap(entry = "param", key = "name", attribute = true, required = false, inline = true)
    private Map<String, String> parameters;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }


   /* public byte[] getPictureByUrl(String pictureUrl) {
        ByteArrayOutputStream outputStream = null;
        try {
            URL url = new URL(pictureUrl);
            outputStream = new ByteArrayOutputStream();

            try {
                byte[] chunk = new byte[4096];
                int bytesRead;
                InputStream stream = url.openStream();

                while ((bytesRead = stream.read(chunk)) > 0) {
                    outputStream.write(chunk, 0, bytesRead);
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return outputStream == null? null:outputStream.toByteArray();
    }*/


    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void setId(int id) {
        this.id = id;
    }
}
