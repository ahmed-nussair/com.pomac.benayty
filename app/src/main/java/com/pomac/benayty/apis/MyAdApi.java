package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.MyAdResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyAdApi {

    @GET("benaity/public/api/advertisements/display")
    Observable<MyAdResponse> displayMyAd(@Query("token") String token, @Query("advertisement_id") int id);
}
