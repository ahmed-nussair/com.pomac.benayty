package com.pomac.benayty.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pomac.benayty.R;
import com.pomac.benayty.adapters.AreasAdapter;
import com.pomac.benayty.adapters.CitiesAdapter;
import com.pomac.benayty.adapters.SecondaryCategoriesAdapter;
import com.pomac.benayty.view.enums.MainCategoryFieldType;
import com.pomac.benayty.view.interfaces.AdFilter;
import com.pomac.benayty.view.interfaces.OnAreaSelected;
import com.pomac.benayty.view.interfaces.OnCitySelected;
import com.pomac.benayty.view.interfaces.OnSecondaryCategorySelected;
import com.pomac.benayty.viewmodel.AreasViewModel;
import com.pomac.benayty.viewmodel.CitiesViewModel;
import com.pomac.benayty.viewmodel.SecondaryCategoriesViewModel;

import java.util.Objects;

public class MainCategoryFieldDialog extends Dialog implements
        OnSecondaryCategorySelected,
        OnAreaSelected,
        OnCitySelected {

    private TextView dialogTitle;
    private RecyclerView fieldRecyclerView;
    private TextView submitButton;

    private Fragment fragment;
    private AdFilter adFilter;
    private MainCategoryFieldType type;

    public MainCategoryFieldDialog(@NonNull Context context, int themeResId,
                                   Fragment fragment, AdFilter adFilter,
                                   MainCategoryFieldType type) {
        super(context, themeResId);
        this.fragment = fragment;
        this.adFilter = adFilter;
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_category_field_dialog_layout);
        ColorDrawable background = new ColorDrawable(Color.BLACK);
        background.setAlpha(100);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(background);
        dialogTitle = findViewById(R.id.dialogTitle);
        fieldRecyclerView = findViewById(R.id.fieldRecyclerView);
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(l -> this.dismiss());

        switch (type){
            case SecondaryCategoryField:
                dialogTitle.setText(fragment.getResources().getString(R.string.choose_secondary_item));
                SecondaryCategoriesViewModel viewModel = ViewModelProviders.of(fragment).get(SecondaryCategoriesViewModel.class);

                viewModel.getSecondaryCategoriesResponse(adFilter.getMainCategoryId()).observe(fragment, response -> {

                    SecondaryCategoriesAdapter adapter = new SecondaryCategoriesAdapter(fragment.getContext(), response.getData(), this);
                    fieldRecyclerView.setAdapter(adapter);
                    fieldRecyclerView.setLayoutManager(new LinearLayoutManager(fragment.getContext()));
                });
                break;
            case AreaField:
                dialogTitle.setText(fragment.getResources().getString(R.string.choose_area));
                AreasViewModel areasViewModel = ViewModelProviders.of(fragment).get(AreasViewModel.class);
                areasViewModel.getAreasResponse().observe(fragment,
                        response -> {
                            AreasAdapter adapter = new AreasAdapter(fragment.getContext(), response.getData(), this);
                            fieldRecyclerView.setAdapter(adapter);
                            fieldRecyclerView.setLayoutManager(new LinearLayoutManager(fragment.getContext()));
                        });
                break;
            case CityField:
                dialogTitle.setText(fragment.getResources().getString(R.string.choose_city));
                CitiesViewModel citiesViewModel = ViewModelProviders.of(fragment).get(CitiesViewModel.class);
                citiesViewModel.getCitiesResponse(adFilter.getAreaId()).observe(fragment,
                response -> {
                    CitiesAdapter adapter = new CitiesAdapter(fragment.getContext(), response.getData(), this);
                    fieldRecyclerView.setAdapter(adapter);
                    fieldRecyclerView.setLayoutManager(new LinearLayoutManager(fragment.getContext()));
                });
                break;
        }


    }

    @Override
    public void onSecondaryCategorySelected(int itemId, String itemName) {
        adFilter.setSecondaryCategory(itemId, itemName);
        dismiss();
    }

    @Override
    public void onAreaSelected(int areaId, String areaName) {
        adFilter.setArea(areaId, areaName);
        dismiss();
    }

    @Override
    public void onCitySelected(int cityId, String cityName) {
        adFilter.setCity(cityId, cityName);
        dismiss();
    }
}
