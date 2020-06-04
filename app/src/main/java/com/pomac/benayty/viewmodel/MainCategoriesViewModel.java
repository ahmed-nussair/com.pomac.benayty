package com.pomac.benayty.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.benayty.Globals;
import com.pomac.benayty.apis.MainCategoriesApi;
import com.pomac.benayty.model.response.MainCategoriesResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class MainCategoriesViewModel extends ViewModel {

    private MutableLiveData<MainCategoriesResponse> mainCategoriesResponse;

    public LiveData<MainCategoriesResponse> getMainCategoriesResponse() {
        if (mainCategoriesResponse == null) {
            mainCategoriesResponse = new MutableLiveData<>();
            loadMainCategories();
        }
        return mainCategoriesResponse;
    }

    private void loadMainCategories() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        MainCategoriesApi api = retrofit.create(MainCategoriesApi.class);

        Call<MainCategoriesResponse> call = api.getMainCategories();

        call.enqueue(new Callback<MainCategoriesResponse>() {

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<MainCategoriesResponse> call, Response<MainCategoriesResponse> response) {
                mainCategoriesResponse.setValue(response.body());
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<MainCategoriesResponse> call, Throwable t) {
                Log.d(Globals.TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
