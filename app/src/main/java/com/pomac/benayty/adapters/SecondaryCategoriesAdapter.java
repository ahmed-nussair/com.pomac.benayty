package com.pomac.benayty.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pomac.benayty.R;
import com.pomac.benayty.model.SecondaryCategory;
import com.pomac.benayty.view.interfaces.OnSecondaryCategorySelected;

import java.util.List;

public class SecondaryCategoriesAdapter extends RecyclerView.Adapter<SecondaryCategoriesAdapter.SecondaryCategoriesViewHolder> {

    private Context context;
    private List<SecondaryCategory> secondaryCategories;
    private OnSecondaryCategorySelected onItemSelected;

    public SecondaryCategoriesAdapter(Context context, List<SecondaryCategory> secondaryCategories,
                                      OnSecondaryCategorySelected onItemSelected) {
        this.context = context;
        this.secondaryCategories = secondaryCategories;
        this.onItemSelected = onItemSelected;
    }

    @NonNull
    @Override
    public SecondaryCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.secondary_category_item_layout, parent, false);
        return new SecondaryCategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondaryCategoriesViewHolder holder, int position) {
        holder.secondaryCategoryTitle.setText(secondaryCategories.get(position).getName());
        holder.secondaryCategoryLayout.setOnClickListener(l ->
                onItemSelected.onSecondaryCategorySelected(secondaryCategories.get(position).getId(),
                        secondaryCategories.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return secondaryCategories.size();
    }

    static class SecondaryCategoriesViewHolder extends RecyclerView.ViewHolder {

        TextView secondaryCategoryTitle;
        LinearLayout secondaryCategoryLayout;
        SecondaryCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            secondaryCategoryTitle = itemView.findViewById(R.id.secondaryCategoryTitle);
            secondaryCategoryLayout = itemView.findViewById(R.id.secondaryCategoryLayout);
        }
    }
}
