package com.pomac.benayty.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.benayty.Globals;
import com.pomac.benayty.apis.SecondaryCategoriesApi;
import com.pomac.benayty.model.response.SecondaryCategoriesResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondaryCategoriesViewModel extends ViewModel {

    private MutableLiveData<SecondaryCategoriesResponse> responseMutableLiveData;

    public LiveData<SecondaryCategoriesResponse> getSecondaryCategoriesResponse(int mainCategoryId) {
        if(responseMutableLiveData == null) {
            responseMutableLiveData = new MutableLiveData<>();
            loadSecondaryCategories(mainCategoryId);
        }
        return responseMutableLiveData;
    }

    private void loadSecondaryCategories(int mainCategoryId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        SecondaryCategoriesApi api = retrofit.create(SecondaryCategoriesApi.class);

        Observable<SecondaryCategoriesResponse> observable = api.getSecondaryCategories(mainCategoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(response -> responseMutableLiveData.setValue(response),
                error -> responseMutableLiveData.setValue(null));

        Globals.compositeDisposable.add(disposable);
    }
}
