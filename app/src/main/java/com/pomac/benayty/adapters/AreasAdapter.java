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
import com.pomac.benayty.model.Area;
import com.pomac.benayty.model.SecondaryCategory;
import com.pomac.benayty.view.interfaces.OnAreaSelected;
import com.pomac.benayty.view.interfaces.OnSecondaryCategorySelected;

import java.util.List;

public class AreasAdapter extends RecyclerView.Adapter<AreasAdapter.AreasViewHolder> {

    private Context context;
    private List<Area> areasCategories;
    private OnAreaSelected onItemSelected;

    public AreasAdapter(Context context, List<Area> areasCategories,
                        OnAreaSelected onItemSelected) {
        this.context = context;
        this.areasCategories = areasCategories;
        this.onItemSelected = onItemSelected;
    }

    @NonNull
    @Override
    public AreasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.area_item_layout, parent, false);
        return new AreasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AreasViewHolder holder, int position) {
        holder.areaTitle.setText(areasCategories.get(position).getName());
        holder.areaLayout.setOnClickListener(l -> onItemSelected.onAreaSelected(
                areasCategories.get(position).getId(),
                areasCategories.get(position).getName()
        ));
    }

    @Override
    public int getItemCount() {
        return areasCategories.size();
    }


    static class AreasViewHolder extends RecyclerView.ViewHolder {

        TextView areaTitle;
        LinearLayout areaLayout;
        AreasViewHolder(@NonNull View itemView) {
            super(itemView);
            areaTitle = itemView.findViewById(R.id.areaTitle);
            areaLayout = itemView.findViewById(R.id.areaLayout);
        }
    }
}
