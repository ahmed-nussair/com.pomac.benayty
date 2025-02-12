package com.pomac.benayty.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.view.enums.AdFieldType;
import com.pomac.benayty.view.dialogs.MainCategoryFieldDialog;
import com.pomac.benayty.view.interfaces.AdFilter;
import com.pomac.benayty.view.interfaces.AppNavigator;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainCategoryFragment extends Fragment implements AdFilter{

    private int mainCategoryId;
    private int secondaryCategoryId;
    private int areaId;
    private int cityId;

    private String mainCategoryName;

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
        mainCategoryName = getArguments().getString(Globals.MAIN_CATEGORY_NAME);

        assert getActivity() != null;
        navigator = (AppNavigator) getActivity();

        secondaryCategoryField.setOnClickListener(l -> {
            MainCategoryFieldDialog dialog = new MainCategoryFieldDialog
                    (getActivity(), android.R.style.Theme_Translucent_NoTitleBar,
                            this, this, AdFieldType.SecondaryCategoryField);
            dialog.setCancelable(false);
            dialog.show();
        });

        areaField.setOnClickListener(l -> {
            MainCategoryFieldDialog dialog = new MainCategoryFieldDialog
                    (getActivity(), android.R.style.Theme_Translucent_NoTitleBar,
                            this, this, AdFieldType.AreaField);
            dialog.setCancelable(false);
            dialog.show();
        });

        cityField.setOnClickListener(l -> {
            if(areaId != -1){
                MainCategoryFieldDialog dialog = new MainCategoryFieldDialog
                        (getActivity(), android.R.style.Theme_Translucent_NoTitleBar,
                                this, this, AdFieldType.CityField);
                dialog.setCancelable(false);
                dialog.show();
            } else {
                Toast.makeText(getContext(), "اختر المنطقة أولًا", Toast.LENGTH_LONG).show();
            }

        });

        findSecondaryCategoriesButton.setOnClickListener(l -> {

            if (secondaryCategoryId == -1) {
                Toast.makeText(getActivity(), "من فضلك اختر القسم الفرعي", Toast.LENGTH_LONG).show();
                return;
            } else if (areaId == -1) {
                Toast.makeText(getActivity(), "من فضلك اختر المنطقة", Toast.LENGTH_LONG).show();
                return;
            } else if (cityId == -1) {
                Toast.makeText(getActivity(), "من فضلك اختر المدينة", Toast.LENGTH_LONG).show();
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putInt(Globals.MAIN_CATEGORY_ID, mainCategoryId);
            bundle.putString(Globals.MAIN_CATEGORY_NAME, mainCategoryName);

            navigator.navigateToSearchResults(mainCategoryId, secondaryCategoryId, areaId, cityId, bundle);
        });
    }

    @Override
    public void setMainCategory(int mainCategoryId, String mainCategoryName) {
        // Nothing to do here
    }

    @Override
    public void setSecondaryCategory(int secondaryCategoryId, String secondaryCategoryName) {
        this.secondaryCategoryId = secondaryCategoryId;
        secondaryCategoryField.setText(secondaryCategoryName);
    }

    @Override
    public void setArea(int areaId, String areaName) {
        this.areaId = areaId;
        this.cityId = -1;
        areaField.setText(areaName);
        cityField.setText(getResources().getString(R.string.choose_city));
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
