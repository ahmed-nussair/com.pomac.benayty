package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.FcmTokenUpdateResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FcmTokenUpdateApi {

    @FormUrlEncoded
    @POST("benaity/public/api/account/update-token")
    Observable<FcmTokenUpdateResponse> updateFcmToken(
            @Field("token") String token,
            @Field("fcm_token") String fcmToken
    );
}
