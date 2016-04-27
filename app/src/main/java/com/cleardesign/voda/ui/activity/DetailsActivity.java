package com.cleardesign.voda.ui.activity;

import android.app.FragmentTransaction;
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
import com.cleardesign.voda.model.pojo.product.WaterProduct;
import com.cleardesign.voda.ui.fragment.BasketFragment;

import java.text.DecimalFormat;

public class DetailsActivity extends AppCompatActivity {

    Product product;
    ImageView imageViewProduct;
    TextView textViewNameProduct;
    TextView tvProductPrice;
    TextView tvCount;
    TextView tvPrice;
    DecimalFormat df = new DecimalFormat("0.00");

    Button minusCountButton;
    Button plusCountButton;
    Button addToBasket;

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

        tvCount = (TextView) findViewById(R.id.tvCount);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvPrice.setText("Итого: " + df.format(product.getPrice()));

        minusCountButton = (Button) findViewById(R.id.minusCountButton);
        plusCountButton = (Button) findViewById(R.id.plusCountButton);
        addToBasket = (Button) findViewById(R.id.addToBasket);

        minusCountButton.setOnClickListener(btListener);
        plusCountButton.setOnClickListener(btListener);
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
                        tvCount.setText(count.toString());
                        allPrice = count * product.getPrice();
                        tvPrice.setText("Итого: " + df.format(allPrice));
                    }
                    break;
                case R.id.plusCountButton:
                    count = Integer.parseInt(tvCount.getText().toString()) + 1;
                    tvCount.setText(count.toString());
                    allPrice = count * product.getPrice();
                    tvPrice.setText("Итого: " + df.format(allPrice));
                    break;

                case R.id.addToBasket:
                    Basket basket = Basket.getInstance();

                   basket.readProductInBasketFromFile(getBaseContext());

                    basket.addProductInBasket(product, Integer.parseInt(tvCount.getText().toString()));

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
