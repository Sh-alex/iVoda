package com.cleardesign.voda.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cleardesign.voda.R;
import com.cleardesign.voda.model.pojo.basket.Basket;
import com.cleardesign.voda.model.pojo.product.Product;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

    Product product;
    ImageView imageViewProduct;
    TextView textViewNameProduct;
    TextView tvProductPrice;
    TextView tvCount;
    TextView tvCountBack;
    TextView tvPrice;
    DecimalFormat df = new DecimalFormat("0.00");

    Button minusCountButton;
    Button minusCountButtonBack;
    Button plusCountButton;
    Button plusCountButtonBack;
    Button addToBasket;
    Basket basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        Intent intent = getIntent();

        product = intent.getParcelableExtra("product");

        imageViewProduct = (ImageView) findViewById(R.id.ivProduct);
        int imageId = getBaseContext().getResources().getIdentifier(product.getImage(), "drawable", "com.cleardesign.voda");
        imageViewProduct.setImageResource(imageId);

        textViewNameProduct = (TextView) findViewById(R.id.tvNameProduct);
        textViewNameProduct.setText(product.getName());

        tvProductPrice = (TextView) findViewById(R.id.tvProductPrice);
        tvProductPrice.setText("Цена: " + df.format(product.getPrice()));

        basket = Basket.getInstance();
        basket.readProductInBasketFromFile(getBaseContext());
        basket.getProductInBasket();
        List<Integer> prod = null;
        for (Map.Entry<Product, List<Integer>> entry : basket.getProductInBasket().entrySet()) {
            if (entry.getKey().getName().equals(product.getName())) {
                prod = entry.getValue();
                break;
            }
        }
        tvCount = (TextView) findViewById(R.id.tvCount);
        if (prod != null)
            tvCount.setText(prod.get(0).toString());
        tvCountBack = (TextView) findViewById(R.id.tvCountBack);
        if (prod != null)
            tvCountBack.setText(prod.get(1).toString());
        tvPrice = (TextView) findViewById(R.id.tvPrice);


        tvPrice.setText("Итого: " + df.format(Integer.parseInt(tvCount.getText().toString()) * product.getPrice() - Integer.parseInt(tvCountBack.getText().toString()) * product.getPrice() / 2));

        minusCountButton = (Button) findViewById(R.id.minusCountButton);
        plusCountButton = (Button) findViewById(R.id.plusCountButton);
        plusCountButtonBack = (Button) findViewById(R.id.plusCountBottleBack);
        minusCountButtonBack = (Button) findViewById(R.id.minusCountBottleBack);
        addToBasket = (Button) findViewById(R.id.addToBasket);

        minusCountButton.setOnClickListener(btListener);
        minusCountButtonBack.setOnClickListener(btListener);
        plusCountButton.setOnClickListener(btListener);
        plusCountButtonBack.setOnClickListener(btListener);
        addToBasket.setOnClickListener(btListener);

    }

    private View.OnClickListener btListener = new View.OnClickListener() {
        public void onClick(View v) {

            Integer count;
            Double allPrice;
            switch (v.getId()) {
                case R.id.minusCountButton:
                    if (!tvCount.getText().toString().equals("1")) {
                        count = Integer.parseInt(tvCount.getText().toString()) - 1;

                        if (tvCount.getText().equals(tvCountBack.getText())) {
                            tvCountBack.setText(count.toString());
                        }

                        tvCount.setText(count.toString());
                        allPrice = count * product.getPrice() - Integer.parseInt(tvCountBack.getText().toString()) * product.getPrice() / 2;
                        tvPrice.setText("Итого: " + df.format(allPrice));
                    }
                    break;
                case R.id.plusCountButton:
                    if (!tvCount.getText().toString().equals("10")) {
                        count = Integer.parseInt(tvCount.getText().toString()) + 1;

                        if (tvCount.getText().equals(tvCountBack.getText())) {
                            tvCountBack.setText(count.toString());
                        }

                        tvCount.setText(count.toString());
                        allPrice = count * product.getPrice() - Integer.parseInt(tvCountBack.getText().toString()) * product.getPrice() / 2;
                        tvPrice.setText("Итого: " + df.format(allPrice));
                    }
                    break;
                case R.id.minusCountBottleBack:
                    if (!tvCountBack.getText().toString().equals("0")) {
                        count = Integer.parseInt(tvCountBack.getText().toString()) - 1;
                        tvCountBack.setText(count.toString());

                        count = Integer.parseInt(tvCount.getText().toString());
                        allPrice = count * product.getPrice() - Integer.parseInt(tvCountBack.getText().toString()) * product.getPrice() / 2;
                        tvPrice.setText("Итого: " + df.format(allPrice));
                    }
                    break;

                case R.id.plusCountBottleBack:
                    if (!tvCount.getText().equals(tvCountBack.getText())) {
                        count = Integer.parseInt(tvCountBack.getText().toString()) + 1;
                        tvCountBack.setText(count.toString());

                        count = Integer.parseInt(tvCount.getText().toString());
                        allPrice = count * product.getPrice() - Integer.parseInt(tvCountBack.getText().toString()) * product.getPrice() / 2;
                        tvPrice.setText("Итого: " + df.format(allPrice));
                    }
                    break;

                case R.id.addToBasket:
                    basket = Basket.getInstance();

                    basket.readProductInBasketFromFile(getBaseContext());

                    basket.addProductInBasket(product, Integer.parseInt(tvCount.getText().toString()), Integer.parseInt(tvCountBack.getText().toString()));

                    basket.writeProductInBasketToFile(getBaseContext(), getParent());

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("fragment", "basket");
                    startActivity(intent);
                    break;
            }
        }
    };

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("count", tvCount.getText().toString());
        outState.putString("all", tvPrice.getText().toString());
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tvCount.setText(savedInstanceState.getString("count"));
        tvPrice.setText(savedInstanceState.getString("all"));

    }
}
