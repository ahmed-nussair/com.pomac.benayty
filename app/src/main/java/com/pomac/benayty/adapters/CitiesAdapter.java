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
import com.pomac.benayty.model.City;
import com.pomac.benayty.view.interfaces.OnAreaSelected;
import com.pomac.benayty.view.interfaces.OnCitySelected;

import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder> {

    private Context context;
    private List<City> citiesCategories;
    private OnCitySelected onItemSelected;

    public CitiesAdapter(Context context, List<City> citiesCategories,
                         OnCitySelected onItemSelected) {
        this.context = context;
        this.citiesCategories = citiesCategories;
        this.onItemSelected = onItemSelected;
    }

    @NonNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.city_item_layout, parent, false);
        return new CitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesViewHolder holder, int position) {
        holder.cityTitle.setText(citiesCategories.get(position).getName());
        holder.cityLayout.setOnClickListener(l -> onItemSelected.onCitySelected(
                citiesCategories.get(position).getId(),
                citiesCategories.get(position).getName()
        ));
    }

    @Override
    public int getItemCount() {
        return citiesCategories.size();
    }


    static class CitiesViewHolder extends RecyclerView.ViewHolder {

        TextView cityTitle;
        LinearLayout cityLayout;
        CitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            cityTitle = itemView.findViewById(R.id.cityTitle);
            cityLayout = itemView.findViewById(R.id.cityLayout);
        }
    }
}
