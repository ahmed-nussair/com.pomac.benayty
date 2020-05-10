package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.MyAccountResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyAccountApi {

    @GET("benaity/public/api/myaccount")
    Observable<MyAccountResponse> getMyAccount(@Query("token") String token);
}
