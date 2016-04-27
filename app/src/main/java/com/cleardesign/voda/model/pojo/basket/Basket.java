package com.cleardesign.voda.model.pojo.basket;

import android.app.Activity;
import android.content.Context;

import com.cleardesign.voda.model.pojo.product.Product;
import com.cleardesign.voda.model.pojo.product.WaterProduct;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
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

    public void setProductInBasket(Map<Product, Integer> productInBasket) {
        this.productInBasket = productInBasket;
    }


    public void removeProductByName(String name) {
        Product productRemove = null;
        for (Map.Entry<Product, Integer> entry : this.getProductInBasket().entrySet()) {
            if(entry.getKey().getName().equals(name)){
                productRemove = entry.getKey();
                break;
            }

        }
        this.getProductInBasket().remove(productRemove);

    }

    public Double calcAllPrice() {
        Double allPrice = 0.0;
        for (Map.Entry<Product, Integer> entry : this.getProductInBasket().entrySet()) {
           allPrice += entry.getKey().getPrice()*entry.getValue();
        }
        return allPrice;
    }

    public void addProductInBasket(Product product, Integer count) {
        boolean flag = true;
        int currentCount = 0;
        Product productRemove = null;
        for (Product pr : productInBasket.keySet()) {
            if (pr.getName().equals(product.getName())) {
                flag = false;
                break;
            }
        }
        if (flag) {
            this.getProductInBasket().put(product, count);
        } else {
            for (Map.Entry<Product, Integer> entry : productInBasket.entrySet()) {
                if (entry.getKey().getName().equals(product.getName())) {
                    productRemove = entry.getKey();
                    currentCount = entry.getValue();
                }
            }

            this.getProductInBasket().remove(productRemove);
            this.getProductInBasket().put(product, count + currentCount);
        }
    }

    public void readProductInBasketFromFile(Context context) {
        String filename = "basket";
        FileInputStream fis = null;
        StringBuilder sb = null;

        try {
            fis = context.getApplicationContext().openFileInput(filename);

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();

            //TODO: TypeToken<Map<WaterProduct, Integer>>()
            Type itemsMapType = new TypeToken<Map<WaterProduct, Integer>>() {
            }.getType();
            Map<Product, Integer> map = new Gson().fromJson(json, itemsMapType);

            this.setProductInBasket(map);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void writeProductInBasketToFile(Context context, Activity activity) {
        String filename = "basket";
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
        String jsonStr = gson.toJson(productInBasket);

        FileOutputStream outputStream;
        try {
            outputStream = context.getApplicationContext().openFileOutput(filename, activity.MODE_PRIVATE);
            outputStream.write(jsonStr.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
