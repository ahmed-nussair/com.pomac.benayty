package com.pomac.benayty.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.view.dialogs.MainCategoryFieldDialog;
import com.pomac.benayty.view.enums.AdFieldType;
import com.pomac.benayty.view.interfaces.AdFilter;
import com.pomac.benayty.view.interfaces.AppNavigator;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddingAdPag1Fragment extends Fragment implements AdFilter {

    private TextView mainCategoryAdField;
    private TextView secondaryCategoryAdField;
    private TextView areaAdField;
    private TextView cityAdField;
    private TextView addingAddNextPageButton;

    private AppNavigator navigator;

    private int mainCategoryId;
    private int secondaryCategoryId;
    private int areaId;
    private int cityId;

    public AddingAdPag1Fragment() {
        mainCategoryId = -1;
        secondaryCategoryId = -1;
        areaId = -1;
        cityId = -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adding_ad_pag1, container, false);

        mainCategoryAdField = view.findViewById(R.id.mainCategoryAdField);
        secondaryCategoryAdField = view.findViewById(R.id.secondaryCategoryAdField);
        areaAdField = view.findViewById(R.id.areaAdField);
        cityAdField = view.findViewById(R.id.cityAdField);
        addingAddNextPageButton = view.findViewById(R.id.addingAddNextPageButton);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;
        navigator = (AppNavigator) getActivity();

        mainCategoryAdField.setOnClickListener(v -> {
            MainCategoryFieldDialog dialog = new MainCategoryFieldDialog
                    (getActivity(), android.R.style.Theme_Translucent_NoTitleBar,
                            this, this, AdFieldType.MainCategoryField);
            dialog.setCancelable(false);
            dialog.show();
        });

        secondaryCategoryAdField.setOnClickListener(v -> {
            if (mainCategoryId != -1) {
                MainCategoryFieldDialog dialog = new MainCategoryFieldDialog
                        (getActivity(), android.R.style.Theme_Translucent_NoTitleBar,
                                this, this, AdFieldType.SecondaryCategoryField);
                dialog.setCancelable(false);
                dialog.show();
            } else {
                Toast.makeText(getContext(), "اختر القسم الرئيسي أولًا", Toast.LENGTH_LONG).show();
            }

        });

        areaAdField.setOnClickListener(v -> {
            MainCategoryFieldDialog dialog = new MainCategoryFieldDialog
                    (getActivity(), android.R.style.Theme_Translucent_NoTitleBar,
                            this, this, AdFieldType.AreaField);
            dialog.setCancelable(false);
            dialog.show();
        });

        cityAdField.setOnClickListener(v -> {
            if (areaId != -1) {
                MainCategoryFieldDialog dialog = new MainCategoryFieldDialog
                        (getActivity(), android.R.style.Theme_Translucent_NoTitleBar,
                                this, this, AdFieldType.CityField);
                dialog.setCancelable(false);
                dialog.show();
            } else {
                Toast.makeText(getContext(), "اختر المنطقة أولًا", Toast.LENGTH_LONG).show();
            }
        });

        addingAddNextPageButton.setOnClickListener(v -> {
            if (mainCategoryId == -1) {
                Toast.makeText(getContext(), "اختر القسم الرئيسي أولاً", Toast.LENGTH_LONG).show();
                return;
            } else if (secondaryCategoryId == -1) {
                Toast.makeText(getContext(), "اختر القسم الفرعي أولاً", Toast.LENGTH_LONG).show();
                return;
            } else if (areaId == -1) {
                Toast.makeText(getContext(), "اختر المنطقة", Toast.LENGTH_LONG).show();
                return;
            } else if (cityId == -1) {
                Toast.makeText(getContext(), "اختر المدينة", Toast.LENGTH_LONG).show();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putInt(Globals.MAIN_CATEGORY_ID, mainCategoryId);
            bundle.putInt(Globals.SECONDARY_CATEGORY_ID, secondaryCategoryId);
            bundle.putInt(Globals.AREA_ID, areaId);
            bundle.putInt(Globals.CITY_ID, cityId);

            Navigation.findNavController(getActivity().findViewById(R.id.nav_host)).navigate(R.id.addingAdPag2Fragment, bundle);
        });
    }

    @Override
    public void setMainCategory(int mainCategoryId, String mainCategoryName) {
        this.mainCategoryId = mainCategoryId;
        this.secondaryCategoryId = -1;
        mainCategoryAdField.setText(mainCategoryName);
        secondaryCategoryAdField.setText(getString(R.string.adding_ad_secondary_category));
    }

    @Override
    public void setSecondaryCategory(int secondaryCategoryId, String secondaryCategoryName) {
        this.secondaryCategoryId = secondaryCategoryId;
        secondaryCategoryAdField.setText(secondaryCategoryName);
    }

    @Override
    public void setArea(int areaId, String areaName) {
        this.areaId = areaId;
        this.cityId = -1;
        areaAdField.setText(areaName);
        cityAdField.setText(getResources().getString(R.string.adding_ad_city));
    }

    @Override
    public void setCity(int cityId, String cityName) {
        this.cityId = cityId;
        cityAdField.setText(cityName);
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
