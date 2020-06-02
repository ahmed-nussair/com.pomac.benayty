package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.RegisterResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterApi {

    @FormUrlEncoded
    @POST("benaity/public/api/users/register")
    Observable<RegisterResponse> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password
    );
}
