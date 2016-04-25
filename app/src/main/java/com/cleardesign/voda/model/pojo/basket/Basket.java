package com.cleardesign.voda.model.pojo.basket;

import com.cleardesign.voda.model.pojo.product.Product;

import java.util.HashMap;
import java.util.Map;

public class Basket {
    private static Basket ourInstance = new Basket();

    public static Basket getInstance() {
        return ourInstance;
    }

    private Basket() {
    }

    private Map<Product, Integer> productInBasket = new HashMap<>();

    public Map<Product, Integer> getProductInBasket() {
        return productInBasket;
    }

    public void setProductInBasket(Map<Product, Integer> countProduct) {
        this.productInBasket = countProduct;
    }



    public void removeProductByName(String name){

    }

    public Integer calcAllPrice(){
        return 0;
    }
}
