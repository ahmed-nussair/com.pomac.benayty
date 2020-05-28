package com.pomac.benayty.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.adapters.AreasAdapter;
import com.pomac.benayty.adapters.CitiesAdapter;
import com.pomac.benayty.adapters.MainCategoriesForAddingAdAdapter;
import com.pomac.benayty.adapters.SecondaryCategoriesAdapter;
import com.pomac.benayty.apis.AreasApi;
import com.pomac.benayty.apis.CitiesApi;
import com.pomac.benayty.apis.MainCategoriesApi;
import com.pomac.benayty.apis.SecondaryCategoriesApi;
import com.pomac.benayty.model.response.AreasResponse;
import com.pomac.benayty.model.response.CitiesResponse;
import com.pomac.benayty.model.response.MainCategoriesResponse;
import com.pomac.benayty.model.response.SecondaryCategoriesResponse;
import com.pomac.benayty.view.enums.AdFieldType;
import com.pomac.benayty.view.interfaces.AdFilter;
import com.pomac.benayty.view.interfaces.OnAreaSelected;
import com.pomac.benayty.view.interfaces.OnCitySelected;
import com.pomac.benayty.view.interfaces.OnMainCategorySelected;
import com.pomac.benayty.view.interfaces.OnSecondaryCategorySelected;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainCategoryFieldDialog extends Dialog implements
        OnMainCategorySelected,
        OnSecondaryCategorySelected,
        OnAreaSelected,
        OnCitySelected {

    private TextView dialogTitle;
    private RecyclerView fieldRecyclerView;
    private TextView submitButton;

    private Fragment fragment;
    private AdFilter adFilter;
    private AdFieldType type;
    private ProgressBar fieldDialogProgressBar;

    public MainCategoryFieldDialog(@NonNull Context context, int themeResId,
                                   Fragment fragment, AdFilter adFilter,
                                   AdFieldType type) {
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
        fieldDialogProgressBar = findViewById(R.id.fieldDialogProgressBar);
        submitButton.setOnClickListener(l -> this.dismiss());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        switch (type){
            case MainCategoryField:
                dialogTitle.setText(fragment.getResources().getString(R.string.choose_main_item));

                MainCategoriesApi mainCategoriesApi = retrofit.create(MainCategoriesApi.class);

                Observable<MainCategoriesResponse> mainCategoriesResponseObservable = mainCategoriesApi.getMainCategories()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

                Globals.compositeDisposable.add(mainCategoriesResponseObservable.subscribe(response -> {
                    MainCategoriesForAddingAdAdapter adapter = new MainCategoriesForAddingAdAdapter(fragment.getContext(), response.getData(), this);
                    fieldRecyclerView.setAdapter(adapter);
                    fieldRecyclerView.setLayoutManager(new LinearLayoutManager(fragment.getContext()));

                    fieldDialogProgressBar.setVisibility(View.GONE);
                    fieldRecyclerView.setVisibility(View.VISIBLE);
                }));
                break;
            case SecondaryCategoryField:
                dialogTitle.setText(fragment.getResources().getString(R.string.choose_secondary_item));

                SecondaryCategoriesApi secondaryCategoriesApi = retrofit.create(SecondaryCategoriesApi.class);

                Observable<SecondaryCategoriesResponse> secondaryCategoriesResponseObservable = secondaryCategoriesApi.getSecondaryCategories(adFilter.getMainCategoryId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

                Globals.compositeDisposable.add(secondaryCategoriesResponseObservable.subscribe(response -> {
                    SecondaryCategoriesAdapter adapter = new SecondaryCategoriesAdapter(fragment.getContext(), response.getData(), this);
                    fieldRecyclerView.setAdapter(adapter);
                    fieldRecyclerView.setLayoutManager(new LinearLayoutManager(fragment.getContext()));

                    fieldDialogProgressBar.setVisibility(View.GONE);
                    fieldRecyclerView.setVisibility(View.VISIBLE);
                }));

                break;
            case AreaField:
                dialogTitle.setText(fragment.getResources().getString(R.string.choose_area));

                AreasApi areasApi = retrofit.create(AreasApi.class);

                Observable<AreasResponse> areasResponseObservable = areasApi.getAreas()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

                Globals.compositeDisposable.add(areasResponseObservable.subscribe(response -> {
                    AreasAdapter adapter = new AreasAdapter(fragment.getContext(), response.getData(), this);
                    fieldRecyclerView.setAdapter(adapter);
                    fieldRecyclerView.setLayoutManager(new LinearLayoutManager(fragment.getContext()));

                    fieldDialogProgressBar.setVisibility(View.GONE);
                    fieldRecyclerView.setVisibility(View.VISIBLE);
                }));

                break;
            case CityField:
                dialogTitle.setText(fragment.getResources().getString(R.string.choose_city));

                CitiesApi citiesApi = retrofit.create(CitiesApi.class);

                Observable<CitiesResponse> citiesResponseObservable = citiesApi.getCities(adFilter.getAreaId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

                Globals.compositeDisposable.add(citiesResponseObservable.subscribe(
                        response -> {
                            CitiesAdapter adapter = new CitiesAdapter(fragment.getContext(), response.getData(), this);
                            fieldRecyclerView.setAdapter(adapter);
                            fieldRecyclerView.setLayoutManager(new LinearLayoutManager(fragment.getContext()));

                            fieldDialogProgressBar.setVisibility(View.GONE);
                            fieldRecyclerView.setVisibility(View.VISIBLE);
                        }
                ));

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

    @Override
    public void onMainCategorySelected(int mainCategoryId, String mainCategoryName) {
        adFilter.setMainCategory(mainCategoryId, mainCategoryName);
        dismiss();
    }
}
