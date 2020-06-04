package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.AdsResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AdsApi {

    @GET("benaity/public/api/advertisement")
    Call<AdsResponse> getAds(@Query("main_id") int mainId,
                             @Query("secondary_id") int secondaryId,
                             @Query("area_id") int areaId,
                             @Query("city_id") int cityId);
}
