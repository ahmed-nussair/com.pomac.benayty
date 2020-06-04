package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.CitiesResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CitiesApi {

    @GET("benaity/public/api/cities")
    Call<CitiesResponse> getCities(@Query("area_id") int areaId);
}
