package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.CodeCheckingResponse;
import com.pomac.benayty.model.response.ForgotPasswordResponse;
import com.pomac.benayty.model.response.UpdatePasswordResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ForgotPasswordApi {

    @FormUrlEncoded
    @POST("benaity/public/api/users/forget_password")
    Call<ForgotPasswordResponse> sendForPasswordRecovery(@Field("email") String email);

    @FormUrlEncoded
    @POST("benaity/public/api/users/check_reset_code")
    Call<CodeCheckingResponse> checkCode(@Field("reset_code") String code);

    @FormUrlEncoded
    @POST("benaity/public/api/users/reset_password")
    Call<UpdatePasswordResponse> updatePassword(
            @Field("reset_code") String resetCode,
            @Field("password") String password,
            @Field("password_confirm") String passwordConfirm
    );
}
