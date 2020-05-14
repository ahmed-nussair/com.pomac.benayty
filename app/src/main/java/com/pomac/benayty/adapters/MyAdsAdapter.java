package com.pomac.benayty.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.model.Advertisement;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MyAdsAdapter extends RecyclerView.Adapter<MyAdsAdapter.AdsViewHolder> {

    private Context context;
    private List<Advertisement> ads;

    public MyAdsAdapter(Context context, List<Advertisement> ads) {
        this.context = context;
        this.ads = ads;
    }

    @NonNull
    @Override
    public AdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ad_item_layout, parent, false);
        return new AdsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsViewHolder holder, int position) {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            calendar.setTime(Objects.requireNonNull(sdf.parse(ads.get(position).getCreatedAt())));

            holder.adNameTextView.setText(ads.get(position).getTitle());
            holder.adUserNameTextView.setText(ads.get(position).getUser().getName());
            holder.adCreationTimeTextView.setText(Globals.getCreationTimeText(calendar.getTime()));

            Transformation transformation = new RoundedTransformationBuilder()
                    .cornerRadiusDp(10)
                    .oval(false)
                    .build();
            Picasso.get()
                    .load(ads.get(position).getImagePath())
                    .fit()
                    .transform(transformation)
                    .into(holder.adImageView);
        } catch (Exception e) {
            Log.e(Globals.TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    @Override
    public int getItemCount() {
        return ads.size();
    }

    static class AdsViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView adImageView;
        TextView adNameTextView;
        TextView adUserNameTextView;
        TextView adCreationTimeTextView;

        AdsViewHolder(@NonNull View itemView) {
            super(itemView);
            adImageView = itemView.findViewById(R.id.adImageView);
            adNameTextView = itemView.findViewById(R.id.adNameTextView);
            adUserNameTextView = itemView.findViewById(R.id.adUserNameTextView);
            adCreationTimeTextView = itemView.findViewById(R.id.adCreationTimeTextView);
        }
    }
}
