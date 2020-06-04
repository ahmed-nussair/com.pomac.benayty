package com.pomac.benayty.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.benayty.Globals;
import com.pomac.benayty.apis.ShowAdvertisementApi;
import com.pomac.benayty.model.response.ShowAdvertisementResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class AdViewModel extends ViewModel {

    private MutableLiveData<ShowAdvertisementResponse> mutableLiveData;

    public LiveData<ShowAdvertisementResponse> showAdvertisement(int adId) {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            doShowAdvertisement(adId);
        }
        return mutableLiveData;
    }

    private void doShowAdvertisement(int adId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        ShowAdvertisementApi api = retrofit.create(ShowAdvertisementApi.class);

        Call<ShowAdvertisementResponse> showAdvertisementResponseCall = api.showAdvertisement(adId);

        showAdvertisementResponseCall.enqueue(new Callback<ShowAdvertisementResponse>() {

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ShowAdvertisementResponse> call, Response<ShowAdvertisementResponse> response) {
                mutableLiveData.setValue(response.body());
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ShowAdvertisementResponse> call, Throwable t) {

            }
        });
    }
}
