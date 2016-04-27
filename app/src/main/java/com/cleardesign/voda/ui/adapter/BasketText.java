package com.cleardesign.voda.ui.adapter;

public class BasketText {

    private String textItem;
    private String countWater;
    private String ivProduct;

    public BasketText(String textItem, String countWater, String ivProduct) {
        this.textItem = textItem;
        this.countWater = countWater;
        this.ivProduct = ivProduct;
    }

    public String getTextItem() {
        return textItem;
    }

    public String getCountWater() {
        return countWater;
    }

    public String getIvProduct() {
        return ivProduct;
    }
}
