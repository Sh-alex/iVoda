package com.cleardesign.voda.ui.fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;

import com.cleardesign.voda.R;
import com.cleardesign.voda.model.pojo.product.AllProducts;
import com.cleardesign.voda.model.pojo.product.CoolerProduct;
import com.cleardesign.voda.model.pojo.product.Product;
import com.cleardesign.voda.model.pojo.product.WaterProduct;
import com.cleardesign.voda.ui.activity.DetailsActivity;
import com.cleardesign.voda.ui.adapter.ListProductsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements ListView.OnItemClickListener {


    private OnFragmentInteractionListener mListener;

    private View myFragmentView;


    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();

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
        myFragmentView = inflater.inflate(R.layout.fragment_main, container, false);

        TabHost tabHost;
        tabHost = (TabHost) myFragmentView.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Water");
        tabSpec.setContent(R.id.waterTab);
        tabSpec.setIndicator("Вода");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Coolers");
        tabSpec.setContent(R.id.coolersTab);
        tabSpec.setIndicator("Куллеры");
        tabHost.addTab(tabSpec);

        //Заповню ListView з водою
        ListView listWater = (ListView) myFragmentView.findViewById(R.id.listWater);

        String[] listProduct = getResources().getStringArray(R.array.listWater);
        List<WaterProduct> productArrayList = new ArrayList<>();

        for (String name : listProduct) {
            productArrayList.add(new WaterProduct(name, 100.0, "bottle"));
        }
        AllProducts allProducts = AllProducts.getInstance();
        allProducts.setWaterProducts(productArrayList);

        ListProductsAdapter adapter = new ListProductsAdapter(getActivity(), productArrayList);
        listWater.setAdapter(adapter);

        //Заповню ListView з кулерами
        ListView listCoolers = (ListView) myFragmentView.findViewById(R.id.listCoolers);
        listProduct = getResources().getStringArray(R.array.listCooler);
        List<CoolerProduct> productArrayListCooler = new ArrayList<>();

        for (String name : listProduct) {
            productArrayListCooler.add(new CoolerProduct(name, 100.0, "cooler"));
        }
        allProducts = AllProducts.getInstance();
        allProducts.setCoolerProducts(productArrayListCooler);

        adapter = new ListProductsAdapter(getActivity(), productArrayListCooler);
        listCoolers.setAdapter(adapter);

        listWater.setOnItemClickListener(this);
        listCoolers.setOnItemClickListener(this);
        return myFragmentView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AllProducts allProducts = AllProducts.getInstance();

        Product product;
        TabHost tabHost = (TabHost) myFragmentView.findViewById(R.id.tabHost);

        if (tabHost.getCurrentTab() == 0) {
            product = allProducts.getWaterProducts().get(position);
        } else {
            product = allProducts.getCoolerProducts().get(position);
        }


        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onItemClick(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
