package com.cleardesign.voda.model.pojo.product;

import android.os.Parcelable;

public abstract class Product implements Parcelable {
     String name;
     Double price;
     String image;

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public Double getPrice() {
          return price;
     }

     public void setPrice(Double price) {
          this.price = price;
     }

     public String getImage() {
          return image;
     }

     public void setImage(String image) {
          this.image = image;
     }
}
