package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.DeleteFromWishListResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DeleteFromWishListApi {

    @FormUrlEncoded
    @POST("benaity/public/api/wishlist/delete")
    Observable<DeleteFromWishListResponse> deleteFromWishList(
            @Field("token") String token,
            @Field("advertisement_id") int adId
    );
}
