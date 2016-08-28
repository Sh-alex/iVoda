package com.cleardesign.voda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cleardesign.voda.R;

import java.util.ArrayList;
import java.util.List;


public class BasketAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<BasketText> objects;
    private final Context cotext;


    private class ViewHolder {
        TextView textItem;
        TextView countWater;
        TextView tvCountBottleBackBasket;
        ImageView ivProduct;

    }

    public BasketAdapter(Context context, ArrayList<BasketText> objects) {
        inflater = LayoutInflater.from(context);
        this.objects = objects;
        this.cotext = context;
    }

    public int getCount() {
        return objects.size();
    }

    public BasketText getItem(int position) {
        return objects.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.rowbasket, null);
            holder.textItem = (TextView) convertView.findViewById(R.id.textItem);
            holder.countWater = (TextView) convertView.findViewById(R.id.countWater);
            holder.tvCountBottleBackBasket = (TextView) convertView.findViewById(R.id.tvCountBottleBackBasket);
            holder.ivProduct = (ImageView) convertView.findViewById(R.id.ivProduct);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textItem.setText(objects.get(position).getTextItem());
        holder.countWater.setText(objects.get(position).getCountWater());
        holder.tvCountBottleBackBasket.setText(objects.get(position).getTvCountBottleBackBasket());

        int id = cotext.getResources().getIdentifier(objects.get(position).getIvProduct(), "drawable", "com.cleardesign.voda");
        holder.ivProduct.setImageResource(id);
        return convertView;
    }
}
