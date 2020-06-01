package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.DeleteMyAdResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DeleteMyAdApi {

    @FormUrlEncoded
    @POST("benaity/public/api/advertisements/delete")
    Observable<DeleteMyAdResponse> deleteAd(
            @Field("token") String token,
            @Field("advertisement_id") int adId
    );
}
