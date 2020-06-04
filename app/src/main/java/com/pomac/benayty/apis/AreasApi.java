package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.AreasResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AreasApi {

    @GET("benaity/public/api/areas")
    Call<AreasResponse> getAreas();
}
