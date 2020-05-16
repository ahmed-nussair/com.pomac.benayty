package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.LoginResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {

    @FormUrlEncoded
    @POST("benaity/public/api/login")
    Observable<LoginResponse> login(
            @Field("phone") String phone,
            @Field("password") String password
    );
}
