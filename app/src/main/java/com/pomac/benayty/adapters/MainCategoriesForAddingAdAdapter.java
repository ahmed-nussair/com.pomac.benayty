package com.pomac.benayty.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pomac.benayty.R;
import com.pomac.benayty.model.MainCategory;
import com.pomac.benayty.view.interfaces.OnMainCategorySelected;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainCategoriesForAddingAdAdapter extends RecyclerView.Adapter<MainCategoriesForAddingAdAdapter.MainCategoriesViewHolder> {

    private Context context;
    private List<MainCategory> mainCategories;
    private OnMainCategorySelected onMainCategorySelected;

    public MainCategoriesForAddingAdAdapter(Context context, List<MainCategory> mainCategories, OnMainCategorySelected onMainCategorySelected) {
        this.context = context;
        this.mainCategories = mainCategories;
        this.onMainCategorySelected = onMainCategorySelected;
    }

    @NonNull
    @Override
    public MainCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_category_for_adding_ad_item_layout, parent, false);
        return new MainCategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainCategoriesViewHolder holder, int position) {
        holder.mainCategoryForAddingAdTitle.setText(mainCategories.get(position).getName());

        holder.mainCategoryForAddingAdLayout
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

        TextView mainCategoryForAddingAdTitle;
        LinearLayout mainCategoryForAddingAdLayout;

        MainCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            mainCategoryForAddingAdTitle = itemView.findViewById(R.id.mainCategoryForAddingAdTitle);
            mainCategoryForAddingAdLayout = itemView.findViewById(R.id.mainCategoryForAddingAdLayout);
        }
    }
}
