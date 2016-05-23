package com.cleardesign.voda.ui.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.cleardesign.voda.R;
import com.cleardesign.voda.model.pojo.basket.Basket;
import com.cleardesign.voda.model.pojo.product.Product;
import com.cleardesign.voda.ui.adapter.BasketAdapter;
import com.cleardesign.voda.ui.adapter.BasketText;
import com.cleardesign.voda.ui.fragment.BasketFragment;
import com.cleardesign.voda.ui.fragment.MainFragment;
import com.cleardesign.voda.ui.fragment.UserFragment;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction transaction;
    MainFragment mainFragment;
    BasketFragment basketFragment;
    UserFragment userFragment;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mainFragment = new MainFragment();
        basketFragment = new BasketFragment();
        userFragment = new UserFragment();


    }

    public void myClickHandler(View v) {
        //get the row the clicked button is in
        LinearLayout vwParentRow = (LinearLayout) v.getParent();

        LinearLayout vwParentCol = (LinearLayout) vwParentRow.getChildAt(1);
        TextView tw = (TextView) vwParentCol.getChildAt(0);


        Basket basket = Basket.getInstance();
        basket.readProductInBasketFromFile(getBaseContext());
        basket.removeProductByName(tw.getText().toString());
        basket.writeProductInBasketToFile(getBaseContext(), getParent());

        ListView lvBasket = (ListView) findViewById(R.id.lvBasket);

        ArrayList<BasketText> objects = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : basket.getProductInBasket().entrySet()) {
            BasketText basketText = new BasketText(entry.getKey().getName(), "Количество (штук): " + entry.getValue().toString(), entry.getKey().getImage());
            objects.add(basketText);
        }


        BasketAdapter basketAdapter = new BasketAdapter(getBaseContext(), objects);
        lvBasket.setAdapter(basketAdapter);

        TextView allPrice = (TextView) findViewById(R.id.tvAllPrice);
        allPrice.setText("Итого: " + basket.calcAllPrice());


        vwParentRow.refreshDrawableState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        transaction = getFragmentManager().beginTransaction();
        MenuItem item;
        for (int i = 0; i <= 4; i++) {
            if (i == 4) {
                navigationView.getMenu().getItem(0).setChecked(true);
                transaction.replace(R.id.container, mainFragment);
                transaction.commit();
                break;
            }
            item = navigationView.getMenu().getItem(i);
            if (item.isChecked()) {
                switch (item.getItemId()) {
                    case R.id.nav_products:
                        transaction.replace(R.id.container, mainFragment);
                        break;
                    case R.id.nav_basket:
                        transaction.replace(R.id.container, basketFragment);
                        break;
                    case R.id.nav_authorization:
                        transaction.replace(R.id.container, userFragment);
                        break;
                }
                transaction.commit();
                break;
            }

        }
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        transaction = getFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.nav_products:
                transaction.replace(R.id.container, mainFragment);
                break;
            case R.id.nav_basket:
                transaction.replace(R.id.container, basketFragment);
                break;
            case R.id.nav_authorization:
                transaction.replace(R.id.container, userFragment);
                break;
        }
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        int currentTab = 0;

        try {
            currentTab = tabHost.getCurrentTab();
            outState.putInt("currentTab", currentTab);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        try {
            tabHost.setCurrentTab(savedInstanceState.getInt("currentTab"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
