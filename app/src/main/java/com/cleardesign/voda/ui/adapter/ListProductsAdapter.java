package com.cleardesign.voda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cleardesign.voda.R;
import com.cleardesign.voda.model.pojo.product.Product;

import java.util.List;

public class ListProductsAdapter extends BaseAdapter {

    private final Context cotext;
    private LayoutInflater inflater;
    private List<? extends Product> products;

    private class ViewHolder {
        ImageView imageView;
        TextView textView;


    }

    public ListProductsAdapter(Context context, List<? extends Product> products) {
        inflater = LayoutInflater.from(context);
        this.products = products;
        this.cotext = context;
    }

    public int getCount() {
        return products.size();
    }

    public Product getItem(int position) {
        return products.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row, null);
            holder.textView = (TextView) convertView.findViewById(R.id.textItem);
            holder.imageView = (ImageView) convertView.findViewById(R.id.productIcon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(products.get(position).getName());

        int id = cotext.getResources().getIdentifier(products.get(position).getImage(), "drawable", "com.cleardesign.voda");
        holder.imageView.setImageResource(id);
        return convertView;
    }
}
