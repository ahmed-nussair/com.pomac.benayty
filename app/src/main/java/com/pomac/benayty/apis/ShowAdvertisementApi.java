package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.ShowAdvertisementResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShowAdvertisementApi {

    @GET("benaity/public/api/advertisement/show")
    Call<ShowAdvertisementResponse> showAdvertisement(@Query("advertisement_id") int adId);
}
