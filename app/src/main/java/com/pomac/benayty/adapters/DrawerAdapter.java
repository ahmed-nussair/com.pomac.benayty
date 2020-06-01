package com.pomac.benayty.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pomac.benayty.R;
import com.pomac.benayty.view.helperclasses.DrawerItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrawerAdapter extends BaseAdapter {

    private Context context;
    private List<DrawerItem> items;

    public DrawerAdapter(Context context, List<DrawerItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.drawer_item_layout, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.drawerTitleTextView.setText(items.get(position).getDrawerItemTitle());
        Picasso.get()
                .load(items.get(position).getDrawerItemImageResource())
                .into(holder.drawerIconImageView);
        return view;
    }

    static class ViewHolder {
        ImageView drawerIconImageView;
        TextView drawerTitleTextView;

        ViewHolder(View view) {
            drawerIconImageView = view.findViewById(R.id.drawerIconImageView);
            drawerTitleTextView = view.findViewById(R.id.drawerTitleTextView);
        }
    }
}
