package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.AddToWishListResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddToWishListApi {

    @FormUrlEncoded
    @POST("benaity/public/api/wishlist/add")
    Observable<AddToWishListResponse> addToWishList(@Field("token") String token, @Field("advertisement_id") int adId);
}
