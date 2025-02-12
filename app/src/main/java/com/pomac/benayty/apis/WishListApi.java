package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.WishListResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WishListApi {

    @GET("benaity/public/api/wishlist")
    Call<WishListResponse> getWishList(@Query("token") String token);
}
