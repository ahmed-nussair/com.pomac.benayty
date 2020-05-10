package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.ShowUserResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShowUserApi {

    @GET("benaity/public/api/users/show")
    Observable<ShowUserResponse> showUser(@Query("user_id") int userId);
}
