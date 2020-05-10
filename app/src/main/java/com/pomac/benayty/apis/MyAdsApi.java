package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.MyAdsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyAdsApi {

    @GET("benaity/public/api/myadvertisements")
    Observable<MyAdsResponse> getMyAds(@Query("token") String token);
}
