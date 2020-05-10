package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.UserAdsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserAdsApi {

    @GET("benaity/public/api/users/advertisements")
    Observable<UserAdsResponse> getUserAds(@Query("user_id") int userId);
}
