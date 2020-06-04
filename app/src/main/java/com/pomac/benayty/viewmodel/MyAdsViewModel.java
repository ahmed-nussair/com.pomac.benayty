package com.pomac.benayty.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.benayty.Globals;
import com.pomac.benayty.apis.MyAdsApi;
import com.pomac.benayty.model.response.MyAdsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class MyAdsViewModel extends ViewModel {

    private MutableLiveData<MyAdsResponse> mutableLiveData;

    public LiveData<MyAdsResponse> getMyAds(String token) {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            loadMyAds(token);
        }
        return mutableLiveData;
    }

    private void loadMyAds(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        MyAdsApi api = retrofit.create(MyAdsApi.class);

        Call<MyAdsResponse> myAdsResponseCall = api.getMyAds(token);

        myAdsResponseCall.enqueue(new Callback<MyAdsResponse>() {

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<MyAdsResponse> call, Response<MyAdsResponse> response) {
                mutableLiveData.setValue(response.body());
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<MyAdsResponse> call, Throwable t) {

            }
        });

//        Observable<MyAdsResponse> observable = api.getMyAds(token)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//
//        Disposable disposable = observable.subscribe(
//                response -> mutableLiveData.setValue(response),
//                error -> mutableLiveData.setValue(null)
//        );
//
//        Globals.compositeDisposable.add(disposable);
    }
}
