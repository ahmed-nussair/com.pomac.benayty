package com.pomac.benayty.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.benayty.Globals;
import com.pomac.benayty.apis.WishListApi;
import com.pomac.benayty.model.response.WishListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

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

        Call<WishListResponse> wishListResponseCall = api.getWishList(token);

        wishListResponseCall.enqueue(new Callback<WishListResponse>() {

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<WishListResponse> call, Response<WishListResponse> response) {
                wishListResponseMutableLiveData.setValue(response.body());
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<WishListResponse> call, Throwable t) {

            }
        });
    }
}
