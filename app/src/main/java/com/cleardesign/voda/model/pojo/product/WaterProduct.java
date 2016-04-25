package com.cleardesign.voda.model.pojo.product;

import android.os.Parcel;
import android.os.Parcelable;

public class WaterProduct extends Product {

    public WaterProduct() {

    }
    public WaterProduct(String name, Double price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getName());
        dest.writeDouble(getPrice());
        dest.writeString(getImage());

    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new WaterProduct(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private WaterProduct(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        image = in.readString();
    }
}
