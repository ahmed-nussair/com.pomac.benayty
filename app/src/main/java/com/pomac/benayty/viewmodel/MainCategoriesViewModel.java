package com.pomac.benayty.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.benayty.Globals;
import com.pomac.benayty.apis.MainCategoriesApi;
import com.pomac.benayty.model.response.MainCategoriesResponse;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainCategoriesViewModel extends ViewModel {

    private MutableLiveData<MainCategoriesResponse> mainCategoriesResponse;
    private Disposable disposable;

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

        Observable<MainCategoriesResponse> observable = api.getMainCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        disposable = observable.subscribe(response -> mainCategoriesResponse.setValue(response), error -> Log.e(Globals.TAG, Objects.requireNonNull(error.getMessage())));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
