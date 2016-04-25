package com.cleardesign.voda.model.pojo.product;

import java.util.List;

public class AllProducts {
    private static AllProducts ourInstance = new AllProducts();

    public static AllProducts getInstance() {
        return ourInstance;
    }

    private AllProducts() {
    }

    private List<WaterProduct> waterProducts;
    private List<CoolerProduct> coolerProducts;

    public List<WaterProduct> getWaterProducts() {
        return waterProducts;
    }

    public void setWaterProducts(List<WaterProduct> waterProducts) {
        this.waterProducts = waterProducts;
    }

    public List<CoolerProduct> getCoolerProducts() {
        return coolerProducts;
    }

    public void setCoolerProducts(List<CoolerProduct> coolerProducts) {
        this.coolerProducts = coolerProducts;
    }

    public Product findProductByName(String name){
        Product product;

        for (WaterProduct waterProduct : waterProducts){
            if(waterProduct.getName().equals(name)){
                product = waterProduct;
                return product;
            }
        }

        for (CoolerProduct coolerProduct : coolerProducts){
            if(coolerProduct.getName().equals(name)){
                product = coolerProduct;
                return product;
            }
        }

        return null;
    }
}
