package com.cleardesign.voda.ui.adapter;

public class BasketText {

    private String textItem;
    private String countWater;
    private String tvCountBottleBackBasket;
    private String ivProduct;

    public BasketText(String textItem, String countWater, String tvCountBottleBackBasket, String ivProduct) {
        this.textItem = textItem;
        this.countWater = countWater;
        this.ivProduct = ivProduct;
        this.tvCountBottleBackBasket = tvCountBottleBackBasket;
    }

    public String getTextItem() {
        return textItem;
    }

    public String getCountWater() {
        return countWater;
    }
    public String getTvCountBottleBackBasket() {
        return tvCountBottleBackBasket;
    }

    public String getIvProduct() {
        return ivProduct;
    }
}
