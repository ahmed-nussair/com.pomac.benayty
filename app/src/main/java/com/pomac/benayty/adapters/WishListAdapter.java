package com.pomac.benayty.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.model.WishListItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {

    private Context context;
    private List<WishListItem> wishList;

    public WishListAdapter(Context context, List<WishListItem> wishList) {
        this.context = context;
        this.wishList = wishList;
    }

    @NonNull
    @Override
    public WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ad_item_layout, parent, false);
        return new WishListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListViewHolder holder, int position) {
        holder.adNameTextView.setText(wishList.get(position).getAdvertisement().getTitle());
        holder.adUserNameTextView.setText(wishList.get(position).getAdvertisement().getUser().getName());

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(10)
                .oval(false)
                .build();
        Picasso.get()
                .load(wishList.get(position).getAdvertisement().getImagePath())
                .fit()
                .transform(transformation)
                .into(holder.adImageView);
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            calendar.setTime(Objects.requireNonNull(sdf.parse(wishList.get(position).getAdvertisement().getCreatedAt())));
            holder.adCreationTimeTextView.setText(Globals.getCreationTimeText(calendar.getTime()));
        } catch (Exception e) {
            Log.e(Globals.TAG, Objects.requireNonNull(e.getMessage()));
            holder.adCreationTimeTextView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return wishList.size();
    }

    static class WishListViewHolder extends RecyclerView.ViewHolder {

        ImageView adImageView;
        TextView adNameTextView;
        TextView adUserNameTextView;
        TextView adCreationTimeTextView;
        WishListViewHolder(@NonNull View itemView) {
            super(itemView);
            adImageView = itemView.findViewById(R.id.adImageView);
            adNameTextView = itemView.findViewById(R.id.adNameTextView);
            adUserNameTextView = itemView.findViewById(R.id.adUserNameTextView);
            adCreationTimeTextView = itemView.findViewById(R.id.adCreationTimeTextView);
        }
    }
}
