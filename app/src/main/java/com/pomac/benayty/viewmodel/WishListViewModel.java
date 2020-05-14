package com.pomac.benayty.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.benayty.Globals;
import com.pomac.benayty.apis.WishListApi;
import com.pomac.benayty.model.response.WishListResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WishListViewModel extends ViewModel {

    private MutableLiveData<WishListResponse> wishListResponseMutableLiveData;

    public LiveData<WishListResponse> getWishList(String token) {
        if(wishListResponseMutableLiveData == null) {
            wishListResponseMutableLiveData = new MutableLiveData<>();
            loadWishList(token);
        }
        return  wishListResponseMutableLiveData;
    }

    private void loadWishList(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        WishListApi api = retrofit.create(WishListApi.class);

        Observable<WishListResponse> observable = api.getWishList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(
                response -> wishListResponseMutableLiveData.setValue(response),
                error -> wishListResponseMutableLiveData.setValue(null)
        );

        Globals.compositeDisposable.add(disposable);
    }
}
