package com.pomac.benayty.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.benayty.Globals;
import com.pomac.benayty.apis.AreasApi;
import com.pomac.benayty.model.response.AreasResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AreasViewModel extends ViewModel {
    private MutableLiveData<AreasResponse> mutableLiveData;

    public LiveData<AreasResponse> getAreasResponse() {
        if(mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            loadAreas();
        }
        return  mutableLiveData;
    }

    private void loadAreas() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        AreasApi api = retrofit.create(AreasApi.class);

        Observable<AreasResponse> observable = api.getAreas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(response -> mutableLiveData.setValue(response),
                error -> mutableLiveData.setValue(null));

        Globals.compositeDisposable.add(disposable);
    }
}
