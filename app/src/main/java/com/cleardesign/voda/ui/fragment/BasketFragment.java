package com.cleardesign.voda.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cleardesign.voda.R;
import com.cleardesign.voda.model.pojo.basket.Basket;
import com.cleardesign.voda.model.pojo.user.User;
import com.cleardesign.voda.ui.activity.DetailsActivity;
import com.cleardesign.voda.ui.adapter.BasketText;
import com.cleardesign.voda.model.pojo.product.Product;
import com.cleardesign.voda.ui.adapter.BasketAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BasketFragment extends Fragment implements ListView.OnItemClickListener {

    private View myFragmentView;

    private OnFragmentInteractionListener mListener;

    public BasketFragment() {
        // Required empty public constructor
    }


    public static BasketFragment newInstance(String param1, String param2) {
        BasketFragment fragment = new BasketFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_basket, container, false);


        final Basket basket = Basket.getInstance();
        basket.readProductInBasketFromFile(getActivity().getBaseContext());


        ListView lvBasket = (ListView) myFragmentView.findViewById(R.id.lvBasket);
        lvBasket.setOnItemClickListener(this);

        ArrayList<BasketText> objects = new ArrayList<>();
        for (Map.Entry<Product, List<Integer>> entry : basket.getProductInBasket().entrySet()) {
            BasketText basketText = new BasketText(entry.getKey().getName(), "Количество (штук): " + entry.getValue().get(0).toString(), "Сдать тару (штук): " + entry.getValue().get(1).toString(), entry.getKey().getImage());
            objects.add(basketText);
        }


        BasketAdapter basketAdapter = new BasketAdapter(getActivity().getBaseContext(), objects);
        lvBasket.setAdapter(basketAdapter);



        TextView allPrice = (TextView) myFragmentView.findViewById(R.id.tvAllPrice);
        allPrice.setText("Итого: " + basket.calcAllPrice());


        Button confirmOrderButton = (Button) myFragmentView.findViewById(R.id.confirmOrderButton);
        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = getActivity().getBaseContext().getFileStreamPath("user");
                if (file.exists()) {
                    User user = User.getInstance();
                    user.readUserFromFile(getActivity().getBaseContext());

                    if (!basket.getProductInBasket().isEmpty()) {
                        String text = "";
                        text += user.getFio() + "\n" + user.getAddress() + "\n" + user.getPhone() + "\n" + user.getEmail() + "\n";
                        for (Map.Entry<Product, List<Integer>> entry : basket.getProductInBasket().entrySet()) {
                            text += entry.getKey().getName() + ": " + entry.getValue().get(0) + ";\n";
                        }
                        text += "Итого: " + basket.calcAllPrice();
                        //basket.getProductInBasket().clear();

                        final Intent emailIntent = new Intent(Intent.ACTION_VIEW);
                        emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");

                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"shevchenko.olexandr96@gmail.com"});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Заказ");
                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);

                        startActivity(emailIntent);
                    } else {
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Корзина пуста", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Вы не авторизованы", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return myFragmentView;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Basket basket = Basket.getInstance();
        basket.readProductInBasketFromFile(getActivity().getBaseContext());

        Product product = null;
        int i = 0;
        for (Map.Entry<Product, List<Integer>> entry : basket.getProductInBasket().entrySet()) {
            if( i == position) {
                product = entry.getKey();
                break;
            }
            i++;
        }

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    public void onItemClick(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
