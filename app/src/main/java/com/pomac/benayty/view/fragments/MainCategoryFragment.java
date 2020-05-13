package com.pomac.benayty.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.view.MainCategoryFieldType;
import com.pomac.benayty.view.dialogs.MainCategoryFieldDialog;
import com.pomac.benayty.view.interfaces.AdFilter;
import com.pomac.benayty.view.interfaces.AppNavigator;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainCategoryFragment extends Fragment implements AdFilter{

    private String mainCategoryTitle;

    private int mainCategoryId;
    private int secondaryCategoryId;
    private int areaId;
    private int cityId;

    private AppNavigator navigator;

    private TextView secondaryCategoryField;
    private TextView areaField;
    private TextView cityField;
    private TextView findSecondaryCategoriesButton;



    public MainCategoryFragment() {
        mainCategoryId = -1;
        secondaryCategoryId = -1;
        areaId = -1;
        cityId = -1;

        mainCategoryTitle = "";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_category, container, false);
        secondaryCategoryField = view.findViewById(R.id.secondaryCategoryField);
        areaField = view.findViewById(R.id.areaField);
        cityField = view.findViewById(R.id.cityField);
        findSecondaryCategoriesButton = view.findViewById(R.id.findSecondaryCategoriesButton);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getArguments() != null;

        mainCategoryId = getArguments().getInt(Globals.MAIN_CATEGORY_ID, 0);
        mainCategoryTitle = getArguments().getString(Globals.MAIN_CATEGORY_NAME);

        assert getActivity() != null;
        navigator = (AppNavigator) getActivity();
        navigator.setTitle(mainCategoryTitle);

        secondaryCategoryField.setOnClickListener(l -> {
            MainCategoryFieldDialog dialog = new MainCategoryFieldDialog
                    (getActivity(), android.R.style.Theme_Translucent_NoTitleBar,
                            this, this, MainCategoryFieldType.SecondaryCategoryField);
            dialog.setCancelable(false);
            dialog.show();
        });

        areaField.setOnClickListener(l -> {
            MainCategoryFieldDialog dialog = new MainCategoryFieldDialog
                    (getActivity(), android.R.style.Theme_Translucent_NoTitleBar,
                            this, this, MainCategoryFieldType.AreaField);
            dialog.setCancelable(false);
            dialog.show();
        });

        cityField.setOnClickListener(l -> {
            if(areaId != -1){
                MainCategoryFieldDialog dialog = new MainCategoryFieldDialog
                        (getActivity(), android.R.style.Theme_Translucent_NoTitleBar,
                                this, this, MainCategoryFieldType.CityField);
                dialog.setCancelable(false);
                dialog.show();
            }

        });
    }

    @Override
    public void setSecondaryCategory(int secondaryCategoryId, String secondaryCategoryName) {
        this.secondaryCategoryId = secondaryCategoryId;
        secondaryCategoryField.setText(secondaryCategoryName);
    }

    @Override
    public void setArea(int areaId, String areaName) {
        this.areaId = areaId;
        areaField.setText(areaName);
    }

    @Override
    public void setCity(int cityId, String cityName) {
        this.cityId = cityId;
        cityField.setText(cityName);
    }

    @Override
    public int getMainCategoryId() {
        return mainCategoryId;
    }

    @Override
    public int getAreaId() {
        return areaId;
    }
}
