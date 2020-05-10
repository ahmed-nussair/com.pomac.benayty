package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.MainCategoriesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MainCategoriesApi {

    @GET("benaity/public/api/main")
    Observable<MainCategoriesResponse> getMainCategories();
}
