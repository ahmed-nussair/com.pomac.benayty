package com.pomac.benayty.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.benayty.Globals;
import com.pomac.benayty.apis.AdsApi;
import com.pomac.benayty.model.response.AdsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class AdsViewModel extends ViewModel {

    private MutableLiveData<AdsResponse> mutableLiveData;

    public LiveData<AdsResponse> getAdsList(int mainCategoryId, int secondaryCategoryId, int areaId, int cityId) {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            loadAds(mainCategoryId, secondaryCategoryId, areaId, cityId);
        }
        return mutableLiveData;
    }

    private void loadAds(int mainCategoryId, int secondaryCategoryId, int areaId, int cityId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        AdsApi api = retrofit.create(AdsApi.class);

        Call<AdsResponse> adsResponseCall = api.getAds(mainCategoryId, secondaryCategoryId, areaId, cityId);

        adsResponseCall.enqueue(new Callback<AdsResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<AdsResponse> call, Response<AdsResponse> response) {
                mutableLiveData.setValue(response.body());
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<AdsResponse> call, Throwable t) {

            }
        });

    }
}
