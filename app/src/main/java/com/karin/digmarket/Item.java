package com.karin.digmarket;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.io.Serializable;

public class Item implements Serializable {

    private String description;
    private String amount;
    private String price;
    private String code;

    public Item(String code, String description, String price, String amount)
    {
        this.code = code;
        this.description = description;
        this.amount = amount;
        this.price = price;

    }

    public Item(String code, String description, String price)
    {
        this.code = code;
        this.description = description;
        this.price = price;

    }
    public Item(Parcel in) {
        description = in.readString();
        amount = in.readString();
        price = in.readString();
        code = in.readString();
    }



    public Item()
    {}

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public int getIntAmount()
    {
        int val = Integer.parseInt(this.amount);
        return val;
    }

    public void setStrAmount(int amount)
    {
        this.amount = Integer.toString(amount);
    }



    public void setPrice(String price)
    {
        this.price = price;
    }

    public void setCode(String code)
    {
        this.code = code;
    }



    public String getDescription()
    {
        return this.description;
    }

    public String getAmount()
    {
        return this.amount;
    }

    public String getCode() {
        return this.code;
    }

    public String getPrice() {
        return this.price;
    }


}
