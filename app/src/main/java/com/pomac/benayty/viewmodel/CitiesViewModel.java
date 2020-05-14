package com.pomac.benayty.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.benayty.Globals;
import com.pomac.benayty.apis.CitiesApi;
import com.pomac.benayty.model.response.CitiesResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CitiesViewModel extends ViewModel {

    private MutableLiveData<CitiesResponse> mutableLiveData;

    public LiveData<CitiesResponse> getCitiesResponse(int areaId) {
        if(mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            loadCities(areaId);
        }
        return mutableLiveData;
    }

    private void loadCities(int areaId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        CitiesApi api = retrofit.create(CitiesApi.class);

        Observable<CitiesResponse> observable = api.getCities(areaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(
                response -> mutableLiveData.setValue(response),
                error -> mutableLiveData.setValue(null)
        );

        Globals.compositeDisposable.add(disposable);
    }
}
