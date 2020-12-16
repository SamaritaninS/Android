package com.example.timer.Pallet;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.timer.R;

import java.util.List;

public class PalletAdapter extends BaseAdapter {
    private List<Pallet> pallets;
    private Context context;

    public PalletAdapter(Context context, List<Pallet> pallets) {
        this.context = context;
        this.pallets = pallets;
    }


    public static class ViewHolder {
        ImageView color;
    }

    @Override
    public int getCount() {
        return pallets.size();
    }

    @Override
    public Object getItem(int position) {
        return pallets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View customView = convertView;
        final Pallet pallet = pallets.get(position);
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater li = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            customView = li.inflate(R.layout.pallet_item, null);
            holder.color =  customView.findViewById(R.id.color);
            customView.setTag(holder);
        } else {
            holder = (ViewHolder) customView.getTag();
        }

        holder.color.setBackgroundColor(Color.parseColor(pallet.getColor()));

        return customView;

    }
}
