package com.pomac.benayty.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.model.WishListItem;
import com.pomac.benayty.view.interfaces.OnAdItemSelected;
import com.pomac.benayty.view.interfaces.OnItemDeleted;
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
    private OnAdItemSelected onAdItemSelected;
    private OnItemDeleted onItemDeleted;

    public WishListAdapter(
            Context context,
            List<WishListItem> wishList,
            OnAdItemSelected onAdItemSelected,
            OnItemDeleted onItemDeleted
    ) {
        this.context = context;
        this.wishList = wishList;
        this.onAdItemSelected = onAdItemSelected;
        this.onItemDeleted = onItemDeleted;
    }

    @NonNull
    @Override
    public WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wish_list_item_layout, parent, false);
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

        holder.adItemRelativeLayout.setOnClickListener(v -> onAdItemSelected.onItemSelected(
                wishList.get(position).getAdvertisement().getId(),
                wishList.get(position).getAdvertisement().getTitle()
        ));
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            calendar.setTime(Objects.requireNonNull(sdf.parse(wishList.get(position).getAdvertisement().getCreatedAt())));
            holder.adCreationTimeTextView.setText(Globals.getCreationTimeText(calendar.getTime()));
        } catch (Exception e) {
            Log.e(Globals.TAG, Objects.requireNonNull(e.getMessage()));
            holder.adCreationTimeTextView.setText("");
        }

        holder.deleteButtonImageView.setOnClickListener(v -> {
            onItemDeleted.onItemDeleted(Integer.parseInt(wishList.get(position).getAdvertisementId()));
            notifyDataSetChanged();
            notifyItemRemoved(Integer.parseInt(wishList.get(position).getAdvertisementId()));
        });
    }

    @Override
    public int getItemCount() {
        return wishList.size();
    }

    static class WishListViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout adItemRelativeLayout;
        ImageView adImageView;
        TextView adNameTextView;
        TextView adUserNameTextView;
        TextView adCreationTimeTextView;
        ImageView deleteButtonImageView;
        WishListViewHolder(@NonNull View itemView) {
            super(itemView);
            adItemRelativeLayout = itemView.findViewById(R.id.adItemRelativeLayout);
            adImageView = itemView.findViewById(R.id.adImageView);
            adNameTextView = itemView.findViewById(R.id.adNameTextView);
            adUserNameTextView = itemView.findViewById(R.id.adUserNameTextView);
            adCreationTimeTextView = itemView.findViewById(R.id.adCreationTimeTextView);
            deleteButtonImageView = itemView.findViewById(R.id.deleteButtonImageView);
        }
    }
}
