package com.pomac.benayty.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.benayty.Globals;
import com.pomac.benayty.apis.AdsApi;
import com.pomac.benayty.model.response.AdsResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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

        Observable<AdsResponse> observable = api.getAds(mainCategoryId, secondaryCategoryId, areaId, cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(
                response -> mutableLiveData.setValue(response),
                error -> mutableLiveData.setValue(null)
        );

        Globals.compositeDisposable.add(disposable);

    }
}
