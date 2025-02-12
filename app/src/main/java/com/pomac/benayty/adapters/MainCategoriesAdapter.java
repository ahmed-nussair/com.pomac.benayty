package com.pomac.benayty.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pomac.benayty.R;
import com.pomac.benayty.model.MainCategory;
import com.pomac.benayty.view.interfaces.OnMainCategorySelected;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainCategoriesAdapter extends RecyclerView.Adapter<MainCategoriesAdapter.MainCategoriesViewHolder> {

    private Context context;
    private List<MainCategory> mainCategories;
    private OnMainCategorySelected onMainCategorySelected;

    public MainCategoriesAdapter(Context context, List<MainCategory> mainCategories, OnMainCategorySelected onMainCategorySelected) {
        this.context = context;
        this.mainCategories = mainCategories;
        this.onMainCategorySelected = onMainCategorySelected;
    }

    @NonNull
    @Override
    public MainCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_category_item_layout, parent, false);
        return new MainCategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainCategoriesViewHolder holder, int position) {
        holder.mainCategoryTitle.setText(mainCategories.get(position).getName());
        Picasso.get()
                .load(mainCategories.get(position).getImagePath())
                .into(holder.mainCategoryImage);

        holder.mainCategoryItem
                .setOnClickListener(l -> onMainCategorySelected
                        .onMainCategorySelected
                                (mainCategories.get(position).getId(),
                                        mainCategories.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return mainCategories.size();
    }

    static class MainCategoriesViewHolder extends RecyclerView.ViewHolder {

        ImageView mainCategoryImage;
        TextView mainCategoryTitle;
        RelativeLayout mainCategoryItem;

        MainCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            mainCategoryImage = itemView.findViewById(R.id.mainCategoryImage);
            mainCategoryTitle = itemView.findViewById(R.id.mainCategoryTitle);
            mainCategoryItem = itemView.findViewById(R.id.mainCategoryItem);
        }
    }
}
