package com.pomac.benayty.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.benayty.Globals;
import com.pomac.benayty.apis.ShowAdvertisementApi;
import com.pomac.benayty.model.response.ShowAdvertisementResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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

        Observable<ShowAdvertisementResponse> observable = api.showAdvertisement(adId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(
                response -> mutableLiveData.setValue(response)
        );

        Globals.compositeDisposable.add(disposable);
    }
}
