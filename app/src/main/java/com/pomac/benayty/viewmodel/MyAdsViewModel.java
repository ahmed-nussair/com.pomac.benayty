package com.pomac.benayty.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.benayty.Globals;
import com.pomac.benayty.apis.MyAdsApi;
import com.pomac.benayty.model.response.MyAdsResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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

        Observable<MyAdsResponse> observable = api.getMyAds(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(
                response -> mutableLiveData.setValue(response),
                error -> mutableLiveData.setValue(null)
        );

        Globals.compositeDisposable.add(disposable);
    }
}
