package com.pkiykov.foodcatalog.data.model;

import com.pkiykov.foodcatalog.R;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "category")
public class CategoryForXML {

    @Attribute
    private int id;

    private String text;

    @Text
    public String getText() {
        return text;
    }

    @Text
    public void setText(String text){
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public int getIconId(int id) {
        int position = 0;
        switch (id){
            case 1:
                position = R.drawable.n0;
                break;
            case 2:
                position = R.drawable.n1;
                break;
            case 3:
                position = R.drawable.n2;
                break;
            case 5:
                position = R.drawable.n3;
                break;
            case 6:
                position = R.drawable.n4;
                break;
            case 7:
                position = R.drawable.n5;
                break;
            case 8:
                position = R.drawable.n6;
                break;
            case 9:
                position = R.drawable.n7;
                break;
            case 10:
                position = R.drawable.n8;
                break;
            case 18:
                position = R.drawable.n9;
                break;
            case 20:
                position = R.drawable.n10;
                break;
            case 23:
                position = R.drawable.n11;
                break;
            case 25:
                position = R.drawable.n12;
                break;
        }
        return position;
    }

    public void setId(int id) {
        this.id = id;
    }
}
