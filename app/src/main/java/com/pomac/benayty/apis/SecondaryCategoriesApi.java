package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.SecondaryCategoriesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SecondaryCategoriesApi {

    @GET("benaity/public/api/secondary")
    Observable<SecondaryCategoriesResponse> getSecondaryCategories(@Query("main_id") int mainId);
}
